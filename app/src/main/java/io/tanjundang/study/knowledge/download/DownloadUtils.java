package io.tanjundang.study.knowledge.download;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;

/**
 * @Author: TanJunDang
 * @Date: 2019/8/6
 * @Description:
 */
public class DownloadUtils {

    DownloadManager mDownloadManager;
    Context mContext;
    VersionDto versionDto;
    long downloadId;
    String filePath;

    public DownloadUtils(Context mContext, VersionDto dto) {
        this.mContext = mContext;
        this.versionDto = dto;
        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public void start() {
        Uri uri = Uri.parse(versionDto.url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), versionDto.fileName);
        filePath = file.getAbsolutePath();
        request.setDestinationUri(Uri.fromFile(file));

        request.setTitle("物管运营1.1");
        request.setDescription("更新1.1版本");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, versionDto.fileName);
        downloadId = mDownloadManager.enqueue(request);
        mContext.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //广播监听下载的各个状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getCurStatus();
        }
    };

    private void getCurStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor mCursor = mDownloadManager.query(query);
        if (mCursor.moveToFirst()) {
            int status = mCursor.getInt(mCursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //下载完成安装APK
                    //8.0有未知应用安装请求权限
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        //先获取是否有安装未知来源应用的权限
                        if (mContext.getPackageManager().canRequestPackageInstalls()) {
                            installAPK();
                        } else {
                            Uri uri = Uri.parse("package:" + mContext.getPackageName());
                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri);
                            ((Activity) mContext).startActivityForResult(intent, 10086);
                        }
                    } else {
                        installAPK();
                    }
                    mCursor.close();
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                    mCursor.close();
                    mContext.unregisterReceiver(receiver);
                    break;
            }
        }
    }

    public void installAPK() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Android 7.0以上要使用FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = new File(filePath);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(mContext, "io.tanjundang.study.fileProvider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            File file = new File(filePath);
            Uri apkUri = Uri.fromFile(file);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }

}
