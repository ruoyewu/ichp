package com.wuruoye.ichp.base.util

import android.graphics.Bitmap
import com.wuruoye.ichp.base.model.Config
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * Created by wuruoye on 2017/9/26.
 * this file is to do
 */
object FileUtil {

    fun createFile(filePath: String): File{
        checkDirectory()
        val file = File(filePath)
        file.createNewFile()
        return file
    }

    fun saveImage(bitmap: Bitmap, fileName: String): String{
        checkDirectory()

        val file = File(Config.IMAGE_PATH + fileName)
        val fos = FileOutputStream(file)

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()

        return file.canonicalPath
    }

    fun writeFile(filePath: String, inStream: InputStream){
        checkDirectory()

        val fos = FileOutputStream(filePath)
        val buf = ByteArray(1024)
        var len: Int

        while (true){
            len = inStream.read(buf)
            if (len != -1){
                fos.write(buf, 0, len)
            }else{
                break;
            }
        }
        fos.flush()
        inStream.close()
    }

    private fun checkDirectory(){
        val file = File(Config.FILE_PATH)
        val image = File(Config.IMAGE_PATH)

        if (!file.exists()){
            file.mkdirs()
        }
        if (!image.exists()){
            image.mkdirs()
        }
    }
}