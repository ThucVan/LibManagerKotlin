package com.example.librarymanager.model

import java.util.Date

data class phieuMuon (
    var maPM: Int = 0,
    var maTV: Int = 0,
    var maSach: Int = 0,
    var ngayMuon: Date? = null,
    var ngayTra: Date? = null,
    var tienThue: Int = 0,
    var traSach: Int = 0,
    var soLuong : Int = 1,
)
