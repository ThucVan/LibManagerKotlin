package com.example.librarymanager.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.librarymanager.database.DbHelper
import com.example.librarymanager.model.sach

class SachDAO(context: Context?) {
    private val db: SQLiteDatabase

    init {
        val dbHelper = DbHelper(context)
        db = dbHelper.writableDatabase
    }

    fun insert(obj: sach): Long {
        val values = ContentValues()
        values.put("tenSach", obj.tenSach)
        values.put("giaThue", obj.giaThue)
        values.put("nxb", obj.nxb)
        values.put("tacGia", obj.tacGia)
        values.put("namXB", obj.namXb)
        values.put("image", obj.image)
        values.put("soLuong", obj.soluong)
        values.put("maLoai", obj.maLoai)

        return db.insert("Sach", null, values)
    }

    fun update(obj: sach): Int {
        val values = ContentValues()
        values.put("tenSach", obj.tenSach)
        values.put("giaThue", obj.giaThue)
        values.put("nxb", obj.nxb)
        values.put("tacGia", obj.tacGia)
        values.put("namXB", obj.namXb)
        values.put("image", obj.image)
        values.put("soLuong", obj.soluong)
        values.put("maLoai", obj.maLoai)

        return db.update(
            "Sach",
            values,
            "maSach=?",
            arrayOf<String>(java.lang.String.valueOf(obj.maSach))
        )
    }

    fun delete(id: String): Int {
        return db.delete("Sach", "maSach=?", arrayOf(id))
    }

    val all: List<sach>
        //get tat ca data
        get() {
            val sql = "SELECT * FROM Sach"
            return getData(sql)
        }

    //get data theo id
    fun getID(id: String?): sach {
        val sql = "SELECT * FROM Sach WHERE maSach=?"
        val list: List<sach> = getData(sql, id!!)
        return list[0]
    }

    //get data nhieu tham so
    @SuppressLint("Range")
    private fun getData(sql: String, vararg selectionArgs: String): List<sach> {
        val list: MutableList<sach> = ArrayList()
        val c: Cursor = db.rawQuery(sql, selectionArgs)
        while (c.moveToNext()) {
            val obj = sach()
            obj.maSach = c.getInt(c.getColumnIndex("maSach"))
            obj.tenSach = c.getString(c.getColumnIndex("tenSach"))
            obj.giaThue = c.getInt(c.getColumnIndex("giaThue"))
            obj.nxb = c.getString(c.getColumnIndex("nxb"))
            obj.tacGia = c.getString(c.getColumnIndex("tacGia"))
            obj.namXb = c.getInt(c.getColumnIndex("namXB"))
            obj.image = c.getString(c.getColumnIndex("image"))
            obj.soluong = c.getInt(c.getColumnIndex("soLuong"))
            obj.maLoai = c.getInt(c.getColumnIndex("maLoai"))
            list.add(obj)
        }
        return list
    }
}
