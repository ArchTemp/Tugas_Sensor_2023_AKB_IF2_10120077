package com.example.aplikasiresto;

//NAMA  : Mohammad Noor Ihsan Akbar
//NIM   : 10120077
//KELAS : IF-2

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class splash extends AppCompatActivity {

    ImageView splashimg;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        splashimg = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.lottie);

        splashimg.animate().translationY(-2500).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1500).setDuration(1000).setStartDelay(2500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splash.this, IntroActivity.class));
                finish();
            }
        }, 2500);
    }
}