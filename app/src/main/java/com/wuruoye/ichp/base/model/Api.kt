package com.wuruoye.ichp.base.model

/**
 * @Created : wuruoye
 * @Date : 2018/3/22.
 * @Description : 网络请求所需的 API 接口
 */
object Api {
    private const val REMOTE_HOST = "http://ichp.wuruoye.com/"

    val USER_INFO = REMOTE_HOST + "getUserInfo"
    val COLLECT_RECORD = REMOTE_HOST + "collRec"
    val LOGIN = REMOTE_HOST + "login"
    val UPLOAD = REMOTE_HOST + "upload"
    val REGISTER = REMOTE_HOST + "register"
    val STORE_USER_INFO = REMOTE_HOST + "storeInfo"
    val ADD_ENTRY = REMOTE_HOST + "addEntry"
    val MODIFY_ENTRY = REMOTE_HOST + "modifyEntry"
    val SEARCH_ENTRY = REMOTE_HOST + "searchEntry"
    val COLLECT_ENTRY = REMOTE_HOST + "collEntry"
    val GET_ENTRY = REMOTE_HOST + "getEntry"
    val ADD_REC = REMOTE_HOST + "addRec"
    val MODIFY_REC = REMOTE_HOST + "modifyRec"
    val DELETE_REC = REMOTE_HOST + "delRec"
    val GET_ALL_REC = REMOTE_HOST + "getAllRec"
    val GET_REC = REMOTE_HOST + "getRec"
    val SEARCH_REC = REMOTE_HOST + "searchRec"
    val ISSUE_ACT = REMOTE_HOST + "issueAct"
    val DELETE_ACT = REMOTE_HOST + "delAct"
    val SEARCH_ACT = REMOTE_HOST + "Act"
    val GET_ALL_ACT = REMOTE_HOST + "getAllAct"
    val GET_USER_ACT = REMOTE_HOST + "getUserAct"
    val COLLECT_ACT = REMOTE_HOST + "collACT"
    val GET_ACT = REMOTE_HOST + "getAct"
    val SEARCH_USER_INFO = REMOTE_HOST + "searchUserInfo"
    val GET_ATTENTION = REMOTE_HOST + "getMyConc"
    val PRAISE_REC = REMOTE_HOST + "apprRec"
    val COMMENT_REC = REMOTE_HOST + "commRec"
    val GET_COMMENT_REC = REMOTE_HOST + "getCommRec"
    val DELETE_COMMENT_REC = REMOTE_HOST + "delCommRec"
    val COMMENT_COMMENT = REMOTE_HOST + "commComm"
    val DELETE_COMMENT_COMMENT = REMOTE_HOST + "delCommComm"
}