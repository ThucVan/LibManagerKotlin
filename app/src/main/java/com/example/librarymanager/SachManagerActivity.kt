package com.example.librarymanager

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.AdapterViewBindingAdapter
import com.bumptech.glide.Glide
import com.example.librarymanager.adapter.LoaiSachSpinnerAdapter
import com.example.librarymanager.dao.LoaiSachDAO
import com.example.librarymanager.dao.SachDAO
import com.example.librarymanager.databinding.SachManagerActivityBinding
import com.example.librarymanager.model.sach
import com.github.dhaval2404.imagepicker.ImagePicker

class SachManagerActivity : AppCompatActivity() {

    private lateinit var mBinding: SachManagerActivityBinding
    private lateinit var loadSachDAO: LoaiSachDAO
    private lateinit var sachDAO: SachDAO
    private var uriImg = ""
    private var idLoaiSach = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.sach_manager_activity)

        loadSachDAO = LoaiSachDAO(this)
        sachDAO = SachDAO(this)

        val type = intent.getIntExtra("type", 0)
        val idSach = intent.getIntExtra("idSach", -1)
        mBinding.tvTitle.text = if (type == 0) "Thêm loại sách" else "Sửa loại sách"

        val listLoaiSach = loadSachDAO.all

        mBinding.spLoaiSach.adapter = LoaiSachSpinnerAdapter(this, listLoaiSach)
        mBinding.spLoaiSach.onItemSelectedListener =
            object : AdapterViewBindingAdapter.OnItemSelected,
                AdapterView.OnItemSelectedListener {
                @SuppressLint("RestrictedApi")
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    id: Long
                ) {
                    idLoaiSach = listLoaiSach[position].maLoai
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        if (idSach != -1) {
            val sach = sachDAO.getID(idSach.toString())
            for (i in listLoaiSach.indices) {
                if (listLoaiSach[i].maLoai == sach.maLoai) {
                    mBinding.spLoaiSach.setSelection(i)
                    break
                }
            }
            mBinding.edName.setText(sach.tenSach)
            mBinding.edPrice.setText(sach.giaThue.toString())
            mBinding.edNxb.setText(sach.nxb.toString())
            mBinding.edNamXb.setText(sach.namXb.toString())
            mBinding.edTacGia.setText(sach.tacGia.toString())
            mBinding.edSoLuong.setText(sach.soluong.toString())

            uriImg = sach.image.toString()
            Glide.with(this).load(sach.image).into(mBinding.imvSach)
        }

        mBinding.imvSach.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()    //User can only select image from Gallery
                .start() //Default Request Code is ImagePicker.REQUEST_CODE
        }

        mBinding.btnSave.setOnClickListener {
            if (idSach == -1) {
                sachDAO.insert(
                    sach(
                        tenSach = mBinding.edName.text.toString(),
                        giaThue = mBinding.edPrice.text.toString().toInt(),
                        nxb = mBinding.edNxb.text.toString(),
                        namXb = mBinding.edNamXb.text.toString().toInt(),
                        tacGia = mBinding.edTacGia.text.toString(),
                        soluong = mBinding.edSoLuong.text.toString().toInt(),
                        image = uriImg,
                        maLoai = loadSachDAO.getID(idLoaiSach.toString()).maLoai
                    )
                )
                Toast.makeText(this, "Thêm thành công !", Toast.LENGTH_SHORT).show()
            } else {
                sachDAO.update(
                    sach(
                        maSach = idSach,
                        tenSach = mBinding.edName.text.toString(),
                        giaThue = mBinding.edPrice.text.toString().toInt(),
                        nxb = mBinding.edNxb.text.toString(),
                        namXb = mBinding.edNamXb.text.toString().toInt(),
                        tacGia = mBinding.edTacGia.text.toString(),
                        soluong = mBinding.edSoLuong.text.toString().toInt(),
                        image = uriImg,
                        maLoai = loadSachDAO.getID(idLoaiSach.toString()).maLoai
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                uriImg = fileUri.toString()

                Glide.with(this).load(fileUri).into(mBinding.imvSach)
            }

            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }

            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}