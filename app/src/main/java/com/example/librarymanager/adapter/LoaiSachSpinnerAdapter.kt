package com.example.librarymanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.librarymanager.R
import com.example.librarymanager.databinding.SachItemSpinnerBinding
import com.example.librarymanager.model.loaiSach
import com.example.librarymanager.model.sach

class LoaiSachSpinnerAdapter(context: Context, private val lists: List<loaiSach>) :
    ArrayAdapter<loaiSach>(context, 0, lists) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: SachItemSpinnerBinding = if (convertView == null) {
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.sach_item_spinner,
                parent,
                false
            )
        } else {
            DataBindingUtil.getBinding(convertView)!!
        }
        binding.tvMaSachSp.text = "${lists[position].maLoai} ."
        binding.tvTenSachSp.text = "${lists[position].tenLoai}"
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: SachItemSpinnerBinding = if (convertView == null) {
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.sach_item_spinner,
                parent,
                false
            )
        } else {
            DataBindingUtil.getBinding(convertView)!!
        }
        binding.tvMaSachSp.text = "${lists[position].maLoai} ."
        binding.tvTenSachSp.text = "${lists[position].tenLoai}"
        return binding.root
    }
}
