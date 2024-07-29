package com.example.librarymanager.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.librarymanager.R
import com.example.librarymanager.dao.SachDAO
import com.example.librarymanager.dao.SachOderDAO
import com.example.librarymanager.dao.ThanhVienDAO
import com.example.librarymanager.databinding.ItemSachOrderBinding
import com.example.librarymanager.model.sachOrder

class SachOrderAdapter(
    val activity: Activity,
    val onClickEdit: (sachOrder) -> Unit,
    val onClickDelete: (sachOrder) -> Unit
) : RecyclerView.Adapter<SachOrderAdapter.ViewHolder>() {
    val list: MutableList<sachOrder> = mutableListOf()

    fun submitData(newData: List<sachOrder>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: sachOrder) {
            if (binding is ItemSachOrderBinding) {
                val sach = SachOderDAO(activity).getID(item.maOrder.toString())

                binding.tvNameBook.text = "Tên Sách : ${sach.tenSach}"
                binding.tvNameTacGia.text = "Tên Tác Giả : ${sach.tenTacGia}"
                binding.tvNxb.text = "NXB : ${sach.nxb}"
                binding.tvNamXb.text = "NXB : ${sach.namXb}"
                binding.tvPrice.text = "Giá : ${sach.price}"
                binding.tvSl.text = "Sl : ${sach.soluong}"


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
                R.layout.item_sach_order, parent, false
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