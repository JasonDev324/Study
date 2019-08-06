package kwantang324.github.io.camera2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import kwantang324.github.io.R;

/**
 * @Author: TanJunDang
 * @Date: 2019/7/31
 * @Description: camera2自定义相机
 */

public class PhotoCaptureActivity extends AppCompatActivity implements View.OnClickListener {

    AutoFitTextureView mTextureView;
    ImageView ivImage;
    Button btnCapture;
    Button btnSwitch;
    Button btnCancel;
    Button btnFinish;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    ///为了使照片竖直显示
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private Handler childHandler;
    private Handler mainHandler;
    private String mCameraId;
    private CameraManager mCameraManager;
    private ImageReader mImageReader;
    private CameraDevice mCameraDevice;
    private CameraCaptureSession mCaptureSession;

    private Size mCaptureSize;
    private Size mPreviewSize;
    private int mSensorOrientation;

    private byte[] photoData;
    private String photoPath;
    private static final String PHONE_SAMSUNG = "samsung";

    public static void Start(Context conetxt) {
        Intent intent = new Intent(conetxt, PhotoCaptureActivity.class);
        ((Activity) conetxt).startActivityForResult(intent, PhotoConfig.REQ_CAPTURE_PHOTO);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_capture);
        mTextureView = findViewById(R.id.textureView);
        ivImage = findViewById(R.id.ivImage);
        btnCapture = findViewById(R.id.btnCapture);
        btnSwitch = findViewById(R.id.btnSwitch);
        btnCancel = findViewById(R.id.btnCancel);
        btnFinish = findViewById(R.id.btnFinish);
        mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        btnCapture.setOnClickListener(this);
        btnSwitch.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

    }

    TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            getCameraId(width, height);
            openCamera();
            configureTransform(width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    private void openCamera() {
        HandlerThread handlerThread = new HandlerThread("camera2");
        handlerThread.start();
        childHandler = new Handler(handlerThread.getLooper());
        mainHandler = new Handler(getMainLooper());
        mImageReader = ImageReader.newInstance(mPreviewSize.getWidth(), mPreviewSize.getHeight(), ImageFormat.JPEG, 2);
        //此ImageReader用于拍照所需
//                可控制图片的质量

        mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                Image image = reader.acquireNextImage();
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);//由缓冲区存入字节数组
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Bitmap resultBitmap = null;

//                前置摄像头要做镜像翻转
                if (mSensorOrientation == 270) {
                    if (Build.BRAND.equals(PHONE_SAMSUNG)) {
                        resultBitmap = rotateFont(bitmap);
                    } else {
                        resultBitmap = mirror(bitmap);
                    }
                } else {
                    if (Build.BRAND.equals(PHONE_SAMSUNG)) {
                        resultBitmap = rotateBack(bitmap);
                    } else {
                        resultBitmap = bitmap;
                    }
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                photoData = baos.toByteArray();
                File file = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
                try {
                    if (file.exists() && file.isDirectory()) {
                        file.delete();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    fos.write(photoData);
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                photoPath = file.getAbsolutePath();
                ivImage.setVisibility(View.VISIBLE);
                btnFinish.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                mTextureView.setVisibility(View.GONE);
                if (bitmap != null)
                    ivImage.setImageBitmap(resultBitmap);
            }
        }, mainHandler);
        try {
            if (ActivityCompat.checkSelfPermission(PhotoCaptureActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mCameraManager.openCamera("" + mCameraId, stateCallback, childHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void getCameraId(int width, int height) {
        //获取摄像头的管理者CameraManager
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            //遍历所有摄像头
            for (String cameraId : mCameraManager.getCameraIdList()) {
                CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(cameraId);
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                //此处默认打开后置摄像头
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT)
                    continue;
                //获取StreamConfigurationMap，它是管理摄像头支持的所有输出格式和尺寸
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                assert map != null;
                int displayRotation = getWindowManager().getDefaultDisplay().getRotation();
                //noinspection ConstantConditions
                mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);


                //根据TextureView的尺寸设置预览尺寸
                mPreviewSize = getOptimalSize(map.getOutputSizes(SurfaceTexture.class), width, height);
                //获取相机支持的最大拍照尺寸
                mCaptureSize = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new Comparator<Size>() {
                    @Override
                    public int compare(Size lhs, Size rhs) {
                        return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getHeight() * rhs.getWidth());
                    }
                });

                mCameraId = cameraId;
                break;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void configureTransform(int viewWidth, int viewHeight) {
        if (null == mTextureView || null == mPreviewSize) {
            return;
        }
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, mPreviewSize.getHeight(), mPreviewSize.getWidth());
        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) viewHeight / mPreviewSize.getHeight(),
                    (float) viewWidth / mPreviewSize.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180, centerX, centerY);
        }
        mTextureView.setTransform(matrix);
    }

    //选择sizeMap中大于并且最接近width和height的size
    private Size getOptimalSize(Size[] sizeMap, int width, int height) {
        List<Size> sizeList = new ArrayList<>();
        for (Size option : sizeMap) {
            if (width > height) {
                if (option.getWidth() > width && option.getHeight() > height) {
                    sizeList.add(option);
                }
            } else {
                if (option.getWidth() > height && option.getHeight() > width) {
                    sizeList.add(option);
                }
            }
        }
        if (sizeList.size() > 0) {
            return Collections.min(sizeList, new Comparator<Size>() {
                @Override
                public int compare(Size lhs, Size rhs) {
                    return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getWidth() * rhs.getHeight());
                }
            });
        }
        return sizeMap[0];
    }


    CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice = camera;
            takePreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            if (null != mCameraDevice) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camera.close();
            mCameraDevice = null;
        }
    };

    private void takePreview() {
        //   开始预览
        try {
            SurfaceTexture mSurfaceTexture = mTextureView.getSurfaceTexture();
            mSurfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            Surface previewSurface = new Surface(mSurfaceTexture);

            // 创建预览需要的CaptureRequest.Builder,用于控制对焦、闪光灯等配置
            final CaptureRequest.Builder previewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            // 将SurfaceView的surface作为CaptureRequest.Builder的目标,输出内容到surface
            previewBuilder.addTarget(previewSurface);
            // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
            mCameraDevice.createCaptureSession(Arrays.asList(previewSurface, mImageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if (null == mCameraDevice) return;
                    mCaptureSession = session;
                    // 自动对焦
                    previewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                    // 打开闪光灯
                    previewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                    CaptureRequest previewRequest = previewBuilder.build();
                    try {
                        mCaptureSession.setRepeatingRequest(previewRequest, null, childHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Toast.makeText(PhotoCaptureActivity.this, "配置失败", Toast.LENGTH_SHORT).show();
                }
            }, childHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void reopenCamera() {
        if (mTextureView.isAvailable()) {
            openCamera();
        } else {
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    public void closeCamera() {
        ivImage.setVisibility(View.GONE);
        btnFinish.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
        mTextureView.setVisibility(View.VISIBLE);
        btnCapture.setVisibility(View.VISIBLE);
        if (null != mCaptureSession) {
            mCaptureSession.close();
            mCaptureSession = null;
        }
        if (null != mCameraDevice) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
        if (null != mImageReader) {
            mImageReader.close();
            mImageReader = null;
        }

    }

    private void takePicture() {
        if (mCameraDevice == null) return;
        try {
//            创建拍照的请求
            CaptureRequest.Builder mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            //  ImageReader作为输出目标
            mCaptureRequestBuilder.addTarget(mImageReader.getSurface());
            // 自动对焦
            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 自动曝光
            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            // 根据设备方向计算设置照片的方向,特殊处理翻转照片,三星手机不适用
            CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics(mCameraId);
            int cameraPos = getJpegOrientation(cameraCharacteristics, rotation);
            mCaptureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, cameraPos);

            CaptureRequest mCaptureRequest = mCaptureRequestBuilder.build();
            mCaptureSession.capture(mCaptureRequest, null, childHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private Bitmap mirror(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(-1f, 1f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    //    处理三星手机图片旋转问题
    private Bitmap rotateFont(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        if (bitmap.getWidth() > bitmap.getHeight()) {
            matrix.postRotate(-90);
            matrix.postScale(-1f, 1f);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    //    处理三星手机图片旋转问题
    private Bitmap rotateBack(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        if (bitmap.getWidth() > bitmap.getHeight()) {
            matrix.postRotate(90);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private int getJpegOrientation(CameraCharacteristics c, int deviceOrientation) {
        if (deviceOrientation == android.view.OrientationEventListener.ORIENTATION_UNKNOWN)
            return 0;
        mSensorOrientation = c.get(CameraCharacteristics.SENSOR_ORIENTATION);

        // Round device orientation to a multiple of 90
        deviceOrientation = (deviceOrientation + 45) / 90 * 90;

        // Reverse device orientation for front-facing cameras
        boolean facingFront = c.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT;
        if (facingFront) deviceOrientation = -deviceOrientation;

        // Calculate desired JPEG orientation relative to camera orientation to make
        // the image upright relative to the device orientation
        int jpegOrientation = (mSensorOrientation + deviceOrientation + 360) % 360;

        return jpegOrientation;
    }

    @Override
    public void onDestroy() {
        closeCamera();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnSwitch)) {
            closeCamera();
            if (mCameraId.equals(CameraCharacteristics.LENS_FACING_FRONT + "")) {
                mCameraId = CameraCharacteristics.LENS_FACING_BACK + "";
            } else {
                mCameraId = CameraCharacteristics.LENS_FACING_FRONT + "";
            }
            reopenCamera();
        } else if (v.equals(btnCapture)) {
            btnCapture.setVisibility(View.GONE);
            takePicture();
        } else if (v.equals(btnCancel)) {
            ivImage.setVisibility(View.GONE);
            btnFinish.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            mTextureView.setVisibility(View.VISIBLE);
            btnCapture.setVisibility(View.VISIBLE);
            photoData = null;
            reopenCamera();
        } else if (v.equals(btnFinish)) {
            Intent intent = new Intent();
            if (photoData != null) {
                intent.putExtra(PhotoConfig.PHOTO_DATA, photoPath);
                setResult(RESULT_OK, intent);
            }
            finish();
        }

    }
}
