package com.example.librarymanager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.AdapterViewBindingAdapter
import com.example.librarymanager.adapter.SachSpinnerAdapter
import com.example.librarymanager.adapter.ThanhVienSpinnerAdapter
import com.example.librarymanager.dao.PhieuMuonDAO
import com.example.librarymanager.dao.SachDAO
import com.example.librarymanager.dao.ThanhVienDAO
import com.example.librarymanager.databinding.PhieuMuonManagerActivityBinding
import com.example.librarymanager.model.phieuMuon
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDate

class PhieuMuonManagerActivity : AppCompatActivity() {
    private lateinit var mBinding: PhieuMuonManagerActivityBinding
    private lateinit var phieuMuonDAO: PhieuMuonDAO
    private lateinit var sachDAO: SachDAO
    private lateinit var thanhVienDAO: ThanhVienDAO

    var sdf: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.phieu_muon_manager_activity)

        phieuMuonDAO = PhieuMuonDAO(this)
        sachDAO = SachDAO(this)
        thanhVienDAO = ThanhVienDAO(this)

        val idPhieuMuon = intent.getIntExtra("idPhieuMuon", -1)
        val type = intent.getIntExtra("type", 0)

        mBinding.tvTitle.text = if (type == 0) "Thêm phiếu mượn" else "Sửa phiếu mượn"

        val listSach = sachDAO.all
        val listTv = thanhVienDAO.all
        var idLoaiSach = 0
        var idTv = 0

        mBinding.spMaSach.adapter = SachSpinnerAdapter(this, listSach)
        mBinding.spMaSach.onItemSelectedListener =
            object : AdapterViewBindingAdapter.OnItemSelected,
                AdapterView.OnItemSelectedListener {
                @SuppressLint("RestrictedApi")
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    id: Long
                ) {
                    idLoaiSach = listSach[position].maLoai
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        mBinding.spMaTv.adapter = ThanhVienSpinnerAdapter(this, listTv)
        mBinding.spMaTv.onItemSelectedListener =
            object : AdapterViewBindingAdapter.OnItemSelected,
                AdapterView.OnItemSelectedListener {
                @SuppressLint("RestrictedApi")
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    id: Long
                ) {
                    idTv = listTv[position].maTV
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        if (idPhieuMuon != -1) {
            val phieuMuon = phieuMuonDAO.getID(idPhieuMuon.toString())
            for (i in listSach.indices) {
                if (phieuMuon.maSach == listSach[i].maSach) {
                    mBinding.spMaSach.setSelection(i)
                    break
                }
            }

            for (i in listSach.indices) {
                if (phieuMuon.maTV == listTv[i].maTV) {
                    mBinding.spMaTv.setSelection(i)
                    break
                }
            }

            mBinding.ckTraSach.visibility = View.VISIBLE
            mBinding.ckTraSach.isChecked = phieuMuon.traSach == 1
            mBinding.edPrice.setText(phieuMuon.tienThue.toString())
            mBinding.edSl.setText(phieuMuon.soLuong.toString())
        }

        mBinding.btnSave.setOnClickListener {
            val price = mBinding.edPrice.text.toString()
            val sl = mBinding.edSl.text.toString()

            if (type == 0) {
                phieuMuonDAO.insert(
                    phieuMuon(
                        maTV = idTv,
                        maSach = idLoaiSach,
                        tienThue = price.toInt(),
                        soLuong = sl.toInt(),
                        ngayMuon = Date.valueOf(
                            LocalDate.now().toString()
                        )
                    )
                )

                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                phieuMuonDAO.update(
                    phieuMuon(
                        maPM = idPhieuMuon,
                        maTV = idTv,
                        maSach = idLoaiSach,
                        tienThue = price.toInt(),
                        soLuong = sl.toInt(),
                        ngayMuon = Date.valueOf(
                            LocalDate.now().toString()
                        ),
                        ngayTra = if (mBinding.ckTraSach.isChecked) Date.valueOf(
                            LocalDate.now().toString()
                        ) else null,
                        traSach = if (mBinding.ckTraSach.isChecked) 1 else 0
                    )
                )

                Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        mBinding.btnCancel.setOnClickListener {
            mBinding.edPrice.setText("")
            mBinding.edSl.setText("")
        }

        mBinding.imvBack.setOnClickListener {
            finish()
        }
    }
}