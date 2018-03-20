package io.tanjundang.study;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;

import java.io.File;
import java.io.IOException;

import io.tanjundang.study.common.tools.DateFormatTool;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.LogTool;

/**
 * @Author: TanJunDang
 * @Date: 2018/3/13
 * @Description: 语音录制工具
 */
public class AudioRecordTool {
    private static volatile AudioRecordTool recordTool;
    private MediaRecorder mediaRecorder;
    private String curFileName;
    boolean isRecording = false;
    private MediaPlayer mediaPlayer;

    private int BASE = 1;
    private int SPACE = 100;// 间隔取样时间

    private Handler handler = new Handler();
    private AudioPlayListener lastListener;

    public static AudioRecordTool getInstance() {
        if (recordTool == null) {
            synchronized (AudioRecordTool.class) {
                if (recordTool == null) {
                    recordTool = new AudioRecordTool();
                }
            }
        }
        return recordTool;
    }

    public void start() {
        if (isRecording) {
            return;
        }

        curFileName = DateFormatTool.getDateStrFormat(System.currentTimeMillis(), "yyyy-MM-dd-HH-mm-ss") + ".amr";
        LogTool.v("audioRecordTool", "prepare");
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//从麦克风采集声音
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB); //内容输出格式
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File file = Functions.getSDCardFile("audio", curFileName);
        mediaRecorder.setOutputFile(file.getPath());
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
        updateMicStatus();
        isRecording = true;
    }

    public void stop() {
        if (mediaRecorder == null) return;
        LogTool.v("audioRecordTool", "stop");
        isRecording = false;
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    public void cancel() {
        if (mediaRecorder == null) return;
        LogTool.v("audioRecordTool", "cancel");
        isRecording = false;
        mediaRecorder.release();
        mediaRecorder = null;
        delFile();
    }

    private void delFile() {
        File file = Functions.getSDCardFile("audio", curFileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private void updateMicStatus() {
        if (mediaRecorder != null) {
            double ratio = (double) mediaRecorder.getMaxAmplitude() / BASE;
            double db = 0;// 分贝
            if (ratio > 1)
                db = 20 * Math.log10(ratio);
            int level = (int) (db / 12);
            LogTool.d("audioRecordTool", "分贝值：" + db + "\n=======level:" + level);
            if (micListener != null) {
                micListener.update(level);
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateMicStatus();
                }
            }, SPACE);

        }
    }


    /**
     * 传入 录音位置即可播放
     *
     * @param filePath
     * @param listener
     */
    public void play(final String filePath, final AudioPlayListener listener) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//            关闭上一个点击的语音的动画
            if (lastListener != null)
                lastListener.stop();
            stopPlayer();
        }

        if (mediaPlayer == null) {
            lastListener = listener;
            mediaPlayer = new MediaPlayer();
            try {
                //        这里找不到文件会报错误
                mediaPlayer.setDataSource(filePath);
//                设置流媒体
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // 装载完毕回调
                        mediaPlayer.start();

                        if (listener != null) listener.start();
                    }
                });
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        play(filePath, listener);
                        return false;
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                        listener.stop();
                    }
                });
            } catch (IOException e) {
                mediaPlayer = null;
                e.printStackTrace();
            }
        }
    }

    public void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public interface MicStatusListener {
        void update(int level);
    }

    private MicStatusListener micListener;

    public void setMicStatusListener(MicStatusListener micListener) {
        this.micListener = micListener;
    }

    public interface AudioPlayListener {
        void start();

        void stop();
    }
}
