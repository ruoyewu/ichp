package com.wuruoye.ichp.base.util

import com.wuruoye.ichp.base.model.Config
import com.wuruoye.ichp.base.model.Listener
import okhttp3.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by wuruoye on 2017/9/16.
 * this file is to do net request
 */
object NetUtil {
    private var mCookies = arrayListOf<Cookie>()
    //初始化唯一的一个client
    private val client = OkHttpClient.Builder()
            .connectTimeout(Config.CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .cookieJar(object : CookieJar{
                override fun saveFromResponse(url: HttpUrl?, cookies: MutableList<Cookie>?) {
                    mCookies.clear()
                    if (cookies != null) {
                        mCookies.addAll(cookies)
                    }
                }

                override fun loadForRequest(url: HttpUrl?): MutableList<Cookie> {
                    return mCookies
                }

            })
//            .readTimeout(Config.READ_TIME_OUT, TimeUnit.SECONDS)
            .build()!!

    /**
     * get 请求
     * @url 请求url
     */
    fun get(url: String, listener: Listener<String>){
        val request = Request.Builder()
                .url(url)
                .build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                listener.onFail(e?.message!!)
            }

            override fun onResponse(call: Call?, response: Response?) {
                if (response!!.isSuccessful){
                    listener.onSuccess(response.body()!!.string())
                }else{
                    listener.onFail("网络请求错误：" + response.message())
                }
            }

        })
    }

    /**
     * post 请求
     * @param keyList 请求参数key
     * @param valueList 请求参数值
     * 必须保证两个list的大小完全一致
     */
    fun post(url: String, keyList: List<String>,
             valueList: List<String>, listener: Listener<String>){
        val requestBody = FormBody.Builder()
        for (i in 0 until keyList.size){
            requestBody.add(keyList[i], valueList[i])
        }
        val request = Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                listener.onFail(e!!.message!!)
            }

            override fun onResponse(call: Call?, response: Response?) {
                if (response!!.isSuccessful){
                    listener.onSuccess(response.body()!!.string())
                }else{
                    listener.onFail("网络请求错误：" + response.message())
                }
            }

        })
    }

    fun postFile(url: String, filePath: String, fileName: String,
                 fileType: String, keyList: List<String>,
                 valueList: List<String>, listener: Listener<String>){
        val file = File(filePath)
        val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(fileName, file.name,
                        RequestBody.create(MediaType.parse(fileType), file))
        for (i in 0 until keyList.size){
            val key = keyList[i]
            val value = valueList[i]
            requestBody.addFormDataPart(key, value)
        }

        val request = Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                listener.onFail(e!!.message!!)
            }

            override fun onResponse(call: Call?, response: Response?) {
                if (response!!.isSuccessful){
                    listener.onSuccess(response.body()!!.string())
                }else{
                    listener.onFail(response.message())
                }
            }

        })
    }

    fun downloadFile(url: String, filePath: String, listener: Listener<String>){
        val request = Request.Builder()
                .url(url)
                .build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                listener.onSuccess(e!!.message!!)
            }

            override fun onResponse(call: Call?, response: Response?) {
                val inStream = response!!.body()!!.byteStream()
                FileUtil.writeFile(filePath, inStream)
            }

        })
    }
}