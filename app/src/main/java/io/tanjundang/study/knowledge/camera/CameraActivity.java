package io.tanjundang.study.knowledge.camera;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.SurfaceView;
import android.view.View;

import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.base.BaseApplication;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.LogTool;
import io.tanjundang.study.common.tools.PermissionTool;

/**
 *
 */
public class CameraActivity extends BaseActivity {

    SurfaceView surfaceView;
    private final int CAMERA_REQ = 0XFF;
    private ArrayList<String> permissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_camera);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
    }

    @Override
    protected void initData() {
        permissionList.add(Manifest.permission.CAMERA);
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void customCamera(View v) {
        Functions.startActivity(CustomCameraActivity.class);
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
