package io.tanjundang.study.knowledge.lrucache

import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloadSampleListener
import com.liulishuo.filedownloader.FileDownloader

import java.io.File

import io.tanjundang.study.R
import io.tanjundang.study.common.tools.BitmapCacheTool
import io.tanjundang.study.common.tools.LogTool

class LruCacheActivity : AppCompatActivity() {
    internal var ivImage: ImageView? = null
    internal var loadCache: ImageView? = null
    internal var BtnTest: Button? = null
    internal var tool = BitmapCacheTool(this)
    internal var url = "http://img.bz1111.com/d7/2012-8/2012081210371.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lru_cache)
        ivImage = findViewById<View>(R.id.ivImage) as ImageView
        loadCache = findViewById<View>(R.id.loadCache) as ImageView
        BtnTest = findViewById<View>(R.id.BtnTest) as Button
        url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522734413179&di=13013c443277821835ea112c87904687&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1672736469%2C1164647862%26fm%3D214%26gp%3D0.jpg"
    }


    fun download(v: View) {
        val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (!file.exists()) {
            file.mkdirs()
        }
        val filedir = File(file, "abc.jpg")
        FileDownloader.getImpl()
                .create(url)
                .setPath(filedir.path)
                .setListener(object : FileDownloadSampleListener() {
                    override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                        runOnUiThread { BtnTest!!.text = "进度" + soFarBytes.toFloat() / totalBytes * 100 }
                        LogTool.v("下载", "下载进度->" + soFarBytes.toFloat() / totalBytes * 100)
                    }

                    override fun completed(task: BaseDownloadTask?) {
                        LogTool.v("下载", "下载成功")
                    }

                    override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                    }

                    override fun error(task: BaseDownloadTask?, e: Throwable?) {
                        LogTool.e("download", e)
                    }


                    override fun warn(task: BaseDownloadTask?) {}
                }).start()
        //        tool.display(ivImage, url);
    }

    fun loadCache(v: View) {
        tool.display(loadCache, url)
    }
}
