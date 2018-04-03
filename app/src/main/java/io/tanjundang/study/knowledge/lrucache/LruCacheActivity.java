package io.tanjundang.study.knowledge.lrucache;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.BitmapCacheUtil;

public class LruCacheActivity extends AppCompatActivity {
    ImageView ivImage;
    ImageView loadCache;
    BitmapCacheUtil util = new BitmapCacheUtil(this);
    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522734413179&di=13013c443277821835ea112c87904687&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1672736469%2C1164647862%26fm%3D214%26gp%3D0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_cache);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        loadCache = (ImageView) findViewById(R.id.loadCache);
    }


    public void download(View v) {
        util.display(ivImage, url);
    }

    public void loadCache(View v) {
        util.display(loadCache, url);
    }
}
