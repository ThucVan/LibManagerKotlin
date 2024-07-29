package com.example.librarymanager.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.librarymanager.database.DbHelper
import com.example.librarymanager.model.thanhVien

class ThanhVienDAO(context: Context?) {
    private val db: SQLiteDatabase

    init {
        val dbHelper: DbHelper = DbHelper(context)
        db = dbHelper.writableDatabase
    }

    fun insert(obj: thanhVien): Long {
        val values = ContentValues()
        values.put("hoTen", obj.hoTen)
        values.put("email", obj.email)
        values.put("gioiTinh", obj.gioiTinh)
        values.put("diaChi", obj.diaChi)
        values.put("sdt", obj.sdt)

        return db.insert("ThanhVien", null, values)
    }

    fun update(obj: thanhVien): Int {
        val values = ContentValues()
        values.put("hoTen", obj.hoTen)
        values.put("email", obj.email)
        values.put("gioiTinh", obj.gioiTinh)
        values.put("diaChi", obj.diaChi)
        values.put("sdt", obj.sdt)

        return db.update(
            "ThanhVien",
            values,
            "maTV=?",
            arrayOf<String>(java.lang.String.valueOf(obj.maTV))
        )
    }

    fun delete(id: String): Int {
        return db.delete("ThanhVien", "maTV=?", arrayOf(id))
    }

    val all: List<thanhVien>
        //get tat ca data
        get() {
            val sql = "SELECT * FROM ThanhVien"
            return getData(sql)
        }

    //get data theo id
    fun getID(id: String?): thanhVien {
        val sql = "SELECT * FROM ThanhVien WHERE maTV=?"
        val list: List<thanhVien> = getData(sql, id!!)
        return list[0]
    }

    //get data nhieu tham so
    @SuppressLint("Range")
    private fun getData(sql: String, vararg selectionArgs: String): List<thanhVien> {
        val list: MutableList<thanhVien> = ArrayList<thanhVien>()
        val c: Cursor = db.rawQuery(sql, selectionArgs)
        while (c.moveToNext()) {
            val obj = thanhVien()
            obj.maTV = c.getInt(c.getColumnIndex("maTV"))
            obj.hoTen = c.getString(c.getColumnIndex("hoTen"))
            obj.gioiTinh = c.getInt(c.getColumnIndex("gioiTinh"))
            obj.email = c.getString(c.getColumnIndex("email"))
            obj.diaChi = c.getString(c.getColumnIndex("diaChi"))
            obj.sdt = c.getString(c.getColumnIndex("sdt"))
            list.add(obj)
        }
        return list
    }
}
