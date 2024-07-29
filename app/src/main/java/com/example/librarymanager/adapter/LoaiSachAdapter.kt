package com.example.librarymanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanager.R
import com.example.librarymanager.databinding.ItemLoaiSachBinding
import com.example.librarymanager.model.loaiSach

class LoaiSachAdapter(val onClickEdit : (loaiSach) -> Unit, val onClickDelete : (loaiSach) -> Unit ) : RecyclerView.Adapter<LoaiSachAdapter.ViewHolder>() {
    val list: MutableList<loaiSach> = mutableListOf()

    fun submitData(newData: List<loaiSach>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindData(loaiSach: loaiSach) {
            if (binding is ItemLoaiSachBinding){
                binding.tvName.text = "Tên loại sách : ${loaiSach.tenLoai}"
                binding.imvEdit.setOnClickListener {
                    onClickEdit.invoke(loaiSach)
                }

                binding.imvDelete.setOnClickListener {
                    onClickDelete.invoke(loaiSach)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_loai_sach, parent, false
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