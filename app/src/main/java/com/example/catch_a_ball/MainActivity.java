package com.example.catch_a_ball;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //elements
    private TextView StartLabel,ScoreLabel;
    private ImageView bu;
    private ImageView pink;

    //position
    private float bu_y;

    //timer
    private Timer timer = new Timer();
    private Handler handler = new Handler();

    //status
    private boolean action_flag = false;
    private boolean start_flag = false;

    //size
    private int FrameHeight;
    private int busize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartLabel = findViewById(R.id.StartLabel);
        ScoreLabel = findViewById(R.id.ScoreLabel);
        bu = findViewById(R.id.image);
        pink = findViewById(R.id.pink);

        //initial position
        pink.setX(-80.0f);
        pink.setY(-80.0f);

    }

    public void changePos(){
        if(action_flag){
            //touching
            bu_y -= 20;
        }
        else{
            //release
            bu_y += 20;
        }
        // move in the frame
        if(bu_y < 0) bu_y = 0;
        if(bu_y > FrameHeight - busize) bu_y = FrameHeight - busize;
        bu.setY(bu_y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!start_flag) {
            start_flag = true;
            StartLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);
        }
        else{
            //FrameHeight
            FrameLayout frameLayout = findViewById(R.id.frame);
            FrameHeight = frameLayout.getHeight();
            //BuSize
            bu_y = bu.getY();
            busize = bu.getHeight();


            if(event.getAction() == MotionEvent.ACTION_DOWN){
                action_flag = true;
            }
            else if(event.getAction() == MotionEvent.ACTION_UP) {
                action_flag = false;
            }
        }
        return super.onTouchEvent(event);
    }
}