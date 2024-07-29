package com.example.librarymanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.librarymanager.dao.ThanhVienDAO
import com.example.librarymanager.databinding.ThanhVienManagerActivityBinding
import com.example.librarymanager.model.thanhVien

class ThanhVienManagerActivity : AppCompatActivity() {
    private lateinit var mBinding: ThanhVienManagerActivityBinding

    private lateinit var thanhVienDAO: ThanhVienDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.thanh_vien_manager_activity)
        thanhVienDAO = ThanhVienDAO(this)

        val idThanhVien = intent.getIntExtra("idThanhVien", -1)
        val type = intent.getIntExtra("type", 0)

        mBinding.tvTitle.text = if (type == 0) "Thêm thành viên" else "Sửa thành viên"

        if (idThanhVien != -1) {
            val thanhVien = thanhVienDAO.getID(idThanhVien.toString())
            mBinding.edName.setText(thanhVien.hoTen)
            mBinding.edEmail.setText(thanhVien.email)
            if (thanhVien.gioiTinh == 0) {
                mBinding.rdNam.isChecked = true
            } else {
                mBinding.rdNu.isChecked = true
            }
            mBinding.edDiaChi.setText(thanhVien.diaChi)
        }

        mBinding.btnSave.setOnClickListener {
            val name = mBinding.edName.text.toString()
            val email = mBinding.edEmail.text.toString()
            val address = mBinding.edDiaChi.text.toString()
            val phone = mBinding.edSdt.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty() && phone.isNotEmpty()) {
                if (type == 0) {
                    thanhVienDAO.insert(
                        thanhVien(
                            hoTen = name,
                            email = email,
                            diaChi = address,
                            sdt = phone,
                            gioiTinh = if (mBinding.rdNam.isChecked) 0 else 1
                        )
                    )
                    Toast.makeText(this, "Thêm thành viên thành công !", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    thanhVienDAO.update(
                        thanhVien(
                            maTV = idThanhVien,
                            hoTen = name,
                            email = email,
                            diaChi = address,
                            sdt = phone,
                            gioiTinh = if (mBinding.rdNam.isChecked) 0 else 1
                        )
                    )
                    Toast.makeText(this, "update thành viên thành công !", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin !", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        mBinding.btnCancel.setOnClickListener {
            mBinding.edName.setText("")
            mBinding.edEmail.setText("")
            mBinding.edDiaChi.setText("")
            mBinding.edSdt.setText("")
        }

        mBinding.imvBack.setOnClickListener {
            finish()
        }
    }
}