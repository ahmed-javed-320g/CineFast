package com.example.a0644_a1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class SeatSelectionFragment extends Fragment {

    private ArrayList<String> selectedSeats;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seat_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) requireActivity();
        ArrayList<String> restored = activity.getCurrentSelectedSeats();
        selectedSeats = (restored != null) ? new ArrayList<>(restored) : new ArrayList<>();

        String title = activity.getCurrentMovieTitle();
        int movieImage = activity.getCurrentMovieImage();
        boolean isComingSoon = activity.isCurrentMovieComingSoon();

        TextView tvTitle = view.findViewById(R.id.TVMovieTitle);
        tvTitle.setText(title);

        view.findViewById(R.id.btnGoBack).setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());

        GridLayout grid = view.findViewById(R.id.GridSeats);
        int rows = 8, cols = 9;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (col == 4) {
                    View space = new View(requireContext());
                    GridLayout.LayoutParams sp = new GridLayout.LayoutParams();
                    sp.width = 40; sp.height = 40;
                    space.setLayoutParams(sp);
                    grid.addView(space);
                    continue;
                }
                ImageView seat = new ImageView(requireContext());
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 70; params.height = 70;
                params.setMargins(8, 8, 8, 8);
                seat.setLayoutParams(params);
                int seatIndex = row * cols + col;

                if (seatIndex % 7 == 0) {
                    seat.setImageResource(R.drawable.seat_booked);
                    seat.setTag("booked");
                } else {
                    String seatLabel = "Row " + (row + 1) + " Seat " + (col + 1);
                    if (selectedSeats.contains(seatLabel)) {
                        seat.setImageResource(R.drawable.seat_yours);
                        seat.setTag("yours");
                    } else {
                        seat.setImageResource(R.drawable.seat_available);
                        seat.setTag("available");
                    }
                }

                if (!isComingSoon) {
                    int currentRow = row, currentCol = col;
                    seat.setOnClickListener(v -> {
                        String state = (String) seat.getTag();
                        String label = "Row " + (currentRow + 1) + " Seat " + (currentCol + 1);
                        if ("available".equals(state)) {
                            seat.setImageResource(R.drawable.seat_yours);
                            seat.setTag("yours");
                            selectedSeats.add(label);
                        } else if ("yours".equals(state)) {
                            seat.setImageResource(R.drawable.seat_available);
                            seat.setTag("available");
                            selectedSeats.remove(label);
                        }
                    });
                }
                grid.addView(seat);
            }
        }

        Button btnPrimary = view.findViewById(R.id.btnPrimary);
        Button btnSecondary = view.findViewById(R.id.btnSecondary);

        if (isComingSoon) {
            btnPrimary.setText("Coming Soon");
            btnPrimary.setEnabled(false);
            btnPrimary.setClickable(false);
            btnPrimary.setFocusable(false);
            btnPrimary.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(android.graphics.Color.GRAY));
            btnPrimary.setTextColor(android.graphics.Color.WHITE);
            btnPrimary.setAlpha(0.6f);

            btnSecondary.setText("Watch Trailer");
            btnSecondary.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
            btnSecondary.setTextColor(android.graphics.Color.BLACK);

            String trailerUrl = activity.getCurrentMovieTrailerUrl();
            btnSecondary.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                startActivity(intent);
            });
        } else {
            btnPrimary.setText("Book Seats");
            btnSecondary.setText("Proceed to Snacks");

            btnPrimary.setOnClickListener(v -> {
                if (selectedSeats.isEmpty()) return;
                for (int i = 0; i < grid.getChildCount(); i++) {
                    View child = grid.getChildAt(i);
                    if (child instanceof ImageView && "yours".equals(child.getTag())) {
                        ((ImageView) child).setImageResource(R.drawable.seat_booked);
                        child.setTag("booked");
                    }
                }
                Toast.makeText(requireContext(), "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                activity.setSelectedSeats(selectedSeats);
                activity.loadFragment(new TicketSummaryFragment(), true);
            });

            btnSecondary.setOnClickListener(v -> {
                if (selectedSeats.isEmpty()) return;
                activity.setSelectedSeats(selectedSeats);
                activity.loadFragment(new SnacksFragment(), true);
            });
        }
    }
}