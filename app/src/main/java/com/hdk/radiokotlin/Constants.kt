package com.hdk.radiokotlin

object Constants {
    const val DB_NAME = "RADIODB"
    const val DB_VERSIONS = 1
    const val TABLE_NAME = "bookmark"
    const val C_ID = "bidx"
    const val C_NAME = "rname"
    const val C_URL1 = "rurl1"
    const val C_URL2 = "rurl2"
    const val C_ICON = "ricon"
    const val C_ADD_TIMESTAMP = "iptdate"

    const val CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME + " ("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_NAME + " TEXT,"
            + C_URL1 + " TEXT,"
            + C_URL2 + " TEXT,"
            + C_ICON + " TEXT,"
            + C_ADD_TIMESTAMP + " TEXT"
            + ")")
}