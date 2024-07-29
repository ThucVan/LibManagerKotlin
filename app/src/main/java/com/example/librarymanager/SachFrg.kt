package com.example.librarymanager

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.librarymanager.adapter.SachAdapter
import com.example.librarymanager.dao.LoaiSachDAO
import com.example.librarymanager.dao.SachDAO
import com.example.librarymanager.databinding.FragmentCustomBinding
import com.example.librarymanager.dialog.DeleteDialog

class SachFrg : Fragment() {
    private lateinit var mBinding: FragmentCustomBinding
    private var sachAdapter: SachAdapter? = null
    private lateinit var sachDao: SachDAO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sachDao = SachDAO(requireContext())

        sachAdapter = SachAdapter(requireActivity(), onClickDelete = {
            DeleteDialog(requireActivity(), onDelete = {
                sachDao.delete(it.maSach.toString())
                sachAdapter?.submitData(sachDao.all)
            }).show()

        }, onClickEdit = { sach ->
            startActivity(Intent(this.context, SachManagerActivity::class.java).apply {
                putExtras(bundleOf("type" to 1, "idSach" to sach.maSach))
            })
        })
        mBinding.rcv.adapter = sachAdapter

        sachAdapter?.submitData(sachDao.all)

        mBinding.fabAdd.setOnClickListener {
            if (LoaiSachDAO(requireContext()).all.isNotEmpty()) {
                startActivity(Intent(this.context, SachManagerActivity::class.java).apply {
                    putExtras(bundleOf("type" to 0))
                })
            } else {
                Toast.makeText(
                    requireContext(),
                    "Vui lòng thêm loại sách trước !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        sachAdapter?.submitData(sachDao.all)
    }
}