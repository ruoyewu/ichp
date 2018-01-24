package com.wuruoye.ichp.base.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by wuruoye on 2017/11/5.
 * this file is to do sql operator
 */

class SQLHelper : SQLiteOpenHelper {
    constructor(context: Context) : super(context, DB_NAME, null, DB_VERSION) {}

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_ARTICLE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        val CREATE_ARTICLE_TABLE = "create table ARTICLE (" +
                "id integer primary key autoincrement," +
                "userid integer not null," +
                "content text not null)"

        val DB_NAME = "com.wuruoye.all2.db"
        val DB_VERSION = 1
        val ARTICLE_TABLE = "ARTICLE"
    }
}
