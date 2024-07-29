package com.example.librarymanager

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.librarymanager.adapter.LoaiSachAdapter
import com.example.librarymanager.dao.LoaiSachDAO
import com.example.librarymanager.databinding.FragmentCustomBinding
import com.example.librarymanager.dialog.DeleteDialog

class LoaiSachFrg : Fragment() {
    private lateinit var mBinding: FragmentCustomBinding
    private var loaiSachAdapter: LoaiSachAdapter? = null
    private lateinit var loaiSachDAO: LoaiSachDAO

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
        loaiSachDAO = LoaiSachDAO(requireContext())

        loaiSachAdapter = LoaiSachAdapter(onClickDelete = {
            DeleteDialog(requireActivity()) {
                loaiSachDAO.delete(it.maLoai)
                loaiSachAdapter?.submitData(loaiSachDAO.all)
            }.show()
        }, onClickEdit = { loaiSach ->
            startActivity(Intent(requireActivity(), LoaiSachManagerActivity::class.java).apply {
                putExtras(bundleOf("type" to 1, "idLoaiSach" to loaiSach.maLoai))
            })
        })

        loaiSachAdapter?.submitData(loaiSachDAO.all)

        mBinding.rcv.adapter = loaiSachAdapter

        mBinding.fabAdd.setOnClickListener {
            startActivity(Intent(requireActivity(), LoaiSachManagerActivity::class.java).apply {
                putExtras(bundleOf("type" to 0))
            })
        }
    }

    override fun onResume() {
        super.onResume()

        loaiSachAdapter?.submitData(loaiSachDAO.all)
    }
}