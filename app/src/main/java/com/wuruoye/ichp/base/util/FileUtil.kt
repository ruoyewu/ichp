package com.wuruoye.ichp.base.util

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * Created by wuruoye on 2017/9/26.
 * this file is to do file operator
 */
object FileUtil {
    fun checkDirectory(directory: String): Boolean {
        val file = File(directory)
        return file.isDirectory
    }

    fun checkFile(filePath: String): Boolean {
        val file = File(filePath)
        return file.isFile
    }

    fun checkAvailable(filePath: String) {
        val file = File(filePath)
        if (filePath.endsWith("/")) {
            if (!file.exists()) {
                file.mkdirs()
            }
        }else {
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
        }
    }

    fun createFile(filePath: String): File {
        checkAvailable(filePath)
        return File(filePath)
    }

    fun removeFile(filePath: String): Boolean {
        val file = File(filePath)
        return if (file.isFile) {
            file.delete()
        }else {
            !file.isDirectory
        }
    }

    fun saveBitmap(bitmap: Bitmap, fileName: String): String{
        val file = createFile(fileName)
        val fos = FileOutputStream(file)

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()

        return file.canonicalPath
    }

    fun writeFile(filePath: String, inStream: InputStream){
        val fos = FileOutputStream(filePath)
        val buf = ByteArray(1024)
        var len: Int

        while (true){
            len = inStream.read(buf)
            if (len != -1){
                fos.write(buf, 0, len)
            }else{
                break
            }
        }
        fos.flush()
        inStream.close()
    }
}