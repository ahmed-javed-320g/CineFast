package com.example.a0644_a1;
import android.os.Bundle;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class snacks extends AppCompatActivity {
    int qty1 = 0;
    int qty2 = 0;
    int qty3 = 0;
    int qty4 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks);

        String movieTitle = getIntent().getStringExtra("MOVIE_TITLE");
        ArrayList<String> selectedSeats = getIntent().getStringArrayListExtra("SELECTED_SEATS");
        int movieImage = getIntent().getIntExtra("MOVIE_IMAGE", 0);

        TextView tvQty1 = findViewById(R.id.tvQty1);
        Button btnPlus1 = findViewById(R.id.btnPlus1);
        Button btnMinus1 = findViewById(R.id.btnMinus1);
        btnPlus1.setOnClickListener(v -> {
            qty1++;
            tvQty1.setText(String.valueOf(qty1));
        });

        btnMinus1.setOnClickListener(v -> {
            if(qty1>0)
            {
                qty1--;
                tvQty1.setText(String.valueOf(qty1));
            }
        });

        TextView tvQty2 = findViewById(R.id.tvQty2);
        Button btnPlus2 = findViewById(R.id.btnPlus2);
        Button btnMinus2 = findViewById(R.id.btnMinus2);
        btnPlus2.setOnClickListener(v -> {
            qty2++;
            tvQty2.setText(String.valueOf(qty2));
        });

        btnMinus2.setOnClickListener(v -> {
            if(qty2>0)
            {
                qty2--;
                tvQty2.setText(String.valueOf(qty2));
            }
        });

        TextView tvQty3 = findViewById(R.id.tvQty3);
        Button btnPlus3 = findViewById(R.id.btnPlus3);
        Button btnMinus3 = findViewById(R.id.btnMinus3);
        btnPlus3.setOnClickListener(v -> {
            qty3++;
            tvQty3.setText(String.valueOf(qty3));
        });

        btnMinus3.setOnClickListener(v -> {
            if(qty3>0)
            {
                qty3--;
                tvQty3.setText(String.valueOf(qty3));
            }
        });

        TextView tvQty4 = findViewById(R.id.tvQty4);
        Button btnPlus4 = findViewById(R.id.btnPlus4);
        Button btnMinus4 = findViewById(R.id.btnMinus4);
        btnPlus4.setOnClickListener(v -> {
            qty4++;
            tvQty4.setText(String.valueOf(qty4));
        });

        btnMinus4.setOnClickListener(v -> {
            if(qty4>0)
            {
                qty4--;
                tvQty4.setText(String.valueOf(qty4));
            }
        });

        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            ArrayList<String> snackNames = new ArrayList<>();
            ArrayList<Double> snackPrices = new ArrayList<>();
            ArrayList<Integer> snackQuantity = new ArrayList<>();

            if(qty1 > 0)
            {
                snackNames.add("Popcorn");
                snackPrices.add(8.99);
                snackQuantity.add(qty1);
            }

            if(qty2 > 0)
            {
                snackNames.add("Nachos");
                snackPrices.add(7.99);
                snackQuantity.add(qty2);
            }

            if(qty3 > 0)
            {
                snackNames.add("Soft Drink");
                snackPrices.add(5.99);
                snackQuantity.add(qty3);
            }

            if(qty4 > 0)
            {
                snackNames.add("Candy Mix");
                snackPrices.add(6.99);
                snackQuantity.add(qty4);
            }

            Intent intent = new Intent(snacks.this,orderDetails.class);
            intent.putExtra("MOVIE_TITLE", movieTitle);
            intent.putStringArrayListExtra("SELECTED_SEATS", selectedSeats);
            intent.putStringArrayListExtra("SNACK_NAMES", snackNames);
            intent.putExtra("MOVIE_IMAGE", movieImage);
            intent.putExtra("SNACK_PRICES",snackPrices);
            intent.putIntegerArrayListExtra("SNACK_QUANTITIES",snackQuantity);

            startActivity(intent);
        });
    }
}
