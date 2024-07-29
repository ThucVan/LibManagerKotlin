package com.example.librarymanager.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.librarymanager.database.DbHelper
import com.example.librarymanager.model.loaiSach

class LoaiSachDAO(context: Context?) {
    private val db: SQLiteDatabase

    init {
        val dbHelper = DbHelper(context)
        db = dbHelper.writableDatabase
    }

    fun insert(obj: loaiSach): Long {
        val values = ContentValues()
        values.put("tenLoai", obj.tenLoai)


        return db.insert("LoaiSach", null, values)
    }

    fun update(obj: loaiSach): Int {
        val values = ContentValues()
        values.put("tenLoai", obj.tenLoai)


        return db.update("LoaiSach", values, "maLoai=?", arrayOf(obj.maLoai.toString()))
    }

    fun delete(id: Int): Int {
        return db.delete("LoaiSach", "maLoai=?", arrayOf(id.toString()))
    }

    val all: List<loaiSach>
        //get tat ca data
        get() {
            val sql = "SELECT * FROM LoaiSach"
            return getData(sql)
        }

    //get data theo id
    fun getID(id: String?): loaiSach {
        val sql = "SELECT * FROM LoaiSach WHERE maLoai=?"
        val list = getData(sql, id!!)
        return list[0]
    }

    //get data nhieu tham so
    @SuppressLint("Range")
    private fun getData(sql: String, vararg selectionArgs: String): List<loaiSach> {
        val list: MutableList<loaiSach> = ArrayList()
        val c = db.rawQuery(sql, selectionArgs)
        while (c.moveToNext()) {
            val obj = loaiSach()
            obj.maLoai = c.getString(c.getColumnIndex("maLoai")).toInt()
            obj.tenLoai = c.getString(c.getColumnIndex("tenLoai"))
            list.add(obj)
        }
        return list
    }
}
