package io.tanjundang.study.knowledge.download

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadSampleListener
import com.liulishuo.filedownloader.FileDownloader
import io.tanjundang.study.R
import io.tanjundang.study.common.tools.LogTool
import io.tanjundang.study.common.tools.PermissionTool
import java.io.File

class DownloadActivity : AppCompatActivity() {

    var downloadUtils: DownloadUtils? = null
    var permissions = ArrayList<String>();
    var builder: NotificationCompat.Builder? = null
    var notificationManager: NotificationManager? = null
    var notification: Notification? = null
    var channel: NotificationChannel? = null
    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            适配8.0通知栏
            channel = NotificationChannel("Primary Channel", "Primary Channel", NotificationManager.IMPORTANCE_DEFAULT);
            channel!!.lightColor = Color.GREEN
            channel!!.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationManager!!.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this@DownloadActivity, channel!!.getId())
        } else {
            builder = NotificationCompat.Builder(this@DownloadActivity, "Kwan")
        }
        notification = builder!!
                .setContentTitle("物管运营1.1")
                .setContentText("物管运营1.1修女版本")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false)
                .setProgress(100, 0, false)
                .build();
    }

    fun start_download(v: View) {
        permissions.clear();
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        PermissionTool.getInstance(this).requestPermissions(permissions, object : View.OnClickListener {
            override fun onClick(v: View?) {
                var url = "https://raw.githubusercontent.com/JustinRoom/JSCKit/master/capture/JSCKitDemo.apk"
                var versionDto = VersionDto();
                versionDto.url = url;
                versionDto.fileName = "JSCKitDemo.apk"
                versionDto.code = 100;
                downloadUtils = DownloadUtils(this@DownloadActivity, versionDto);
                downloadUtils!!.start()
            }
        })
    }

    var count = 1;
    fun file_download(v: View) {
        permissions.clear();
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        PermissionTool.getInstance(this).requestPermissions(permissions, object : View.OnClickListener {
            override fun onClick(v: View?) {
                var url = "https://raw.githubusercontent.com/JustinRoom/JSCKit/master/capture/JSCKitDemo.apk"
                var versionDto = VersionDto();
                versionDto.url = url;
                versionDto.fileName = "JSCKitDemo.apk"
                versionDto.code = 100;
                var fileDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), versionDto.fileName);
                FileDownloader.getImpl()
                        .create(versionDto.url)
                        .setPath(fileDir.absolutePath)
                        .setListener(object : FileDownloadSampleListener() {
                            override fun started(task: BaseDownloadTask?) {
                                super.started(task)
                                runOnUiThread { notificationManager!!.notify(1, notification); }
                            }

                            override fun completed(task: BaseDownloadTask?) {
                                super.completed(task)
                                LogTool.v("notification", "下载完成：")
                                notificationManager!!.cancel(1)
                            }

                            override fun error(task: BaseDownloadTask?, e: Throwable?) {
                                super.error(task, e)
                                LogTool.v("notification", "下载失败：" + e)

                            }

                            override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                                super.progress(task, soFarBytes, totalBytes)
                                LogTool.v("notification", "soFarBytes:" + soFarBytes + "\ntotalBytes:" + totalBytes)
                                count++;
                                runOnUiThread {
                                    notification = builder!!
                                            .setProgress(100, count, false)
                                            .build()
                                    notificationManager!!.notify(1, notification);
                                }

                            }
                        }).start()
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10086) {
            downloadUtils!!.installAPK()
        }
    }
}
