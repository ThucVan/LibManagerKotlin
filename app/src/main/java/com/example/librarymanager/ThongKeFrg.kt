package com.example.librarymanager

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.librarymanager.adapter.PhieuMuonAdapter
import com.example.librarymanager.dao.PhieuMuonDAO
import com.example.librarymanager.databinding.ThongKeFrgBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class ThongKeFrg : Fragment() {
    private lateinit var mBinding: ThongKeFrgBinding
    private var phieuMuonAdapter: PhieuMuonAdapter? = null
    private lateinit var phieuMuonDAO: PhieuMuonDAO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.thong_ke_frg, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phieuMuonDAO = PhieuMuonDAO(requireContext())
        phieuMuonAdapter = PhieuMuonAdapter(requireActivity(), {}, {}, true)
        phieuMuonAdapter?.sendMail = {
            try {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.setData(Uri.parse("mailto:"))
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(it))
                intent.putExtra(Intent.EXTRA_SUBJECT, "Đã quá hạn trả sách")
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(activity, "Không tìm thấy ứng dụng email", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.rcvPhieuMuon.adapter = phieuMuonAdapter

        mBinding.btnFind.setOnClickListener {
            phieuMuonAdapter?.submitData(phieuMuonDAO.searchByIDTv(mBinding.edSearh.text.toString()))

            if (phieuMuonDAO.searchByIDTv(mBinding.edSearh.text.toString()).isEmpty()){
                Toast.makeText(activity, "Id thành viên sai hoặc không có sách nào mượn quá hạn", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        phieuMuonAdapter?.submitData(phieuMuonDAO.chuaTraSach)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(phieuMuonDAO.traSach.size.toFloat(), "Đã trả"))
        entries.add(
            PieEntry(
                phieuMuonDAO.all.size - phieuMuonDAO.traSach.size.toFloat(),
                "Chưa trả"
            )
        )

        // Thiết lập PieDataSet
        val dataSet = PieDataSet(entries, "Thống kê phiếu mượn")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.asList()

        // Thiết lập PieData
        val data = PieData(dataSet)

        // Cấu hình PieChart
        mBinding.pieChart.data = data
        mBinding.pieChart.description.isEnabled = false
        mBinding.pieChart.setCenterTextSize(22f)
        mBinding.pieChart.animateY(1000)
    }
}