package com.example.a0644_a1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class TicketSummaryFragment extends Fragment {

    private static final double SEAT_PRICE = 16.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ticket_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) requireActivity();
        String movieTitle = activity.getCurrentMovieTitle();
        int movieImage = activity.getCurrentMovieImage();
        ArrayList<String> seats = activity.getCurrentSelectedSeats();
        ArrayList<String> snackNames = activity.getSnackNames();
        ArrayList<Double> snackPrices = activity.getSnackPrices();
        ArrayList<Integer> snackQtys = activity.getSnackQuantities();

        TextView tvTitle = view.findViewById(R.id.TVMovieTitle);
        ImageView ivPoster = view.findViewById(R.id.IVMoviePoster);
        if (movieTitle != null) tvTitle.setText(movieTitle);
        if (movieImage != 0) ivPoster.setImageResource(movieImage);

        LinearLayout llTickets = view.findViewById(R.id.LLTickets);
        LinearLayout llSnacks = view.findViewById(R.id.LLSnacks);
        TextView tvTotal = view.findViewById(R.id.TVTotalPrice);

        double total = 0;

        if (seats != null) {
            for (String seat : seats) {
                addRow(llTickets, seat, "16 PKR");
                total += SEAT_PRICE;
            }
        }

        if (snackNames != null) {
            for (int i = 0; i < snackNames.size(); i++) {
                double itemTotal = snackPrices.get(i) * snackQtys.get(i);
                addRow(llSnacks, "x" + snackQtys.get(i) + " " + snackNames.get(i),
                        String.format("%.2f PKR", itemTotal));
                total += itemTotal;
            }
        }

        tvTotal.setText(String.format("%.2f PKR", total));

        // Save to SharedPreferences
        int numSeats = seats != null ? seats.size() : 0;
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("CineFAST", Context.MODE_PRIVATE);
        prefs.edit()
                .putString("last_movie", movieTitle)
                .putInt("last_seats", numSeats)
                .putFloat("last_price", (float) total)
                .apply();

        Button btnSend = view.findViewById(R.id.btnSendTicket);
        btnSend.setOnClickListener(v -> {
            // Keep your existing WhatsApp / email logic here
            Toast.makeText(requireContext(), "Ticket sent!", Toast.LENGTH_SHORT).show();
        });
    }

    private void addRow(LinearLayout parent, String label, String price) {
        LinearLayout row = new LinearLayout(requireContext());
        row.setOrientation(LinearLayout.HORIZONTAL);

        TextView tvLabel = new TextView(requireContext());
        tvLabel.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        tvLabel.setText(label);
        tvLabel.setTextColor(0xFFFFFFFF);

        TextView tvPrice = new TextView(requireContext());
        tvPrice.setText(price);
        tvPrice.setTextColor(0xFFFFFFFF);

        row.addView(tvLabel);
        row.addView(tvPrice);
        parent.addView(row);
    }
}