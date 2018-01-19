package com.wuruoye.ichp.base.model.bean

import org.json.JSONObject

/**
 * Created by wuruoye on 2017/12/24.
 * this file is to
 */
data class Result(
        val result: Boolean,
        val info: String,
        val code: Int
){
    companion object {
        fun parseData(data: String): Result{
            val json = JSONObject(data)
            val result = json.getBoolean("result")
            val info = json.getString("info")
            val code = json.getInt("code")
            return Result(result, info, code)
        }
    }
}