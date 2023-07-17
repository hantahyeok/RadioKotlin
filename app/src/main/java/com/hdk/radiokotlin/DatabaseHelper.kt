package com.hdk.radiokotlin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.airbnb.lottie.animation.content.Content

class DatabaseHelper(context: Context?):SQLiteOpenHelper(
    context,
    Constants.DB_NAME,
    null,
    Constants.DB_VERSIONS
)  {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(Constants.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS" + Constants.TABLE_NAME)
        onCreate(db)
    }

    fun insertInfo(
        rname: String?,
        rurl1: String?,
        rurl2: String?,
        ricon: String?,
        iptdate: String?
    ): Long{
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(Constants.C_NAME, rname)
        values.put(Constants.C_URL1, rurl1)
        values.put(Constants.C_URL2, rurl2)
        values.put(Constants.C_ICON, ricon)
        values.put(Constants.C_ADD_TIMESTAMP, iptdate)

        val id = db.insert(Constants.TABLE_NAME, null, values)
        db.close()
        return id
    }    
}