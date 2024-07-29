package com.example.librarymanager

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.example.librarymanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.lifecycleOwner = this

        //set toolbar the the cho actionbar
        setSupportActionBar(mBinding.toolBar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Quản lý phiếu mượn"
        manager.beginTransaction()
            .replace(R.id.flContent, PhieuMuonFrg())
            .commit()

        mBinding.nvView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_phieu_muon -> {
                    title = "Quản lý phiếu mượn"
                    manager.beginTransaction()
                        .replace(R.id.flContent, PhieuMuonFrg())
                        .commit()
                }

                R.id.nav_loai_sach -> {
                    title = "Quản lý loại sách"
                    manager.beginTransaction()
                        .replace(R.id.flContent, LoaiSachFrg())
                        .commit()
                }

                R.id.nav_sach -> {
                    title = "Quản lý sách"
                    manager.beginTransaction()
                        .replace(R.id.flContent, SachFrg())
                        .commit()
                }

                R.id.nav_thanh_vien -> {
                    title = "Quản lý thành viên"
                    manager.beginTransaction()
                        .replace(R.id.flContent, ThanhVienFrg())
                        .commit()
                }

                R.id.order -> {
                    title = "Đặt Sách"
                    manager.beginTransaction()
                        .replace(R.id.flContent, OrderSachFrg())
                        .commit()
                }

                R.id.user -> {
                    title = "Quản lý user"
                    manager.beginTransaction()
                        .replace(R.id.flContent, UserFrg())
                        .commit()
                }

                R.id.change_pass -> {
                    title = "Đổi mật khẩu"
                    manager.beginTransaction()
                        .replace(R.id.flContent, ChangePassFragment())
                        .commit()
                }

                R.id.statistical -> {
                    title = "Thống Kê Tình Trạng"
                    manager.beginTransaction()
                        .replace(R.id.flContent, ThongKeFrg())
                        .commit()
                }

                R.id.sub_Logout -> {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                }
            }
            mBinding.drawerLayout.closeDrawers()
            false
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) mBinding.drawerLayout.openDrawer(GravityCompat.START)

        return super.onOptionsItemSelected(item)
    }
}