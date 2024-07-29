package com.example.librarymanager.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context?) : SQLiteOpenHelper(context, dbName, null, dbVersion) {
    override fun onCreate(db: SQLiteDatabase) {
        //Tao bang User
        val createTableUser = "create table User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL, hoten TEXT NOT NULL, gioiTinh INTEGER NOT NULL, sdt TEXT NOT NULL, email TEXT NOT NULL, diachi TEXT NOT NULL)"

        db.execSQL(createTableUser)
        // Tao bang Thanh Vien
        val createTableThanhVien =
            "create table ThanhVien (" +
                    "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "hoTen TEXT NOT NULL, " +
                    "email TEXT NOT NULL, gioiTinh INTEGER NOT NULL, diaChi TEXT NOT NULL, sdt TEXT NOT NULL)"


        db.execSQL(createTableThanhVien)

        // Tao bang Loai Sach
        val createTableLoaiSach =
            "create table LoaiSach (" +
                    "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tenLoai TEXT NOT NULL)"
        db.execSQL(createTableLoaiSach)

        // Tao bang Sach
        val createTableSach =
            "create table Sach(" +
                    "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tenSach TEXT NOT NULL, " +
                    "giaThue INTEGER NOT NULL, " +
                    "nxb TEXT NOT NULL, " +
                    "tacGia TEXT NOT NULL, " +
                    "namXB INTEGER NOT NULL, " +
                    "image TEXT NOT NULL, " +
                    "soLuong INTEGER NOT NULL, " +
                    "maLoai INTEGER REFERENCES LoaiSach(maLoai))"
        db.execSQL(createTableSach)

        // Tao bang SachOrder
        val createTableSachOrder =
            "create table SachOder(" +
                    "maSachOrder INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tenSach TEXT NOT NULL, " +
                    "tenTacGia TEXT NOT NULL, " +
                    "nxb TEXT NOT NULL, " +
                    "namXb TEXT NOT NULL, " +
                    "price INTEGER NOT NULL, " +
                    "soLuong INTEGER NOT NULL)"
        db.execSQL(createTableSachOrder)

        // Tao bang Phieu Muon
        val createTablePhieuMuon =
            "create table PhieuMuon (" +
                    "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "maTT TEXT REFERENCES ThuThu(maTT), " +
                    "maTV INTEGER REFERENCES ThanhVien(maTV), " +
                    "maSach INTEGER REFERENCES Sach(maSach), " +
                    "ngayThue DATE NOT NULL, " +
                    "ngayTra DATE, " +
                    "tienThue INTEGER NOT NULL, " +
                    "traSach INTEGER," +
                    "soLuong INTEGER NOT NULL)"
        db.execSQL(createTablePhieuMuon)
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("drop table if exists User")
        db.execSQL("drop table if exists ThanhVien")
        db.execSQL("drop table if exists LoaiSach")
        db.execSQL("drop table if exists Sach")
        db.execSQL("drop table if exists SachOder")
        db.execSQL("drop table if exists PhieuMuon")

        onCreate(db)
    }

    companion object {
        const val dbName: String = "LIBRARY_MANAGER"
        const val dbVersion: Int = 1
    }
}
