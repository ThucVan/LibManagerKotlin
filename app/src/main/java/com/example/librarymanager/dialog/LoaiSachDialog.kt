package com.example.librarymanager.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.example.librarymanager.R
import com.example.librarymanager.databinding.LoaiSachDialogBinding
import com.example.librarymanager.model.loaiSach

class LoaiSachDialog(
    val activity: Activity,
    val type: Int,
    val onSave: (loaiSach) -> Unit,
    val loaiSach: loaiSach? = null
) : Dialog(activity, R.style.BaseDialog) {
    lateinit var mBinding: LoaiSachDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        mBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.loai_sach_dialog,
                null,
                false
            )
        setContentView(mBinding.root)

        if (loaiSach != null) {
            mBinding.edName.setText(loaiSach.tenLoai)
        }

        mBinding.tvTitle.text = if (type == 0) "Thêm loại sách" else "Sửa loại sách"
        mBinding.btnCancel.setOnClickListener {
            dismiss()
        }
        mBinding.btnSave.setOnClickListener {
            if (mBinding.edName.text.toString().isNotEmpty()) {
                if (type == 0) {
                    onSave.invoke(loaiSach(tenLoai = mBinding.edName.text.toString()))
                } else {
                    loaiSach?.copy(tenLoai = mBinding.edName.text.toString())
                        ?.let { it1 -> onSave.invoke(it1) }
                }
                dismiss()
            } else {
                mBinding.edName.error = "Tên loại sách không được để trống !"
            }
        }
    }
}