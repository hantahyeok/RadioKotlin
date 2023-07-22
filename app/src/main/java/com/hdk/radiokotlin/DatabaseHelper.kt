package com.hdk.radiokotlin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.airbnb.lottie.animation.content.Content
import com.hdk.radiokotlin.Constants.C_NAME
import com.hdk.radiokotlin.Constants.TABLE_NAME

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

    fun deleteData(rname: String){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$C_NAME = ?", arrayOf(rname))
    }

    fun search(name: String){

    }
// SELECT COUNT(0) FROM TABLE_NAME WHERE C_NAME = name
    fun getAllData(): String {
        var result = "No data in DB"

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME, null)

        try {
            if (cursor.count != 0) {
                val stringBuffer = StringBuffer()
                while (cursor.moveToNext()) {
                    stringBuffer.append("ID :" + cursor.getInt(0).toString() + "\n")
                    stringBuffer.append("NAME :" + cursor.getString(1) + "\n")
                    stringBuffer.append("URL1 :" + cursor.getString(2) + "\n")
                    stringBuffer.append("URL2 :" + cursor.getString(3) + "\n")
                    stringBuffer.append("ICON :" + cursor.getString(4) + "\n")
                }
                result = stringBuffer.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return result
    }


//    fun searchRecords(query: String): ArrayList<ModelRecord>

}