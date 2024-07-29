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
import com.example.librarymanager.adapter.PhieuMuonAdapter
import com.example.librarymanager.dao.PhieuMuonDAO
import com.example.librarymanager.dao.SachDAO
import com.example.librarymanager.dao.ThanhVienDAO
import com.example.librarymanager.databinding.FragmentCustomBinding
import com.example.librarymanager.dialog.DeleteDialog

class PhieuMuonFrg : Fragment() {
    private lateinit var mBinding: FragmentCustomBinding
    private var phieuMuonAdapter: PhieuMuonAdapter? = null
    private lateinit var phieuMuonDAO: PhieuMuonDAO

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

        phieuMuonDAO = PhieuMuonDAO(requireContext())

        phieuMuonAdapter = PhieuMuonAdapter(requireActivity(), onClickDelete = {
            DeleteDialog(requireActivity()) {
                phieuMuonDAO.delete(it.maPM.toString())
                phieuMuonAdapter?.submitData(phieuMuonDAO.all)
            }.show()
        }, onClickEdit = {
            startActivity(Intent(this.context, PhieuMuonManagerActivity::class.java).apply {
                putExtras(bundleOf("type" to 1, "idPhieuMuon" to it.maPM))
            })
        })

        mBinding.rcv.adapter = phieuMuonAdapter

        mBinding.fabAdd.setOnClickListener {
            if (ThanhVienDAO(requireActivity()).all.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng thêm thành viên trước !", Toast.LENGTH_SHORT).show()
            }else if (SachDAO(requireContext()).all.isEmpty()){
                Toast.makeText(requireContext(), "Vui lòng thêm sách viên trước !", Toast.LENGTH_SHORT).show()
            }else{
                startActivity(Intent(this.context, PhieuMuonManagerActivity::class.java).apply {
                    putExtras(bundleOf("type" to 0))
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()

        phieuMuonAdapter?.submitData(phieuMuonDAO.all)
    }
}