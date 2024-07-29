package com.example.librarymanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanager.R
import com.example.librarymanager.databinding.ItemThanhVienBinding
import com.example.librarymanager.model.thanhVien

class ThanhVienAdapter(
    val onClickEdit: (thanhVien: thanhVien) -> Unit,
    val onClickDelete: (thanhVien: thanhVien) -> Unit
) : RecyclerView.Adapter<ThanhVienAdapter.ViewHolder>() {
    val list: MutableList<thanhVien> = mutableListOf()

    fun submitData(newData: List<thanhVien>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: thanhVien) {
            if (binding is ItemThanhVienBinding) {
                binding.tvName.text = "Tên : ${item.hoTen}"
                binding.tvEmail.text = "Email : ${item.email}"
                binding.tvSex.text =
                    if (item.gioiTinh == 0) "Giới Tính : Nam" else "Giới Tính : Nữ"
                binding.tvDiaChi.text = "Địa Chỉ : ${item.diaChi}"

                binding.imvEdit.setOnClickListener {
                    onClickEdit.invoke(item)
                }

                binding.imvDelete.setOnClickListener {
                    onClickDelete.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_thanh_vien, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        if (list.isNotEmpty()) {
            return list.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(list[position])
    }
}