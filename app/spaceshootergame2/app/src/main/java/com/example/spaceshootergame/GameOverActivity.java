package com.example.spaceshootergame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    TextView tvScore;
    Button btnPlayAgain, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int score = getIntent().getIntExtra("score", 0);

        tvScore = findViewById(R.id.tvScore);
        btnPlayAgain = findViewById(R.id.playAgainBtn);
        btnExit = findViewById(R.id.btnExit);

        tvScore.setText("Score: " + score);

        btnPlayAgain.setOnClickListener(v -> {
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        });

        btnExit.setOnClickListener(v -> finishAffinity());
    }
}