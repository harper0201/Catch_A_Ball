package com.example.catch_a_ball;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //elements
    private TextView StartLabel,ScoreLabel;
    private ImageView bu;
    private ImageView pink,black,orange;

    //position
    private float bu_y;
    private float black_x,black_y;
    private float pink_x,pink_y;
    private float orange_x,orange_y;

    //timer
    private Timer timer = new Timer();

    //status
    private boolean action_flag = false;
    private boolean start_flag = false;

    //size
    private int FrameHeight;
    private int busize;
    private int ScreenHeight,ScreenWidth;

    //score
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartLabel = findViewById(R.id.StartLabel);
        ScoreLabel = findViewById(R.id.ScoreLabel);
        bu = findViewById(R.id.image);
        pink = findViewById(R.id.pink);
        black = findViewById(R.id.black);
        orange = findViewById(R.id.orange);

        //ScreenSize
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point Size = new Point();
        display.getSize(Size);
        ScreenWidth = Size.x;
        ScreenHeight = Size.y;

        //initial position
        orange.setX(-50.0f);
        orange.setY(-50.0f);
        black.setX(-50.0f);
        black.setY(-50.0f);
        pink.setX(-50.0f);
        pink.setY(-50.0f);

    }

    public void changePos(){
        CheckHit();

        orange_x -= 12;
        if(orange_x < 0){
            orange_x = ScreenWidth + 20;
            orange_y = (float) Math.ceil(Math.random()*(FrameHeight-orange.getHeight()));
        }
        orange.setX(orange_x);
        orange.setY(orange_y);

        //black
        black_x -= 16;
        if(black_x < 0){
            black_x = ScreenWidth + 10;
            black_y = (float) Math.ceil(Math.random()*(FrameHeight-black.getHeight()));
        }
        black.setX(black_x);
        black.setY(black_y);

        //pink
        pink_x -= 20;
        if(pink_x < 0){
            pink_x = ScreenWidth + 250;
            pink_y = (float) Math.ceil(Math.random()*(FrameHeight-pink.getHeight()));
        }
        pink.setX(pink_x);
        pink.setY(pink_y);


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

        //set the score
        ScoreLabel.setText("Score:"+ score);
    }

    public void CheckHit(){
        //orange
        float OrangeCenterX = orange_x + orange.getWidth()/2.0f;
        float OrangeCenterY = orange_y + orange.getWidth()/2.0f;
        if(0 <= OrangeCenterX && OrangeCenterX <= busize && bu_y <= OrangeCenterY && OrangeCenterY <= bu_y + busize){
            orange_x = -100f;
            score += 10;
        }
        // pink
        float PinkCenterX = pink_x + pink.getWidth()/2.0f;
        float PinkCenterY = pink_y + pink.getWidth()/2.0f;
        if(0 <= PinkCenterX && PinkCenterX <= busize && bu_y <= PinkCenterY && PinkCenterY <= bu_y + busize){
            pink_x = -100f;
            score += 30;
        }
        //black
        float BlackCenterX = black_x + black.getWidth()/2.0f;
        float BlackCenterY = black_y + black.getWidth()/2.0f;
        if(0 <= BlackCenterX && BlackCenterX <= busize && bu_y <= BlackCenterY && BlackCenterY <= bu_y + busize){
            // game over
            if(timer != null){
                timer.cancel();
                timer = null;
            }
            //show result
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!start_flag) {
            start_flag = true;
            StartLabel.setVisibility(View.GONE);

            //FrameHeight
            FrameLayout frameLayout = findViewById(R.id.frame);
            FrameHeight = frameLayout.getHeight();
            //BuSize
            bu_y = bu.getY();
            busize = bu.getHeight();

            //repeative task
            timer.schedule(new TimerTask() {
                @Override
               public void run() {
                    changePos();
               }
            }, 0, 20);
        }
        else{
            //Action
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