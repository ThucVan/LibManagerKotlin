package com.example.librarymanager

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.librarymanager.dao.UserDAO
import com.example.librarymanager.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mBinding.lifecycleOwner = this

        // doc user, pass trong SharedPreferences
        val pref = getSharedPreferences("USER_FILE", MODE_PRIVATE)
        mBinding.edUsername.setText(pref.getString("USERNAME", ""))
        mBinding.edPass.setText(pref.getString("PASSWORD", ""))
        mBinding.ckbPass.setChecked(pref.getBoolean("REMEMBER", false))

        mBinding.btnExit.setOnClickListener {
            finishAffinity()
        }

        mBinding.btnLogin.setOnClickListener {
            val strUser = mBinding.edUsername.text.toString()
            val strPass = mBinding.edPass.text.toString()

            val dao = UserDAO(this)
            if (strUser.isEmpty() || strPass.isEmpty()) {
                Toast.makeText(
                    applicationContext, "Tên đăng nhập và mật khẩu không được để trống",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (dao.checkLogin(
                        strUser,
                        strPass
                    ) > 0 || (strUser == "admin" && strPass == "admin")
                ) {
                    Toast.makeText(applicationContext, "Đăng nhập thành công", Toast.LENGTH_SHORT)
                        .show()

                    rememberUser(strUser, strPass, mBinding.ckbPass.isChecked)

                    val i = Intent(applicationContext, MainActivity::class.java)
                    i.putExtra("user", strUser)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(
                        applicationContext, "Tên đăng nhập hoặc mật khẩu không đúng",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun rememberUser(u: String?, p: String?, status: Boolean) {
        val pref = getSharedPreferences("USER_FILE", MODE_PRIVATE)
        val edit = pref.edit()
        if (!status) {
            //xoa tinh trang luu tru truoc do
            edit.clear()
        } else {
            //luu du lieu
            edit.putString("USERNAME", u)
            edit.putString("PASSWORD", p)
            edit.putBoolean("REMEMBER", status)
        }
        //luu lai toan bo
        edit.commit()
    }
}