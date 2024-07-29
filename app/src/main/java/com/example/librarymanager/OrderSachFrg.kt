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
import com.example.librarymanager.adapter.SachOrderAdapter
import com.example.librarymanager.dao.SachDAO
import com.example.librarymanager.dao.SachOderDAO
import com.example.librarymanager.databinding.FragmentCustomBinding
import com.example.librarymanager.dialog.DeleteDialog

class OrderSachFrg : Fragment() {
    private lateinit var mBinding: FragmentCustomBinding
    private var sachOrderAdapter: SachOrderAdapter? = null
    private lateinit var sachOrderDao: SachOderDAO

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
        sachOrderDao = SachOderDAO(requireContext())

        sachOrderAdapter = SachOrderAdapter(requireActivity(), onClickDelete = {
            DeleteDialog(requireActivity(), onDelete = {
                sachOrderDao.delete(it.maOrder.toString())
                sachOrderAdapter?.submitData(sachOrderDao.all)
            }).show()

        }, onClickEdit = { sach ->
            startActivity(Intent(this.context, OrderSachActivity::class.java).apply {
                putExtras(bundleOf("type" to 1, "idOrderSach" to sach.maOrder))
            })
        })
        mBinding.rcv.adapter = sachOrderAdapter

        sachOrderAdapter?.submitData(sachOrderDao.all)

        mBinding.fabAdd.setOnClickListener {
            startActivity(Intent(this.context, OrderSachActivity::class.java).apply {
                putExtras(bundleOf("type" to 0))
            })
        }
    }

    override fun onResume() {
        super.onResume()

        sachOrderAdapter?.submitData(sachOrderDao.all)
    }
}