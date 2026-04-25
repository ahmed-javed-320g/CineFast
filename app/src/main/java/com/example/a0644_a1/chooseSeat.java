package com.example.a0644_a1;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;

public class chooseSeat extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseseat);
        String title = getIntent().getStringExtra("MOVIE TITLE");
        int movieImage = getIntent().getIntExtra("MOVIE_IMAGE",0);
        TextView movieTextView = findViewById(R.id.TVMovieTitle);
        if (title != null) {
            movieTextView.setText(title);
        }

        findViewById(R.id.btnGoBack).setOnClickListener(v -> finish());
        ArrayList<String> selectedSeats = new ArrayList<>();

        GridLayout grid = findViewById(R.id.GridSeats);
        int rows = 8;
        int cols = 9;
        int totalSeats = rows*cols;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                if (col == 4) {
                    View space = new View(this);
                    GridLayout.LayoutParams spaceParams = new GridLayout.LayoutParams();
                    spaceParams.width = 40;
                    spaceParams.height = 40;
                    space.setLayoutParams(spaceParams);
                    grid.addView(space);
                    continue;
                }

                ImageView seat = new ImageView(this);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 70;
                params.height = 70;
                params.setMargins(8, 8, 8, 8);
                seat.setLayoutParams(params);

                int seatIndex = row * cols + col;

                if (seatIndex % 7 == 0) {
                    seat.setImageResource(R.drawable.seat_booked);
                    seat.setTag("booked");
                } else {
                    seat.setImageResource(R.drawable.seat_available);
                    seat.setTag("available");
                }

                int currentRow = row;
                int currentCol = col;

                seat.setOnClickListener(v -> {
                    String state = (String) seat.getTag();
                    String seatLabel = "Row " + (currentRow + 1) + " Seat " + (currentCol + 1);

                    if ("available".equals(state)) {
                        seat.setImageResource(R.drawable.seat_yours);
                        seat.setTag("yours");
                        selectedSeats.add(seatLabel);

                    } else if ("yours".equals(state)) {
                        seat.setImageResource(R.drawable.seat_available);
                        seat.setTag("available");
                        selectedSeats.remove(seatLabel);
                    }
                });

                grid.addView(seat);
            }
        }
        Button btnBookSeats = findViewById(R.id.btnbookSeats);
        Button btnProceed = findViewById(R.id.btnproceedToSnacks);

        btnBookSeats.setOnClickListener(v -> {

            if (selectedSeats.isEmpty()) return;
            for (int i = 0; i < grid.getChildCount(); i++) {
                View view = grid.getChildAt(i);
                if (view instanceof ImageView) {
                    ImageView seatView = (ImageView) view;
                    if ("yours".equals(seatView.getTag())) {
                        seatView.setImageResource(R.drawable.seat_booked);
                        seatView.setTag("booked");
                    }
                }
            }

            Intent intent = new Intent(chooseSeat.this, orderDetails.class);
            intent.putExtra("MOVIE_TITLE", title);
            intent.putExtra("MOVIE_IMAGE", movieImage);
            intent.putStringArrayListExtra("SELECTED_SEATS", selectedSeats);
            intent.putExtra("SNACKS_VALUE", 0);

            startActivity(intent);
        });

        btnProceed.setOnClickListener(v -> {

            if (selectedSeats.isEmpty()) return;

            Intent intent = new Intent(chooseSeat.this, snacks.class);
            intent.putExtra("MOVIE_TITLE", title);
            intent.putExtra("MOVIE_IMAGE", movieImage);
            intent.putStringArrayListExtra("SELECTED_SEATS", selectedSeats);
            intent.putExtra("SNACKS_VALUE", 0);

            startActivity(intent);
        });
    }
}
