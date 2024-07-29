package com.example.librarymanager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.librarymanager.dao.UserDAO
import com.example.librarymanager.databinding.FragmentChangePassBinding

class ChangePassFragment : Fragment() {
    private lateinit var mBinding: FragmentChangePassBinding
    private lateinit var userDAO: UserDAO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_change_pass, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDAO = UserDAO(requireContext())

        mBinding.btnCancel.setOnClickListener {
            mBinding.edPassOld.setText("")
            mBinding.edPassChange.setText("")
            mBinding.edRePassChange.setText("")
        }

        mBinding.btnSave.setOnClickListener { //doc user, pass trong SharedPreferences
            val pref = requireActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE)
            val user = pref.getString("USERNAME", "")
            if (validate() > 0) {
                val user = userDAO.getID(user)
                user.password = mBinding.edPassChange.getText().toString()
                userDAO.update(user)
                if (userDAO.update(user) > 0) {
                    Toast.makeText(activity, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT)
                        .show()
                    mBinding.edPassOld.setText("")
                    mBinding.edPassChange.setText("")
                    mBinding.edRePassChange.setText("")
                } else {
                    Toast.makeText(activity, "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun validate(): Int {
        var check = 1
        if (mBinding.edPassOld.getText()?.length == 0 || mBinding.edPassChange.getText()?.length == 0 || mBinding.edRePassChange.getText()?.length == 0) {
            Toast.makeText(context, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            check = -1
        } else {
            //doc user, pass trong SharedPreferences
            val pref = requireActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE)
            val passOld = pref.getString("PASSWORD", "")
            val pass: String = mBinding.edPassChange.getText().toString()
            val rePass: String = mBinding.edRePassChange.getText().toString()
            if (passOld != mBinding.edPassOld.getText().toString()) {
                Toast.makeText(context, "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show()
                check = -1
            }
            if (pass != rePass) {
                Toast.makeText(context, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show()
                check = -1
            }
        }
        return check
    }

}