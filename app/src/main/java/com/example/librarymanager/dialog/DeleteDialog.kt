package com.example.librarymanager.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.example.librarymanager.R
import com.example.librarymanager.databinding.DeleteDialogBinding

class DeleteDialog(
    val activity: Activity,
    val onDelete: () -> Unit
) : Dialog(activity, R.style.BaseDialog) {
    lateinit var mBinding: DeleteDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        mBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.delete_dialog,
                null,
                false
            )
        setContentView(mBinding.root)

        mBinding.btnCancel.setOnClickListener {
            dismiss()
        }
        mBinding.btnDelete.setOnClickListener {
            onDelete.invoke()
            dismiss()
        }
    }
}