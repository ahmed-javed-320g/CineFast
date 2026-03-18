package com.example.a0644_a1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.Toast;
import android.net.Uri;


public class orderDetails extends AppCompatActivity {
    double seatPrice = 16.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);

        String movieTitle = getIntent().getStringExtra("MOVIE_TITLE");
        int movieImage = getIntent().getIntExtra("MOVIE_IMAGE",0);
        ArrayList<String> selectedSeats = getIntent().getStringArrayListExtra("SELECTED_SEATS");
        ArrayList<String> snackNames = getIntent().getStringArrayListExtra("SNACK_NAMES");
        ArrayList<Double> snackPrices = (ArrayList<Double>) getIntent().getSerializableExtra("SNACK_PRICES");
        ArrayList<Integer> snackQuantity = (ArrayList<Integer>) getIntent().getIntegerArrayListExtra("SNACK_QUANTITIES");

        TextView tvMovieTitle = findViewById(R.id.TVMovieTitle);
        ImageView IVMoviePoster = findViewById(R.id.IVMoviePoster);

        if(movieTitle!= null)
        {
            tvMovieTitle.setText(movieTitle);
        }

        if(movieImage != 0)
        {
            IVMoviePoster.setImageResource(movieImage);
        }

        LinearLayout LLTickets = findViewById(R.id.LLTickets);
        LinearLayout LLSnacks = findViewById(R.id.LLSnacks);
        TextView TVTotalPrice = findViewById(R.id.TVTotalPrice);

        double total = 0;

        if (selectedSeats != null)
        {
            for (String seat : selectedSeats)
            {
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);

                TextView seatText = new TextView(this);
                seatText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                seatText.setText(seat);
                seatText.setTextColor(getResources().getColor(android.R.color.white));

                TextView priceText = new TextView(this);
                priceText.setText("16 PKR");
                priceText.setTextColor(getResources().getColor(android.R.color.white));

                row.addView(seatText);
                row.addView(priceText);
                LLTickets.addView(row);
                total += seatPrice;
            }
        }


        if (snackNames != null && snackPrices != null && snackQuantity != null)
        {
            for (int i = 0; i < snackNames.size(); i++)
            {
                double itemTotal = snackPrices.get(i) * snackQuantity.get(i);
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);

                TextView snackText = new TextView(this);
                snackText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                snackText.setText("x" + snackQuantity.get(i) + " " + snackNames.get(i));
                snackText.setTextColor(getResources().getColor(android.R.color.white));

                TextView priceText = new TextView(this);
                priceText.setText(String.format("%.2f PKR", itemTotal));
                priceText.setTextColor(getResources().getColor(android.R.color.white));

                row.addView(snackText);
                row.addView(priceText);
                LLSnacks.addView(row);
                total += itemTotal;
            }
        }

        TVTotalPrice.setText(String.format("%.2f PKR", total));
        StringBuilder orderSummary = new StringBuilder();

        orderSummary.append("BOOKING DETAILS\n\n");
        orderSummary.append("Movie: ").append(movieTitle).append("\n\n");

        orderSummary.append("Seats:\n");
        if (selectedSeats != null) {
            for (String seat : selectedSeats) {
                orderSummary.append("- ").append(seat).append(" (16 PKR)\n");
            }
        }

        orderSummary.append("\nSnacks:\n");
        if (snackNames != null) {
            for (int i = 0; i < snackNames.size(); i++) {
                orderSummary.append("- ").append(snackQuantity.get(i)).append(" x ").append(snackNames.get(i)).append("\n");
            }
        }

        orderSummary.append("\nTOTAL: ").append(String.format("%.2f PKR", total));


        Button btnSendTicket = findViewById(R.id.btnSendTicket);
        btnSendTicket.setOnClickListener(v -> {

            String phoneNumber = "923235789789";

            String message = Uri.encode(orderSummary.toString());

            Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
            whatsappIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone="
                            + phoneNumber + "&text=" + message));

            whatsappIntent.setPackage("com.whatsapp");

            try
            {
                startActivity(whatsappIntent);
            } catch (Exception e)
            {
                Toast.makeText(this, "WhatsApp not installed or number invalid", Toast.LENGTH_LONG).show();
            }



            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ahmed.javed.320g@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Movie Ticket Booking");
            emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummary.toString());

            startActivity(Intent.createChooser(emailIntent, "Send Ticket via Email"));
        });

    }
}
