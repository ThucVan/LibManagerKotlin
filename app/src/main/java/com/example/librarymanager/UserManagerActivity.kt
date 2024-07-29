package com.example.librarymanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.librarymanager.dao.UserDAO
import com.example.librarymanager.databinding.UserManagerActivityBinding
import com.example.librarymanager.model.loaiSach
import com.example.librarymanager.model.user

class UserManagerActivity : AppCompatActivity() {

    private lateinit var mBinding: UserManagerActivityBinding
    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.user_manager_activity)

        userDAO = UserDAO(this)

        val type = intent.getIntExtra("type", 0)
        val idUser = intent.getIntExtra("idUser", -1)
        mBinding.tvTitle.text = if (type == 0) "Thêm user" else "Sửa user"

        if (idUser != -1) {
            val user = userDAO.getID(idUser.toString())
            mBinding.edName.setText(user.hoTen)
            mBinding.edUserName.setText(user.username)
            mBinding.edPass.setText(user.password)
            mBinding.edDiaChi.setText(user.diaChi)
            mBinding.edEmail.setText(user.email)
            mBinding.edSdt.setText(user.sdt)
            if (user.gioiTinh == 0) {
                mBinding.rdNam.isChecked = true
            } else {
                mBinding.rdNu.isChecked = true
            }
        }

        mBinding.btnSave.setOnClickListener {
            if (idUser == -1) {
                userDAO.insert(
                    user(
                        username = mBinding.edUserName.text.toString(),
                        password = mBinding.edPass.text.toString(),
                        hoTen = mBinding.edName.text.toString(),
                        gioiTinh = if (mBinding.rdNam.isChecked) 0 else 1,
                        email = mBinding.edEmail.text.toString(),
                        sdt = mBinding.edSdt.text.toString(),
                        diaChi = mBinding.edDiaChi.text.toString()
                    )
                )
                Toast.makeText(this, "Thêm thành công !", Toast.LENGTH_SHORT).show()
            } else {
                userDAO.update(
                    user(
                        id= idUser,
                        username = mBinding.edUserName.text.toString(),
                        password = mBinding.edPass.text.toString(),
                        hoTen = mBinding.edName.text.toString(),
                        gioiTinh = if (mBinding.rdNam.isChecked) 0 else 1,
                        email = mBinding.edEmail.text.toString(),
                        sdt = mBinding.edSdt.text.toString(),
                        diaChi = mBinding.edDiaChi.text.toString()
                    )
                )

                Toast.makeText(this, "Sửa thành công !", Toast.LENGTH_SHORT).show()
            }
            finish()
        }

        mBinding.btnCancel.setOnClickListener {
            mBinding.edName.setText("")
            mBinding.edUserName.setText("")
            mBinding.edPass.setText("")
            mBinding.edDiaChi.setText("")
            mBinding.edEmail.setText("")
            mBinding.edSdt.setText("")
        }

        mBinding.imvBack.setOnClickListener {
            finish()
        }
    }
}