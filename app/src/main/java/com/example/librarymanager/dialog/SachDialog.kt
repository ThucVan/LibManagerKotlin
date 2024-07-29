package com.example.librarymanager.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.AdapterViewBindingAdapter.OnItemSelected
import com.example.librarymanager.R
import com.example.librarymanager.adapter.LoaiSachSpinnerAdapter
import com.example.librarymanager.dao.LoaiSachDAO
import com.example.librarymanager.databinding.SachDialogBinding
import com.example.librarymanager.model.sach

class SachDialog(
    val activity: Activity,
    val type: Int,
    val onSave: (sach) -> Unit,
    val sach: sach? = null
) : Dialog(activity, R.style.BaseDialog) {
    lateinit var mBinding: SachDialogBinding
    private var maLoaiSach = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        mBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.sach_dialog,
                null,
                false
            )
        setContentView(mBinding.root)

        if (sach != null) {
            mBinding.edName.setText(sach.tenSach)
            mBinding.edPrice.setText(sach.giaThue.toString())
        }

        mBinding.tvTitle.text = if (type == 0) "Thêm sách" else "Sửa sách"

        val listLoaiSach = LoaiSachDAO(activity).all

        mBinding.spLoaiSach.adapter = LoaiSachSpinnerAdapter(activity, listLoaiSach)
        mBinding.spLoaiSach.onItemSelectedListener = object : OnItemSelected,
            AdapterView.OnItemSelectedListener {
            @SuppressLint("RestrictedApi")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                maLoaiSach = listLoaiSach[position].maLoai
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        mBinding.btnCancel.setOnClickListener {
            dismiss()
        }
        mBinding.btnSave.setOnClickListener {
            if (mBinding.edName.text.toString().isNotEmpty() && mBinding.edPrice.text.toString()
                    .isNotEmpty()
            ) {
                if (type == 0) {
                    onSave.invoke(
                        sach(
                            tenSach = mBinding.edName.text.toString(),
                            giaThue = mBinding.edPrice.text.toString().toInt(),
                            maLoai = maLoaiSach
                        )
                    )
                } else {
                    sach?.copy(
                        tenSach = mBinding.edName.text.toString(),
                        giaThue = mBinding.edPrice.text.toString().toInt(),
                        maLoai = maLoaiSach
                    )
                        ?.let { it1 -> onSave.invoke(it1) }
                }
                dismiss()
            } else {
                mBinding.edName.error = "Vui lòng nhập đủ các trường để tiếp tục !"
            }
        }
    }
}