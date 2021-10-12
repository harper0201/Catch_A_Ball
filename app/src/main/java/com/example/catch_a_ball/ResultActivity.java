package com.example.catch_a_ball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView ScoreLabel = findViewById(R.id.ScoreLabel);
        TextView HighScoreLabel = findViewById(R.id.HighScoreLabel);
        int score = getIntent().getIntExtra("SCORE",0);
        ScoreLabel.setText(score + " ");

        SharedPreferences sharedPreferences = this.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int HighScore = sharedPreferences.getInt("HIGH_CORE",0);;
            if(HighScore < score){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("HIGH SCORE",score);
                editor.commit();
                HighScoreLabel.setText("high score:" + score);
            }
            else{
                HighScoreLabel.setText("high score:" + HighScore);
            }
    }

    public void TryAgain(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}