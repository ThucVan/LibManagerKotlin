package com.example.librarymanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.librarymanager.dao.LoaiSachDAO
import com.example.librarymanager.databinding.LoaiSachManagerActivityBinding
import com.example.librarymanager.model.loaiSach

class LoaiSachManagerActivity : AppCompatActivity() {

    private lateinit var mBinding: LoaiSachManagerActivityBinding
    private lateinit var loadSachDAO: LoaiSachDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.loai_sach_manager_activity)

        loadSachDAO = LoaiSachDAO(this)

        val type = intent.getIntExtra("type", 0)
        val idLoaiSach = intent.getIntExtra("idLoaiSach", -1)
        mBinding.tvTitle.text = if (type == 0) "Thêm loại sách" else "Sửa loại sách"

        if (idLoaiSach != -1) {
            val loaiSach = loadSachDAO.getID(idLoaiSach.toString())
            mBinding.edName.setText(loaiSach.tenLoai)
        }

        mBinding.btnSave.setOnClickListener {
            if (idLoaiSach == -1) {
                loadSachDAO.insert(loaiSach(tenLoai = mBinding.edName.text.toString()))
                Toast.makeText(this, "Thêm thành công !", Toast.LENGTH_SHORT).show()
            } else {
                loadSachDAO.update(
                    loaiSach(
                        idLoaiSach,
                        mBinding.edName.text.toString()
                    )
                )

                Toast.makeText(this, "Sửa thành công !", Toast.LENGTH_SHORT).show()
            }
            finish()
        }

        mBinding.btnCancel.setOnClickListener {
            mBinding.edName.setText("")
        }

        mBinding.imvBack.setOnClickListener {
            finish()
        }
    }
}