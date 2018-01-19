package com.wuruoye.ichp.base.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase

/**
 * Created by wuruoye on 2017/11/5.
 * this file is to do
 */

object SQLiteUtil {
    private val READ_DATABASE = 1
    private val WRITE_DATABASE = 2

    private val ADD_ARTICLE = "insert into ${SQLHelper.ARTICLE_TABLE} (userid, content) values (?, ?)"
    private val QUERY_ARTICLE = "select * from ${SQLHelper.ARTICLE_TABLE} where userid = ? order by id desc"
    private val QUERY_ARTICLE_CONTENT = "select * from ${SQLHelper.ARTICLE_TABLE} where userid = ? and content = ?"
    private val DELETE_ARTICLE_ID = "delete from ${SQLHelper.ARTICLE_TABLE} where id = ?"


}
