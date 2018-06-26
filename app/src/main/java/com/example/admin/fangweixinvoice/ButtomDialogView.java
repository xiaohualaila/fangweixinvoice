package com.example.admin.fangweixinvoice;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.admin.fangweixinvoice.view.AudioRecorderButton;
import com.example.admin.fangweixinvoice.view.Recorder;


public class ButtomDialogView extends Dialog {
    private boolean iscancelable = true;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable = true;//控制返回键是否dismiss
    private CallBackVoice callBackVoice;

    public void setCallBackVoice(CallBackVoice callBackVoice){
        this.callBackVoice = callBackVoice;
    }

    private AudioRecorderButton btn;
    public ButtomDialogView(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_luyin);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        setCanceledOnTouchOutside(isBackCancelable);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }

    private void initView() {
        btn = findViewById(R.id.id_recorder_button);
        btn.setOnAudioFinishRecorderListener(new AudioRecorderButton.OnAudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String path) {
                Recorder recorder = new Recorder(seconds,path);
                callBackVoice.backData(recorder);
            }
        });
    }

    public interface CallBackVoice{
         void backData(Recorder recorder);
    }
}
