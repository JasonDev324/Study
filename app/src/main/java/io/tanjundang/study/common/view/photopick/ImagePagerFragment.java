package io.tanjundang.study.common.view.photopick;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.imageaware.NonViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseFragment;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.ImageLoaderTool;
import io.tanjundang.study.common.tools.LogTool;
import pl.droidsonroids.gif.GifImageView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


public class ImagePagerFragment extends BaseFragment {

    private final View.OnClickListener onClickImageClose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
        }
    };
    private final PhotoViewAttacher.OnPhotoTapListener onPhotoTapClose = new PhotoViewAttacher.OnPhotoTapListener() {
        @Override
        public void onPhotoTap(View view, float v, float v2) {
            getActivity().onBackPressed();
        }
    };
    private final PhotoViewAttacher.OnViewTapListener onViewTapListener = new PhotoViewAttacher.OnViewTapListener() {
        @Override
        public void onViewTap(View view, float v, float v1) {
            getActivity().onBackPressed();
        }
    };
    String uri;
    View image;
    private String TAG = this.getClass().getName();
    private View baseView;
    private DonutProgress circleLoading;
    private ViewGroup rootLayout;
    private View imageLoadFail;

    public void setData(String uriString) {
        uri = uriString;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        baseView = inflater.inflate(R.layout.fragment_photopick_image_pager, container, false);
        initView();
        showPhoto();
        return baseView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView() {
        circleLoading = (DonutProgress) baseView.findViewById(R.id.circleLoading);
        rootLayout = (ViewGroup) baseView.findViewById(R.id.rootLayout);
        imageLoadFail = baseView.findViewById(R.id.imageLoadFail);
    }

    private void showPhoto() {
        if (!isAdded()) {
            return;
        }
        //使用displayImage,而不使用loadImage,因为loadImage会导致当同时加载同一个url的时候出现task被取消的情况
        //详情见https://github.com/nostra13/Android-Universal-Image-Loader/issues/804
        ImageSize size = new ImageSize(Functions.getScreenWidth(), Functions.getScreenHeight());
        NonViewAware imageAware = new NonViewAware(size, ViewScaleType.CROP);
        ImageLoaderTool.imageLoader.displayImage(uri, imageAware, ImageLoaderTool.imageBigOptions, new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        circleLoading.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        if (!isAdded()) {
                            return;
                        }

                        circleLoading.setVisibility(View.GONE);
                        imageLoadFail.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingComplete(final String imageUri, View view, Bitmap loadedImage) {
                        if (!isAdded()) {
                            return;
                        }

                        circleLoading.setVisibility(View.GONE);

                        File file;
                        if (ImageInfo.isLocalFile(uri)) {
                            file = ImageInfo.getLocalFile(uri);
                        } else {
                            file = ImageLoaderTool.getInstance().getDiskFile(imageUri);
                        }
                        if (Functions.isGifByFile(file)) {
                            image = getActivity().getLayoutInflater().inflate(R.layout.photopick_imageview_gif, null);
                            rootLayout.addView(image);
                            image.setOnClickListener(onClickImageClose);
                        } else {
                            PhotoView photoView = (PhotoView) getActivity().getLayoutInflater().inflate(R.layout.photopick_imageview_touch, null);
                            image = photoView;
                            rootLayout.addView(image);
                            photoView.setOnPhotoTapListener(onPhotoTapClose);
                            photoView.setOnViewTapListener(onViewTapListener);
                        }

                        try {
                            if (image instanceof GifImageView) {
                                Uri uri1 = Uri.fromFile(file);
                                ((GifImageView) image).setImageURI(uri1);
                            } else if (image instanceof PhotoView) {
                                ((PhotoView) image).setImageBitmap(loadedImage);

                            }
                        } catch (Exception e) {
                            LogTool.v(TAG, e.getMessage());
                        }
                    }
                },
                new ImageLoadingProgressListener() {

                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        if (!isAdded()) {
                            return;
                        }

                        int progress = current * 100 / total;
                        circleLoading.setProgress(progress);
                    }
                });
    }
}
