package com.example.librarymanager.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.librarymanager.database.DbHelper
import com.example.librarymanager.model.sach
import com.example.librarymanager.model.user

class UserDAO(context: Context?) {
    private val db: SQLiteDatabase

    init {
        val dbHelper = DbHelper(context)
        db = dbHelper.writableDatabase
    }

    fun insert(obj: user): Long {
        val values = ContentValues()
        values.put("id", obj.id)
        values.put("username", obj.username)
        values.put("password", obj.password)
        values.put("hoten", obj.hoTen)
        values.put("gioitinh", obj.gioiTinh)
        values.put("email", obj.email)
        values.put("sdt", obj.sdt)
        values.put("diachi", obj.diaChi)


        return db.insert("User", null, values)
    }

    fun update(obj: user): Int {
        val values = ContentValues()
        values.put("username", obj.username)
        values.put("password", obj.password)
        values.put("hoten", obj.hoTen)
        values.put("gioitinh", obj.gioiTinh)
        values.put("email", obj.email)
        values.put("sdt", obj.sdt)
        values.put("diachi", obj.diaChi)

        return db.update("User", values, "id=?", arrayOf(obj.id.toString()))
    }

    fun delete(id: String): Int {
        return db.delete("User", "id=?", arrayOf(id))
    }

    @SuppressLint("Range")
    private fun getData(sql: String, vararg selectionArgs: String): List<user> {
        val list: MutableList<user> = ArrayList()
        val c: Cursor? = db.rawQuery(sql, selectionArgs)

        if (c != null) {
            try {
                while (c.moveToNext()) {
                    val obj = user()
                    obj.id = c.getInt(c.getColumnIndex("id"))
                    obj.username = c.getString(c.getColumnIndex("username"))
                    obj.password = c.getString(c.getColumnIndex("password"))
                    obj.hoTen = c.getString(c.getColumnIndex("hoten"))
                    obj.gioiTinh = c.getInt(c.getColumnIndex("gioiTinh"))
                    obj.email = c.getString(c.getColumnIndex("email"))
                    obj.sdt = c.getString(c.getColumnIndex("sdt"))
                    obj.diaChi = c.getString(c.getColumnIndex("diachi"))
                    list.add(obj)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                c.close()
            }
        }

        return list
    }


    val all: List<user>
        //get tat ca data
        get() {
            val sql = "SELECT * FROM User"

            return getData(sql)
        }

    //get data theo id
    fun getID(id: String?): user {
        val sql = "SELECT * FROM User WHERE id=?"

        val list = getData(sql, id!!)

        return list[0]
    }

    //check login
    fun checkLogin(username: String, password: String): Int {
        val sql = "SELECT * FROM User WHERE username=? AND password=?"
        val list = getData(sql, username, password)
        if (list.size == 0) return -1
        return 1
    }
}
