package io.tanjundang.study;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.tanjundang.study.common.tools.Functions;

/**
 * @Author: TanJunDang
 * @Date: 2018/3/9
 * @Description: 双重校验
 */
public class AudioDialogManager {
    AlertDialog mDialog;
    AlertDialog.Builder builder;
    Context mContext;
    View rootview;
    TextView tvContent;
    ImageView ivRecall;
    RelativeLayout rlRecord;
    static volatile AudioDialogManager manager;

    public AudioDialogManager(Context context) {
        this.mContext = context;
        rootview = LayoutInflater.from(mContext).inflate(R.layout.view_audio_dialog, null);
        tvContent = (TextView) rootview.findViewById(R.id.tvContent);
        rlRecord = (RelativeLayout) rootview.findViewById(R.id.rlRecord);
        ivRecall = (ImageView) rootview.findViewById(R.id.ivRecall);
        builder = new AlertDialog.Builder(mContext, R.style.CustomDialog);
        builder.setView(rootview);
        mDialog = builder.create();
    }

    public static AudioDialogManager getInstance(Context context) {
        if (manager == null) {
            synchronized (AudioDialogManager.class) {
                if (manager == null) {
                    manager = new AudioDialogManager(context);
                }
            }
        }
        return manager;
    }


    public void showRecordingDialog() {
        tvContent.setText("松开结束");
        mDialog.show();
    }

    public void showNormalDialog() {
        tvContent.setText("按住说话");
        mDialog.show();
    }

    public void showWantToCancelDialog() {
        if (mDialog != null) {
            tvContent.setText(R.string.str_audio_btn_want_to_cancel);
            mDialog.show();
        }
    }

    public void modifyText(int resid) {
        switch (resid) {
            case R.string.str_audio_btn_normal:
                break;
            case R.string.str_audio_btn_recording:
                rlRecord.setVisibility(View.VISIBLE);
                ivRecall.setVisibility(View.GONE);
                break;
            case R.string.str_audio_btn_want_to_cancel:
                rlRecord.setVisibility(View.GONE);
                ivRecall.setVisibility(View.VISIBLE);
                ivRecall.setImageResource(R.drawable.ic_audio_recall);
                break;
            case R.string.str_audio_time_short:
                rlRecord.setVisibility(View.GONE);
                ivRecall.setVisibility(View.VISIBLE);
                ivRecall.setImageResource(R.drawable.ic_audio_time_short);
                break;
        }
        tvContent.setText(resid);
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}


