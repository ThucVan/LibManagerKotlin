package com.example.librarymanager.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.librarymanager.database.DbHelper
import com.example.librarymanager.model.phieuMuon
import java.text.ParseException
import java.text.SimpleDateFormat

class PhieuMuonDAO(context: Context?) {
    private val db: SQLiteDatabase
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    init {
        val dbHelper = DbHelper(context)
        db = dbHelper.writableDatabase
    }

    fun insert(obj: phieuMuon): Long {
        val values = ContentValues()
        values.put("maTT", obj.maTV)
        values.put("maTV", obj.maTV)
        values.put("maSach", obj.maSach)
        values.put("ngayThue", sdf.format(obj.ngayMuon))
        if (obj.ngayTra != null){
            values.put("ngayTra", sdf.format(obj.ngayTra))
        }
        values.put("tienThue", obj.tienThue)
        values.put("traSach", obj.traSach)
        values.put("soLuong", obj.soLuong)

        return db.insert("PhieuMuon", null, values)
    }

    fun update(obj: phieuMuon): Int {
        val values = ContentValues()

        values.put("maTT", obj.maTV)
        values.put("maTV", obj.maTV)
        values.put("maSach", obj.maSach)
        values.put("ngayThue", sdf.format(obj.ngayMuon))
        if (obj.ngayTra != null){
            values.put("ngayTra", sdf.format(obj.ngayTra))
        }
        values.put("tienThue", obj.tienThue)
        values.put("traSach", obj.traSach)
        values.put("soLuong", obj.soLuong)

        return db.update(
            "PhieuMuon",
            values,
            "maPM=?",
            arrayOf<String>(java.lang.String.valueOf(obj.maPM))
        )
    }

    fun delete(id: String): Int {
        return db.delete("PhieuMuon", "maPM=?", arrayOf(id))
    }

    val all: List<phieuMuon>
        //get tat ca data
        get() {
            val sql = "SELECT * FROM PhieuMuon"
            return getData(sql)
        }

    val traSach : List<phieuMuon>
        get() {
            val sql = "SELECT * FROM PhieuMuon Where traSach = 1"
            return getData(sql)
        }

    val chuaTraSach : List<phieuMuon>
        get() {
            val sql = "SELECT * FROM PhieuMuon Where traSach = 0"
            return getData(sql)
        }

    fun searchByIDTv(id: String?): List<phieuMuon> {
        val sql = "SELECT * FROM PhieuMuon WHERE maTV=? AND traSach = 0"
        val list: List<phieuMuon> = getData(sql, id!!)
        return list
    }

    //get data theo id
    fun getID(id: String?): phieuMuon {
        val sql = "SELECT * FROM PhieuMuon WHERE maPM=?"
        val list: List<phieuMuon> = getData(sql, id!!)
        return list[0]
    }

    //get data nhieu tham so
    @SuppressLint("Range")
    private fun getData(sql: String, vararg selectionArgs: String): List<phieuMuon> {
        val list: MutableList<phieuMuon> = ArrayList()
        val c: Cursor = db.rawQuery(sql, selectionArgs)
        while (c.moveToNext()) {
            val obj = phieuMuon()
            obj.maPM = c.getInt(c.getColumnIndex("maPM"))
            obj.maTV = c.getInt(c.getColumnIndex("maTT"))
            obj.maTV = c.getInt(c.getColumnIndex("maTV"))
            obj.maSach = c.getInt(c.getColumnIndex("maSach"))
            try {
                obj.ngayMuon = sdf.parse(c.getString(c.getColumnIndex("ngayThue")))
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            try {
                obj.ngayTra = sdf.parse(c.getString(c.getColumnIndex("ngayTra")))
            } catch (e: Exception) {
                e.printStackTrace()
            }

            obj.tienThue = c.getInt(c.getColumnIndex("tienThue"))
            obj.traSach = c.getInt(c.getColumnIndex("traSach"))
            obj.soLuong = c.getInt(c.getColumnIndex("soLuong"))
            list.add(obj)
        }
        return list
    }
}
