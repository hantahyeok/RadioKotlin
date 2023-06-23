package com.hdk.radiokotlin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "bookmark.db"
        private const val DATABASE_VERSION = 1

        // 테이블 및 컬럼 이름 정의
        const val TABLE_NAME = "bookmarks"
        private const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_FAVICON = "favicon"
        const val COLUMN_URL = "url"
        const val COLUMN_URL_RESOLVED = "url_resolved"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // 테이블 생성 쿼리 실행
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_FAVICON TEXT, $COLUMN_URL TEXT, $COLUMN_URL_RESOLVED TEXT);"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 기존 테이블 삭제 후 다시 생성 (단순히 예제이므로 간단하게 처리하였습니다)
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME;")
        onCreate(db)
    }
}