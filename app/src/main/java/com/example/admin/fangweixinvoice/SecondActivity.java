package com.example.admin.fangweixinvoice;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.fangweixinvoice.view.MediaManager;
import com.example.admin.fangweixinvoice.view.Recorder;


public class SecondActivity extends AppCompatActivity {

    private TextView luyin_btn;
    private TextView seconds;
    private View length;
    private int mMinItemWidth;
    private int mMaxItemWidth;
    private RelativeLayout rl_play_voice;
    private View mAnimView;
    private String voice_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        mMaxItemWidth = (int) (displayMetrics.widthPixels*0.7f);
        mMinItemWidth = (int) (displayMetrics.widthPixels*0.15f);
        rl_play_voice = findViewById(R.id.rl_play_voice);
        seconds = findViewById(R.id.id_recorder_time);
        length = findViewById(R.id.id_recorder_length);
        luyin_btn =  findViewById(R.id.luyin_btn);
        luyin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ButtomDialogView dialogView = new ButtomDialogView(SecondActivity.this);
                dialogView.show();
                dialogView.setCallBackVoice(new ButtomDialogView.CallBackVoice() {
                    @Override
                    public void backData(Recorder recorder) {
                        voice_path =recorder.getFilePath();
                        seconds.setText(Math.round(recorder.getTime())+"\"");
                        ViewGroup.LayoutParams lp = length.getLayoutParams();
                        lp.width = (int)(mMinItemWidth+(mMaxItemWidth/60f * recorder.getTime()));
                        dialogView.dismiss();
                    }
                });
            }
        });
        rl_play_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAnimView != null){
                    mAnimView.setBackgroundResource(R.drawable.adj);
                    mAnimView = null;
                }
                mAnimView = view.findViewById(R.id.id_recorder_anim);
                mAnimView.setBackgroundResource(R.drawable.play_anim);
                AnimationDrawable anim = (AnimationDrawable) mAnimView.getBackground();
                anim.start();
                MediaManager.playSound(voice_path, new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mAnimView.setBackgroundResource(R.drawable.adj);
                    }
                });
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }
}
