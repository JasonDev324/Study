package io.tanjundang.study.knowledge.download

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.tanjundang.study.R
import io.tanjundang.study.common.tools.PermissionTool

class DownloadActivity : AppCompatActivity() {

    var downloadUtils: DownloadUtils? = null
    var permissions = ArrayList<String>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
    }

    fun start_download(v: View) {
        permissions.clear();
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        PermissionTool.getInstance(this).requestPermissions(permissions, object : View.OnClickListener {
            override fun onClick(v: View?) {
                var url = "https://raw.githubusercontent.com/JustinRoom/JSCKit/master/capture/JSCKitDemo.apk"
                var versionDto: VersionDto = VersionDto();
                versionDto.url = url;
                versionDto.fileName = "JSCKitDemo.apk"
                versionDto.code = 100;
                downloadUtils = DownloadUtils(this@DownloadActivity, versionDto);
                downloadUtils!!.start()
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
