package io.tanjundang.study;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;

import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.base.CommonHolder;
import io.tanjundang.study.base.CommonRecyclerViewAdapter;
import io.tanjundang.study.common.tools.BitmapCacheUtil;
import io.tanjundang.study.common.tools.CommonDialog;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.PermissionTool;
import io.tanjundang.study.common.tools.PhotoTool;
import io.tanjundang.study.knowledge.audio.AudioRecordActivity;
import io.tanjundang.study.knowledge.lrucache.LruCacheActivity;

public class TestActivity extends BaseActivity {

    TextView tvMsg;
    Button ivVoice;
    ImageView ivImage;
    RecyclerView recyclerView;
    ArrayList permissionList = new ArrayList();
    ArrayList<TestInfo> list = new ArrayList<>();
    TestAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_test);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        ivVoice = (Button) findViewById(R.id.ivVoice);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestAdapter(this, R.layout.recyle_item_test, list);
        recyclerView.setAdapter(mAdapter);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_from_point);
        LayoutAnimationController lac = new LayoutAnimationController(anim);
        lac.setDelay(0.5f);
        lac.setInterpolator(new AccelerateInterpolator());
        recyclerView.setLayoutAnimation(lac);
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    protected void initData() {
        permissionList.add(Manifest.permission.WRITE_CONTACTS);
        permissionList.add(Manifest.permission.RECORD_AUDIO);
        permissionList.add(Manifest.permission.INTERNET);
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

    }

    public void startThread() {

    }

    public void checkPermission(final View view) {
        PermissionTool.getInstance(this).requestPermissions(permissionList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dothing();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void start(View v) {
        showDiaglog();
    }


    public void TEST1(View v) {
        checkPermission(v);
    }

    public void showDiaglog() {
        CommonDialog.Builder dialog = new CommonDialog.Builder(this);
        dialog.setTitle("666")
                .setContent("abc")
                .setPositiveListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Functions.toast("FUCK");
                    }
                }).build().show();

    }


    public void TEST2(View v) {
        startThread();
    }

    String url = "http://pics.sc.chinaz.com/files/pic/pic9/201803/wpic176.jpg";
    BitmapCacheUtil tool = new BitmapCacheUtil(this);

    public void TEST3(View v) {
        tool.display(ivImage, url);
    }


    public void TEST4(View v) {
    }

    public void voice(View v) {
        Intent intent = new Intent(this, LruCacheActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void dothing() {
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor cursor = resolver.query(uri, null, null, null, null);
        int i = 0;
        StringBuffer sb = new StringBuffer("");
        while (cursor.moveToNext()) {
            i++;
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            sb.append("名字：" + name + "手机：" + phone + "\n");
            list.add(new TestInfo(name));
            if (i == 40) {
                break;
            }
        }

        tvMsg.setText(sb.toString());
        Functions.toast("Success");
        mAdapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionTool.getInstance(this).onRequestPermissionsResult(permissionList, requestCode, permissions, grantResults);
    }


    public class TestAdapter extends CommonRecyclerViewAdapter<TestInfo> {
        public TestAdapter(Context context, int layoutId, ArrayList<TestInfo> list) {
            super(context, layoutId, list);
        }

        @Override
        public void convert(CommonHolder holder, TestInfo data, int pos) {
            holder.setText(R.id.tvName, data.getName(), null);
        }
    }

}

