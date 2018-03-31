package com.wuruoye.ichp.base

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.media.MediaRecorder
import android.media.MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.wuruoye.ichp.R
import com.wuruoye.ichp.base.model.Config
import com.wuruoye.ichp.base.util.*
import com.wuruoye.library.contract.WIPresenter
import com.wuruoye.library.ui.WPhotoActivity

/**
 * Created by wuruoye on 2017/9/26.
 * this file is to do
 */
abstract class MediaActivity<T : WIPresenter> : WPhotoActivity<T>() {
    private lateinit var filePath: String

    private lateinit var dlgRecord: AlertDialog
    private var mMediaRecorder: MediaRecorder? = null
    private lateinit var mRecordAnimator1: ValueAnimator
    private lateinit var mRecordAnimator2: ValueAnimator
    private lateinit var mRecordBack1: View
    private lateinit var mRecordBack2: View
    private lateinit var mRecordTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecordDialog()
    }

    @SuppressLint("InflateParams", "ClickableViewAccessibility")
    private fun initRecordDialog() {
        val recordView = LayoutInflater.from(this)
                .inflate(R.layout.dlg_record, null)
        mRecordTitle = recordView.findViewById(R.id.tv_record)
        mRecordBack1 = recordView.findViewById<View>(R.id.v_record_back_1)
        mRecordBack2 = recordView.findViewById<View>(R.id.v_record_back_2)
        initRecordAnimator(mRecordBack1, mRecordBack2)
        val ibRecord = recordView.findViewById<ImageButton>(R.id.iv_record)
        ibRecord.isClickable = true
        ibRecord.setOnTouchListener({ _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startRecord()
                }
                MotionEvent.ACTION_UP -> {
                    stopRecord()
                }
            }
            true
        })
        dlgRecord = AlertDialog.Builder(this)
                .setView(recordView)
                .create()
    }

    private fun initRecordAnimator(back1: View, back2: View) {
        mRecordAnimator1 = ValueAnimator.ofFloat(1F, 2F)
        mRecordAnimator2 = ValueAnimator.ofFloat(1F, 2F)
        mRecordAnimator1.duration = TIME_RECORD_ANIMATOR
        mRecordAnimator2.duration = TIME_RECORD_ANIMATOR
        mRecordAnimator1.repeatCount = -1
        mRecordAnimator2.repeatCount = -1
        mRecordAnimator1.repeatMode = ValueAnimator.REVERSE
        mRecordAnimator2.repeatMode = ValueAnimator.REVERSE
        mRecordAnimator1.addUpdateListener { animator ->
            val scale = animator.animatedValue as Float
            loge("scale 1: $scale")
            back1.scaleX = scale
            back1.scaleY = scale
        }
        mRecordAnimator2.addUpdateListener { animator ->
            val scale = animator.animatedValue as Float
            loge("scale 2: $scale")
            back2.scaleX = scale
            back2.scaleY = scale
        }
    }

    private fun startRecord() {
        if (mMediaRecorder != null) {
            mMediaRecorder!!.setOutputFile(filePath)
            mMediaRecorder!!.prepare()
            mMediaRecorder?.start()

            mRecordAnimator1.start()
            Handler().postDelayed({
                mRecordAnimator2.start()
            }, TIME_RECORD_ANIMATOR / 2)
            mRecordTitle.text = "正在录制中..."
        }else {
            toast("音频录制初始化失败...")
        }
    }

    private fun stopRecord() {
        if (mMediaRecorder != null) {
            mMediaRecorder!!.stop()
            mMediaRecorder!!.release()
            mMediaRecorder = null
            dlgRecord.dismiss()
            onPhotoBack(filePath)
        }

        mRecordAnimator1.cancel()
        mRecordAnimator2.cancel()
        mRecordBack1.scaleX = 1F
        mRecordBack1.scaleY = 1F
        mRecordBack2.scaleX = 1F
        mRecordBack2.scaleY = 1F
        mRecordTitle.text = "长按录制视频"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == VIDEO_CHOOSE && resultCode == Activity.RESULT_OK) {
            val filePath = FilePathUtil.getPathFromUri(applicationContext, data!!.data)!!
            onPhotoBack(filePath)
        }else if (requestCode == VIDEO_TAKE && resultCode == Activity.RESULT_OK) {
            val filePath = FilePathUtil.getPathFromUri(applicationContext, data!!.data)!!
            onPhotoBack(filePath)
        }else if (requestCode == RECORD_TAKE && resultCode == Activity.RESULT_OK) {
            val filePath = FilePathUtil.getPathFromUri(applicationContext, data!!.data)!!
            onPhotoBack(filePath)
        }else if (requestCode == RECORD_CHOOSE && resultCode == Activity.RESULT_OK) {
            val filePath = FilePathUtil.getPathFromUri(applicationContext, data!!.data)!!
            onPhotoBack(filePath)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun chooseVideo() {
        if (PermissionUtil(this).requestPermission(Config.FILE_PERMISSION)) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "video/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, PHOTO_CHOOSE)
        }
    }

    fun takeVideo() {
        if (PermissionUtil(this).requestPermission(Config.FILE_PERMISSION) &&
                PermissionUtil(this).requestPermission(Config.CAMERA_PERMISSION)) {
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.1)
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 600000)
            startActivityForResult(intent, VIDEO_TAKE)
        }
    }

    fun chooseRecord() {
        if (PermissionUtil(this).requestPermission(Config.FILE_PERMISSION)) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "audio/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, RECORD_CHOOSE)
        }
    }

    fun takeRecord(filePath: String, timeLimit: Int) {
        if (PermissionUtil(this).requestPermission(Config.FILE_PERMISSION) &&
                PermissionUtil(this).requestPermission(Config.AUDIO_PERMISSION)) {
            FileUtil.checkAvailable(filePath)
            this.filePath = filePath
            if (mMediaRecorder == null) {
                mMediaRecorder = MediaRecorder()
                mMediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
                mMediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                mMediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                mMediaRecorder!!.setMaxDuration(timeLimit)
                mMediaRecorder!!.setOnInfoListener { mr, what, extra ->
                    if (what == MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                        stopRecord()
                    }
                }
            }
            dlgRecord.show()
        }
    }

    companion object {
        const val PHOTO_CHOOSE = 1
        const val PHOTO_TAKE = 2
        const val PHOTO_CROP = 3
        const val VIDEO_CHOOSE = 4
        const val VIDEO_TAKE = 5
        const val RECORD_CHOOSE = 6
        const val RECORD_TAKE = 7

        const val TIME_RECORD_ANIMATOR = 1000L
    }
}