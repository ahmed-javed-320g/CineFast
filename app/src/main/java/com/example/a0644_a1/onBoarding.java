package com.example.a0644_a1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class onBoarding extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        Button btngetstarted = findViewById(R.id.btngetstarted);
        btngetstarted.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(onBoarding.this,movieList.class);
                startActivity(intent);
            }
        });
    }
}
