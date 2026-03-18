package com.example.a0644_a1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView logo;
        logo = findViewById(R.id.logo);
        Animation logoAnim = AnimationUtils.loadAnimation(this, R.anim.animated_logo);
        logo.startAnimation(logoAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this,onBoarding.class);
                startActivity(intent);
                finish();
            }
        },7000);
    }
}
