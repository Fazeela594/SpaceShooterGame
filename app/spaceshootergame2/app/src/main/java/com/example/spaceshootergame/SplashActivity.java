package com.example.spaceshootergame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    Button startBtn;
    ProgressBar progressBar;
    TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startBtn = findViewById(R.id.startBtn);
        progressBar = findViewById(R.id.progress);
        loadingText = findViewById(R.id.loadingText);

        // hide loading initially
        progressBar.setVisibility(View.GONE);
        loadingText.setVisibility(View.GONE);

        startBtn.setOnClickListener(v -> {

            startBtn.setVisibility(View.GONE);

            progressBar.setVisibility(View.VISIBLE);
            loadingText.setVisibility(View.VISIBLE);

            new Thread(() -> {

                int[] progress = {0};  // ✅ FIX (no lambda error)

                while (progress[0] < 100) {

                    progress[0] += 2;

                    runOnUiThread(() ->
                            progressBar.setProgress(progress[0])
                    );

                    try {
                        Thread.sleep(40);
                    } catch (Exception ignored) {}
                }

                runOnUiThread(() -> {
                    startActivity(new Intent(
                            SplashActivity.this,
                            MainActivity.class
                    ));
                    finish();
                });

            }).start();
        });
    }
}