package io.tanjundang.study.knowledge.lrucache;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.BitmapCacheTool;
import io.tanjundang.study.common.tools.LogTool;

public class LruCacheActivity extends AppCompatActivity {
    ImageView ivImage;
    ImageView loadCache;
    Button BtnTest;
    BitmapCacheTool tool = new BitmapCacheTool(this);
    String url = "http://img.bz1111.com/d7/2012-8/2012081210371.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_cache);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        loadCache = (ImageView) findViewById(R.id.loadCache);
        BtnTest = (Button) findViewById(R.id.BtnTest);
        url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522734413179&di=13013c443277821835ea112c87904687&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1672736469%2C1164647862%26fm%3D214%26gp%3D0.jpg";
    }


    public void download(View v) {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!file.exists()) {
            file.mkdirs();
        }
        File filedir = new File(file, "abc.jpg");
        FileDownloader.getImpl()
                .create(url)
                .setPath(filedir.getPath())
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void progress(BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BtnTest.setText("进度" + ((float) soFarBytes / totalBytes) * 100);
                            }
                        });
                        LogTool.v("下载", "下载进度->" + ((float) soFarBytes / totalBytes) * 100);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        LogTool.v("下载", "下载成功");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        LogTool.e("download", e);
                    }


                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
//        tool.display(ivImage, url);
    }

    public void loadCache(View v) {
        tool.display(loadCache, url);
    }
}
