package com.example.librarymanager.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanager.R
import com.example.librarymanager.dao.SachDAO
import com.example.librarymanager.dao.ThanhVienDAO
import com.example.librarymanager.databinding.ItemPhieuMuonBinding
import com.example.librarymanager.model.phieuMuon
import java.text.SimpleDateFormat

class PhieuMuonAdapter(
    val activity: Activity,
    val onClickDelete: (phieuMuon) -> Unit,
    val onClickEdit: (phieuMuon) -> Unit,
    val isThongke: Boolean? = false,
) : RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder>() {
    val list: MutableList<phieuMuon> = mutableListOf()
    var sdf: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")

    val thanhVienDAO = ThanhVienDAO(activity)
    val sachDAO = SachDAO(activity)

    var sendMail: ((String) -> Unit?)? = null

    fun submitData(newData: List<phieuMuon>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: phieuMuon) {
            if (binding is ItemPhieuMuonBinding) {
                binding.tvId.text = "ID : ${item.maPM}"
                binding.tvName.text = "Tên TV : ${thanhVienDAO.getID(item.maTV.toString()).hoTen}"
                binding.tvNameSach.text =
                    "Tên Sách : ${sachDAO.getID(item.maSach.toString()).tenSach}"
                binding.tvSl.text = "SL : ${item.soLuong}"
                binding.tvPrice.text = "Tiền Thuê : ${item.tienThue}"
                binding.tvNgayMuon.text = "Ngày Mượn : ${sdf.format(item.ngayMuon)}"

                if (item.traSach != 0) {
                    binding.tvNgayTra.visibility = View.VISIBLE
                    binding.tvNgayTra.text = "Ngày Trả : ${sdf.format(item.ngayTra)}"
                } else {
                    binding.tvNgayTra.visibility = View.GONE
                }

                if (item.traSach == 0) {
                    binding.tvStatusSach.text = "Chưa Trả Sách"
                    binding.tvStatusSach.setTextColor(ContextCompat.getColor(activity, R.color.red))
                } else {
                    binding.tvStatusSach.text = "Đã Trả Sách"
                    binding.tvStatusSach.setTextColor(
                        ContextCompat.getColor(
                            activity,
                            R.color.blue
                        )
                    )
                }

                if (isThongke == true) {
                    binding.imvEdit.visibility = View.GONE
                    binding.imvDelete.visibility = View.GONE
                    binding.btnSendEmail.visibility = View.VISIBLE
                } else {
                    binding.imvEdit.visibility = View.VISIBLE
                    binding.imvDelete.visibility = View.VISIBLE
                    binding.btnSendEmail.visibility = View.GONE
                }

                binding.btnSendEmail.setOnClickListener {
                    sendMail?.invoke(ThanhVienDAO(activity).getID(item.maTV.toString()).email.toString())
                }

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
                R.layout.item_phieu_muon, parent, false
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