package com.example.a0644_a1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class movieList extends AppCompatActivity {
    Button btnToday, btnTomorrow;
    private void openYouTube(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
    private void updateDateButtons(Button selected, Button unselected) {
        selected.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E50914")));
        selected.setTextColor(Color.WHITE);

        unselected.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1A1A1A")));
        unselected.setTextColor(Color.parseColor("#888888"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);

        btnToday = findViewById(R.id.btnToday);
        btnTomorrow = findViewById(R.id.btnTomorow);
        btnToday.setOnClickListener(v -> {
            updateDateButtons(btnToday,btnTomorrow);
        });
        btnTomorrow.setOnClickListener(v -> {
            updateDateButtons(btnTomorrow,btnToday);
        });

        findViewById(R.id.btnTrailer1).setOnClickListener(v -> {
            openYouTube("https://www.youtube.com/watch?v=EXeTwQWrcwY");
        });

        findViewById(R.id.btnTrailer2).setOnClickListener(v -> {
            openYouTube("https://www.youtube.com/watch?v=YoHD9XEInc0");
        });

        findViewById(R.id.btnTrailer3).setOnClickListener(v -> {
            openYouTube("https://www.youtube.com/watch?v=zSWdZVtXT7E");
        });

        findViewById(R.id.btnTrailer4).setOnClickListener(v -> {
            openYouTube("https://www.youtube.com/watch?v=PLl99DlL6b4");
        });

        findViewById(R.id.btnBook1).setOnClickListener(v -> {
            Intent intent = new Intent(movieList.this,chooseSeat.class);
            intent.putExtra("MOVIE TITLE", "The Dark Knight");
            intent.putExtra("MOVIE_IMAGE", R.drawable.batman);
            startActivity(intent);
        });

        findViewById(R.id.btnBook2).setOnClickListener(v -> {
            Intent intent = new Intent(movieList.this,chooseSeat.class);
            intent.putExtra("MOVIE TITLE", "Inception");
            intent.putExtra("MOVIE_IMAGE", R.drawable.inception);
            startActivity(intent);
        });

        findViewById(R.id.btnBook3).setOnClickListener(v -> {
            Intent intent = new Intent(movieList.this,chooseSeat.class);
            intent.putExtra("MOVIE TITLE", "Interstellar");
            intent.putExtra("MOVIE_IMAGE", R.drawable.interstellar);
            startActivity(intent);
        });

        findViewById(R.id.btnBook4).setOnClickListener(v -> {
            Intent intent = new Intent(movieList.this,chooseSeat.class);
            intent.putExtra("MOVIE TITLE", "The Shawshank Redemption");
            intent.putExtra("MOVIE_IMAGE", R.drawable.the_shawshank_redemption);
            startActivity(intent);
        });
    }

}
