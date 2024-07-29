package com.example.librarymanager

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.librarymanager.adapter.ThanhVienAdapter
import com.example.librarymanager.dao.ThanhVienDAO
import com.example.librarymanager.databinding.FragmentCustomBinding
import com.example.librarymanager.dialog.DeleteDialog

class ThanhVienFrg : Fragment() {
    private lateinit var mBinding: FragmentCustomBinding
    private var thanhVienAdapter: ThanhVienAdapter? = null
    private lateinit var thanhVienDAO: ThanhVienDAO

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

        thanhVienDAO = ThanhVienDAO(requireContext())

        thanhVienAdapter = ThanhVienAdapter(onClickEdit = {
            startActivity(Intent(this.context, ThanhVienManagerActivity::class.java).apply {
                putExtras(bundleOf("type" to 1, "idThanhVien" to it.maTV))
            })
        }, onClickDelete = {
            DeleteDialog(requireActivity(), onDelete = {
                thanhVienDAO.delete(it.maTV.toString())
                thanhVienAdapter?.submitData(thanhVienDAO.all)
            }).show()
        })

        mBinding.rcv.adapter = thanhVienAdapter
        thanhVienAdapter?.submitData(thanhVienDAO.all)

        mBinding.fabAdd.setOnClickListener {
            startActivity(Intent(this.context, ThanhVienManagerActivity::class.java).apply {
                putExtras(bundleOf("type" to 0))
            })
        }
    }

    override fun onResume() {
        super.onResume()

        thanhVienAdapter?.submitData(thanhVienDAO.all)
    }
}