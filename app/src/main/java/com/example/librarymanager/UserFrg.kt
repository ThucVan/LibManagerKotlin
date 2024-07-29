package com.example.librarymanager

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.librarymanager.adapter.UserAdapter
import com.example.librarymanager.dao.UserDAO
import com.example.librarymanager.databinding.FragmentCustomBinding
import com.example.librarymanager.dialog.DeleteDialog

class UserFrg : Fragment() {
    private lateinit var mBinding: FragmentCustomBinding
    private var userAdapter: UserAdapter? = null
    private lateinit var userDAO: UserDAO

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
        userDAO = UserDAO(requireActivity())
        userAdapter = UserAdapter(requireActivity(), {
            startActivity(Intent(this.context, UserManagerActivity::class.java).apply {
                putExtras(bundleOf("type" to 1, "idUser" to it.id))
            })
        }, {
            DeleteDialog(requireActivity()) {
                userDAO.delete(it.id.toString())
            }.show()
        })

        mBinding.rcv.adapter = userAdapter

        mBinding.fabAdd.setOnClickListener {
            startActivity(Intent(this.context, UserManagerActivity::class.java).apply {
                putExtras(bundleOf("type" to 0))
            })
        }
    }

    override fun onResume() {
        super.onResume()

        userAdapter?.submitData(userDAO.all)
    }
}