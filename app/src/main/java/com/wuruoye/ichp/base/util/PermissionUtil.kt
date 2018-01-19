package com.wuruoye.ichp.base.util

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build

/**
 * Created by wuruoye on 2017/9/26.
 * this file is to do
 */
class PermissionUtil(val activity: Activity) {

    fun requestPermission(permissions: Array<String>): Boolean{
        var isOk = true
        if (Build.VERSION.SDK_INT >= 23) {
            for (i in 0 until permissions.size){
                if (activity.checkSelfPermission(permissions[i]) == PackageManager.PERMISSION_DENIED){
                    isOk = false
                    activity.requestPermissions(permissions, 0)
                }
            }
        }
        return isOk
    }
}