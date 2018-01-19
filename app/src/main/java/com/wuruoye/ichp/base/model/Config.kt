package com.wuruoye.ichp.base.model

import android.Manifest
import android.os.Environment

/**
 * Created by wuruoye on 2017/9/15.
 * this file is to do
 */
object Config {
    private val REMOTE_HOST = "https://ddp.wuruoye.com/"
    val USER_REGISTER = REMOTE_HOST + "user/register_user"
    val USER_LOGIN = REMOTE_HOST + "user/login_user"
    val USER_LOGOUT = REMOTE_HOST + "user/logout_user"
    val USER_AVATAR = REMOTE_HOST + "user/avatar"
    val USER_TEST = REMOTE_HOST + "user/test"

    val COURSE_ADD_EA = REMOTE_HOST + "course/add_ea"
    val COURSE_ADD_LABEL = REMOTE_HOST + "course/add_label"
    val COURSE_ADD_EA_LABEL = REMOTE_HOST + "course/add_ea_label"
    val COURSE_ADD_EA_LABEL_LIST = REMOTE_HOST + "course/add_ea_label_list"
    val COURSE_QUERY_LABEL = REMOTE_HOST + "course/query_label"
    val COURSE_QUERY_LABEL_EA = REMOTE_HOST + "course/query_label_ea"
    val COURSE_DELETE_EA = REMOTE_HOST + "course/delete_ea"
    val COURSE_DELETE_LABEL = REMOTE_HOST + "course/delete_label"

    val CONNECT_TIME_OUT = 30L
    val READ_TIME_OUT = 30L

    private val APP_PATH = Environment.getExternalStorageDirectory().absolutePath + "/com.wuruoye.ichp/"
    val FILE_PATH = APP_PATH + "file/"
    val IMAGE_PATH = Environment.getExternalStorageDirectory().absolutePath + "/DCIM/Camera/"
    val PROVIDER_AUTHORITY = "com.wuruoye.ichp.fileprovider"
    val FILE_PERMISSION = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val CAMERA_PERMISSION = arrayOf(
            Manifest.permission.CAMERA
    )
}