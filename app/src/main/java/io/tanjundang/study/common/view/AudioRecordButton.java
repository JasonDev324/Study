package io.tanjundang.study.common.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.tanjundang.study.AudioDialogManager;
import io.tanjundang.study.AudioRecordTool;
import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.LogTool;


/**
 * @Author: TanJunDang
 * @Date: 2018/3/9
 * @Description: 仿微信语音按钮
 */
public class AudioRecordButton extends android.support.v7.widget.AppCompatButton implements AudioRecordTool.MicStatusListener {

    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_TO_CANCEL = 3;

    private int mCurState = STATE_NORMAL;
    private boolean isRecording;

    private static final int DEFAULT_MOVE_HEIGHT = 200;

    //    录音最短时间
    private static final int AUDIO_RECORD_MINIMUM_TIME = 1000;
    long currentDate;
    AudioDialogManager dialogManager;

    public AudioRecordButton(Context context) {
        this(context, null);
    }

    public AudioRecordButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioRecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        AudioRecordTool.getInstance().setMicStatusListener(this);
        dialogManager = new AudioDialogManager(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isRecording) {
                    break;
                }
                currentDate = System.currentTimeMillis();
                isRecording = true;
                changeState(STATE_RECORDING);

                dialogManager.showRecordingDialog();
                AudioRecordTool.getInstance().start();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecording) {
                    if (wantToCancel(x, y)) {
                        changeState(STATE_WANT_TO_CANCEL);
                    } else {
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
//                录音时间过短
                if (System.currentTimeMillis() - currentDate < AUDIO_RECORD_MINIMUM_TIME) {
                    dialogManager.modifyText(R.string.str_audio_time_short);
                    Observable.interval(500, TimeUnit.MILLISECONDS)
                            .take(1)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    dialogManager.dismiss();
                                    reset();
                                }
                            });
                    break;
                }
                dialogManager.dismiss();
                if (mCurState == STATE_RECORDING) {
// TODO: 2018/3/9 release
                    AudioRecordTool.getInstance().stop();
                } else if (mCurState == STATE_WANT_TO_CANCEL) {
// TODO: 2018/3/9 cancel
                    AudioRecordTool.getInstance().cancel();
                }
                reset();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void reset() {
        isRecording = false;
        changeState(STATE_NORMAL);
    }

    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {
            return true;
        }
//            在按钮上方、在按钮下方
        if (y < -DEFAULT_MOVE_HEIGHT || y > getHeight() + DEFAULT_MOVE_HEIGHT) {
            return true;
        }
        return false;
    }

    private void changeState(int state) {
        if (mCurState != state) {
            mCurState = state;
            switch (mCurState) {
                case STATE_NORMAL:
                    setBackgroundColor(Color.WHITE);
                    setText(R.string.str_audio_btn_normal);
                    dialogManager.modifyText(R.string.str_audio_btn_normal);
                    break;
                case STATE_RECORDING:
                    setBackgroundColor(Color.GRAY);
                    setText(R.string.str_audio_btn_recording);
                    dialogManager.modifyText(R.string.str_audio_btn_recording);
                    break;
                case STATE_WANT_TO_CANCEL:
                    setBackgroundColor(Color.GRAY);
                    setText(R.string.str_audio_btn_want_to_cancel);
                    dialogManager.modifyText(R.string.str_audio_btn_want_to_cancel);
                    break;
            }

        }
    }

    @Override
    public boolean isClickable() {
        return true;
    }

    @Override
    public boolean isLongClickable() {
        return true;
    }

    @Override
    public void update(final int level) {
        LogTool.v("audio_button", level);
        if (isRecording) {
            Observable.interval(1, TimeUnit.MILLISECONDS)
                    .take(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            dialogManager.updateVoiceLevel(level);
                        }
                    });
        }
    }


}
