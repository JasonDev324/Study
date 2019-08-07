package io.tanjundang.study.knowledge.jetpack

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.tanjundang.study.R
import io.tanjundang.study.common.tools.PermissionTool
import io.tanjundang.study.knowledge.download.DownloadUtils
import io.tanjundang.study.knowledge.download.VersionDto
import io.tanjundang.study.knowledge.room.RoomActivity

/**
 * @Author: TanJunDang
 * @Date: 2019/6/6
 * @Description:
 */

class JetPackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)
    }

    var downloadUtils: DownloadUtils? = null
    var permissions = ArrayList<String>();
    fun LifeCycle(v: View) {
        permissions.clear();
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        PermissionTool.getInstance(this).requestPermissions(permissions, object : View.OnClickListener {
            override fun onClick(v: View?) {
                var url = "https://raw.githubusercontent.com/JustinRoom/JSCKit/master/capture/JSCKitDemo.apk"
                var versionDto: VersionDto = VersionDto();
                versionDto.url = url;
                versionDto.fileName = "JSCKitDemo.apk"
                versionDto.code = 100;
                downloadUtils = DownloadUtils(this@JetPackActivity, versionDto);
                downloadUtils!!.start()
            }
        })
//        StartActivity(LifecycleActivity::class.java)
    }

    fun ViewModel(v: View) {
        StartActivity(LifecycleActivity::class.java)
    }

    fun Room(v: View) {
        StartActivity(RoomActivity::class.java)
    }

    fun StartActivity(cls: Class<*>) {
        val intent = Intent(this@JetPackActivity, cls)
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10086) {
            downloadUtils!!.installAPK()
        }
    }
}