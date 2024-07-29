package com.example.librarymanager.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.librarymanager.R
import com.example.librarymanager.dao.LoaiSachDAO
import com.example.librarymanager.databinding.ItemSachBinding
import com.example.librarymanager.model.sach

class SachAdapter(
    val activity: Activity,
    val onClickEdit: (sach) -> Unit,
    val onClickDelete: (sach) -> Unit
) : RecyclerView.Adapter<SachAdapter.ViewHolder>() {
    val list: MutableList<sach> = mutableListOf()

    fun submitData(newData: List<sach>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: sach) {
            if (binding is ItemSachBinding) {
                binding.tvId.text = "Id : ${item.maSach}"
                binding.tvName.text = "Tên Sách : ${item.tenSach}"
                binding.tvPrice.text = "Giá : ${item.giaThue}"
                binding.tvType.text =
                    "Loại Sách : ${LoaiSachDAO(activity).getID(item.maLoai.toString()).tenLoai}"

                binding.tvSl.text = "Sl : ${item.soluong}"
                binding.tvTacGia.text = "Tac Gia : ${item.tacGia}"
                binding.tvNamXb.text = "Nam XB : ${item.namXb}"
                binding.tvNxb.text = "NXB : ${item.nxb}"

                Glide.with(binding.root).load(item.image).into(binding.imvSach)

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
                R.layout.item_sach, parent, false
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