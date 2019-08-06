package io.tanjundang.study.knowledge.camera;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.LogTool;
import io.tanjundang.study.common.tools.PermissionTool;
import kwantang324.github.io.camera2.PhotoCaptureActivity;
import kwantang324.github.io.camera2.PhotoConfig;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;

import static kwantang324.github.io.camera2.PhotoConfig.PHOTO_DATA;

/**
 *
 */
public class CameraActivity extends BaseActivity {

    ImageView ivImage;
    private final int CAMERA_REQ = 0XFF;
    private ArrayList<String> permissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_camera);
        ivImage = findViewById(R.id.ivImage);
    }

    @Override
    protected void initData() {
        permissionList.add(Manifest.permission.CAMERA);
        permissionList.add(Manifest.permission.RECORD_AUDIO);
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void customCamera(View v) {
        PhotoCaptureActivity.Start(this);
    }

    /**
     * 必须有三个权限CAMERA、RECORD_AUDIO、WRITE_EXTERNAL_STORAGE
     * 没有动态获取RECORD_AUDIO权限会报错误setAudioSource failed.
     *
     * @param v
     */
    public void customVideo(View v) {
        PermissionTool.getInstance(this).requestPermissions(permissionList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.startActivity(VideoRecordActivity.class);
            }
        });
    }

    public void Camera(View view) {
        PermissionTool.getInstance(this).requestPermissions(permissionList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQ);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (requestCode == CAMERA_REQ) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            savePic(bitmap, "DICM", AUTHOR);
        } else if (requestCode == PhotoConfig.REQ_CAPTURE_PHOTO && resultCode == RESULT_OK) {
            String photoPath = data.getStringExtra(PHOTO_DATA);
//            if (TextUtils.isEmpty(photoJson)) return;
//            byte[] photo = GsonTool.getServerBean(photoJson, Byte[].class);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(photo.getBytes(), 0, photo.getBytes().length);
            String targerPath = Environment.getExternalStorageDirectory() + "/" + "abc.jpg";
            Luban.with(this).load(photoPath).setTargetDir(targerPath).
                    filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            Glide.with(CameraActivity.this)
                                    .load(path)
                                    .into(ivImage);
                            return false;
                        }
                    }).launch();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionTool.getInstance(this).onRequestPermissionsResult(permissionList, requestCode, permissions, grantResults);
    }

    public void savePic(Bitmap bitmap, String folder, String fileName) {
        File file = Functions.getSDCardFile(folder, fileName + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);//将bitmap输出到流中
            fos.close();
            Functions.toast("保存文件成功");
            LogTool.log2File("6666", null);
        } catch (Exception e) {
            Functions.toast(e.getMessage().toString());
            e.printStackTrace();
        }
    }
}
