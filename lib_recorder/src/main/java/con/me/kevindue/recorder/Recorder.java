package con.me.kevindue.recorder;

import android.media.MediaRecorder;
import android.text.TextUtils;

import java.io.IOException;
import java.util.Date;

/**
 * 录音器
 */
public class Recorder {
    private boolean isRecording = false;//是否正在录音中
    private MediaRecorder recorder;
    private long startTime;

    private Recorder() {

    }
    private static class SingletonHolder {

        private static final Recorder INSTANCE = new Recorder();

    }

    public static Recorder getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public void startRecording(String filePath){
        if (TextUtils.isEmpty(filePath)){
            throw new IllegalArgumentException("MediaRecorder file is empty !");
        }
        try {
            if (recorder != null) {
                recorder.release();
                recorder = null;
            }
            recorder =  new MediaRecorder();
            // 设置声音源为麦克风
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置输出格式为3gp
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            // 设置声音解码AMR_NB
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            // 设置输出文件路径，mFileName为录音音频输出路径
            recorder.setOutputFile(filePath);
            // 媒体录制器准备
            recorder.prepare();
            // 开始录制
            recorder.start();
            isRecording = true;
            startTime = new Date().getTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int stopRecording() {
        if (recorder != null){
            recorder.stop();
            recorder.reset();
            isRecording = false;
            //返回录音持续时间
            return (int) (new Date().getTime() - startTime) / 1000;
        }
        return 0;
    }

    public void releaseRecoder() {
        if (recorder != null){
            recorder.release();
            recorder = null;
            isRecording = false;

        }
    }

    public boolean isRecording() {
        return isRecording;
    }

}
