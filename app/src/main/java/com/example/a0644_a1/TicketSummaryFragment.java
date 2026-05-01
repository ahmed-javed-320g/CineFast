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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

        view.findViewById(R.id.btnGoBack).setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());

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

        // Save locally for "Last Booking" menu
        int numSeats = seats != null ? seats.size() : 0;
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("CineFAST", Context.MODE_PRIVATE);
        prefs.edit()
                .putString("last_movie", movieTitle)
                .putInt("last_seats", numSeats)
                .putFloat("last_price", (float) total)
                .apply();

        // Save booking to Firebase
        saveBookingToFirebase(movieTitle, movieImage, seats, total);

        // Build order summary for sharing
        StringBuilder orderSummary = new StringBuilder();
        orderSummary.append("BOOKING DETAILS\n\n");
        orderSummary.append("Movie: ").append(movieTitle).append("\n\n");
        orderSummary.append("Seats:\n");
        if (seats != null) {
            for (String seat : seats) {
                orderSummary.append("- ").append(seat).append(" (16 PKR)\n");
            }
        }
        orderSummary.append("\nSnacks:\n");
        if (snackNames != null) {
            for (int i = 0; i < snackNames.size(); i++) {
                orderSummary.append("- ").append(snackQtys.get(i))
                        .append(" x ").append(snackNames.get(i)).append("\n");
            }
        }
        orderSummary.append("\nTOTAL: ").append(String.format("%.2f PKR", total));

        Button btnSend = view.findViewById(R.id.btnSendTicket);
        btnSend.setOnClickListener(v -> {
            String phoneNumber = "923235789789";
            String message = Uri.encode(orderSummary.toString());
            Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
            whatsappIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message));
            whatsappIntent.setPackage("com.whatsapp");
            try {
                startActivity(whatsappIntent);
            } catch (Exception e) {
                Toast.makeText(requireContext(), "WhatsApp not installed", Toast.LENGTH_LONG).show();
            }

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ahmed.javed.320g@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Movie Ticket Booking");
            emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummary.toString());
            startActivity(Intent.createChooser(emailIntent, "Send Ticket via Email"));
        });
    }

    private void saveBookingToFirebase(String movieTitle, int movieImage,
                                       ArrayList<String> seats, double total) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) return;
        String userId = auth.getCurrentUser().getUid();

        DatabaseReference bookingsRef = FirebaseDatabase.getInstance("https://cinefast-c8347-default-rtdb.firebaseio.com/")
                .getReference("bookings").child(userId);
        String bookingId = bookingsRef.push().getKey();
        if (bookingId == null) return;

        long futureTimestamp = System.currentTimeMillis() + (24L * 60L * 60L * 1000L); // 1 day in the future
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date(futureTimestamp));

        String posterName = "";
        try {
            posterName = requireContext().getResources().getResourceEntryName(movieImage);
        } catch (Exception e) {
            posterName = "";
        }

        Map<String, Object> booking = new HashMap<>();
        booking.put("movieName", movieTitle);
        booking.put("moviePoster", posterName);
        booking.put("seats", seats != null ? seats.size() : 0);
        booking.put("totalPrice", total);
        booking.put("dateTime", dateTime);
        booking.put("timestamp", futureTimestamp);

        bookingsRef.child(bookingId).setValue(booking)
                .addOnSuccessListener(unused ->
                        Toast.makeText(requireContext(), "Booking saved!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(requireContext(), "Failed to save booking", Toast.LENGTH_SHORT).show());
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