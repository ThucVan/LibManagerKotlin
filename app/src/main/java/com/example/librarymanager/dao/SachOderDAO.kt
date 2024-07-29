package com.example.librarymanager.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.librarymanager.database.DbHelper
import com.example.librarymanager.model.sachOrder

class SachOderDAO(context: Context?) {
    private val db: SQLiteDatabase

    init {
        val dbHelper = DbHelper(context)
        db = dbHelper.writableDatabase
    }

    fun insert(obj: sachOrder): Long {
        val values = ContentValues()
        values.put("tenSach", obj.tenSach)
        values.put("tenTacGia", obj.tenTacGia)
        values.put("nxb", obj.nxb)
        values.put("namXb", obj.namXb)
        values.put("price", obj.price)
        values.put("soLuong", obj.soluong)

        return db.insert("SachOder", null, values)
    }

    fun update(obj: sachOrder): Int {
        val values = ContentValues()
        values.put("tenSach", obj.tenSach)
        values.put("tenTacGia", obj.tenTacGia)
        values.put("nxb", obj.nxb)
        values.put("namXb", obj.namXb)
        values.put("price", obj.price)
        values.put("soLuong", obj.soluong)

        return db.update(
            "SachOder",
            values,
            "maSachOrder=?",
            arrayOf<String>(java.lang.String.valueOf(obj.maOrder))
        )
    }

    fun delete(id: String): Int {
        return db.delete("SachOder", "maSachOrder=?", arrayOf(id))
    }

    val all: List<sachOrder>
        //get tat ca data
        get() {
            val sql = "SELECT * FROM SachOder"
            return getData(sql)
        }

    //get data theo id
    fun getID(id: String?): sachOrder {
        val sql = "SELECT * FROM SachOder WHERE maSachOrder=?"
        val list: List<sachOrder> = getData(sql, id!!)
        return list[0]
    }

    //get data nhieu tham so
    @SuppressLint("Range")
    private fun getData(sql: String, vararg selectionArgs: String): List<sachOrder> {
        val list: MutableList<sachOrder> = ArrayList()
        val c: Cursor = db.rawQuery(sql, selectionArgs)
        while (c.moveToNext()) {
            val obj = sachOrder()
            obj.maOrder = c.getInt(c.getColumnIndex("maSachOrder"))
            obj.tenSach = c.getString(c.getColumnIndex("tenSach"))
            obj.tenTacGia = c.getString(c.getColumnIndex("tenTacGia"))
            obj.nxb = c.getString(c.getColumnIndex("nxb"))
            obj.namXb = c.getInt(c.getColumnIndex("namXb"))
            obj.soluong = c.getInt(c.getColumnIndex("soLuong"))
            obj.price = c.getInt(c.getColumnIndex("price"))
            list.add(obj)
        }
        return list
    }
}
