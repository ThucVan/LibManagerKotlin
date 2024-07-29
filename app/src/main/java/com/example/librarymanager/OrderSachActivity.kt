package com.example.librarymanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.librarymanager.dao.SachDAO
import com.example.librarymanager.dao.SachOderDAO
import com.example.librarymanager.dao.ThanhVienDAO
import com.example.librarymanager.databinding.OrderSachActivityBinding
import com.example.librarymanager.model.sachOrder

class OrderSachActivity : AppCompatActivity() {
    private lateinit var mBinding: OrderSachActivityBinding
    private lateinit var sachOrderDAO: SachOderDAO
    private lateinit var sachDAO: SachDAO
    private lateinit var thanhVienDAO: ThanhVienDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.order_sach_activity)

        sachOrderDAO = SachOderDAO(this)
        sachDAO = SachDAO(this)
        thanhVienDAO = ThanhVienDAO(this)

        val idOrder = intent.getIntExtra("idOrderSach", -1)
        val type = intent.getIntExtra("type", 0)

        mBinding.tvTitle.text = if (type == 0) "Đặt Sách" else "Cập Nhập Đặt Sách"

        if (idOrder != -1) {
            val order = sachOrderDAO.getID(idOrder.toString())
            mBinding.edNameBook.setText(order.tenSach)
            mBinding.edTacGia.setText(order.tenTacGia)
            mBinding.edNxb.setText(order.nxb)
            mBinding.edNamNxb.setText(order.namXb.toString())
            mBinding.edPrice.setText(order.price.toString())
            mBinding.edSl.setText(order.soluong.toString())
        }


        mBinding.btnCancel.setOnClickListener {
            mBinding.edNameBook.setText("")
            mBinding.edTacGia.setText("")
            mBinding.edNxb.setText("")
            mBinding.edNamNxb.setText("")
            mBinding.edPrice.setText("")
            mBinding.edSl.setText("")
        }

        mBinding.btnSave.setOnClickListener {
            val nameBook = mBinding.edNameBook.text.toString()
            val nameTacGia = mBinding.edTacGia.text.toString()
            val edNxb = mBinding.edNxb.text.toString()
            val edNamXb = mBinding.edNamNxb.text.toString()
            val edPrice = mBinding.edPrice.text.toString()
            val edSL = mBinding.edSl.text.toString()

            if (nameBook.isNotEmpty() && nameTacGia.isNotEmpty() && edNxb.isNotEmpty() && edNamXb.isNotEmpty() && edPrice.isNotEmpty() && edSL.isNotEmpty()) {
                if (type == 0) {
                    sachOrderDAO.insert(
                        sachOrder(
                            tenSach = nameBook,
                            tenTacGia = nameTacGia,
                            nxb = edNxb,
                            namXb = edNamXb.toInt(),
                            price = edPrice.toInt(),
                            soluong = edSL.toInt()
                        )
                    )
                    Toast.makeText(this, "Thêm Thành công !", Toast.LENGTH_SHORT).show()
                } else {
                    sachOrderDAO.update(
                        sachOrder(
                            maOrder = idOrder,
                            tenSach = nameBook,
                            tenTacGia = nameTacGia,
                            nxb = edNxb,
                            namXb = edNamXb.toInt(),
                            price = edPrice.toInt(),
                            soluong = edSL.toInt()
                        )
                    )
                    Toast.makeText(this, "Sửa Thành công !", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, "Vui lòng điền đủ thông tin ! !", Toast.LENGTH_SHORT).show()
            }
        }

        mBinding.imvBack.setOnClickListener {
            finish()
        }
    }
}