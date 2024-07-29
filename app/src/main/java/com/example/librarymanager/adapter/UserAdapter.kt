package com.example.librarymanager.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanager.R
import com.example.librarymanager.databinding.ItemUserBinding
import com.example.librarymanager.model.user

class UserAdapter(
    val activity: Activity,
    val onClickEdit: (user) -> Unit,
    val onClickDelete: (user) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    val list: MutableList<user> = mutableListOf()

    fun submitData(newData: List<user>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: user) {
            if (binding is ItemUserBinding) {
                binding.tvUserName.text = "Tên đăng nhập : ${item.username}"
                binding.tvPass.text = "Mật khẩu : ${item.password}"
                binding.tvName.text = "Họ tên : ${item.hoTen}"
                binding.tvSex.text =
                    if (item.gioiTinh == 0) "Giới tính : Nam" else "Giới tính : Nữ"
                binding.tvSdt.text = "SDT : ${item.sdt}"
                binding.tvAddress.text = "Địa chỉ : ${item.diaChi}"

                binding.imvDelete.setOnClickListener {
                    onClickDelete.invoke(item)
                }

                binding.imvEdit.setOnClickListener {
                    onClickEdit.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user, parent, false
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