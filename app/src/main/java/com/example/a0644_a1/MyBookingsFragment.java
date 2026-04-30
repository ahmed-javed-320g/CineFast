package com.example.a0644_a1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class MyBookingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private ArrayList<Booking> bookingList;
    private ProgressBar progressBar;
    private TextView tvNoBookings;
    private DatabaseReference bookingsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        tvNoBookings = view.findViewById(R.id.TVnoBookings);
        recyclerView = view.findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bookingList = new ArrayList<>();
        adapter = new BookingAdapter(requireContext(), bookingList, this::onCancelClicked);
        recyclerView.setAdapter(adapter);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            progressBar.setVisibility(View.GONE);
            tvNoBookings.setVisibility(View.VISIBLE);
            return;
        }

        String userId = auth.getCurrentUser().getUid();
        bookingsRef = FirebaseDatabase.getInstance()
                .getReference("bookings").child(userId);
        loadBookings();
    }

    private void loadBookings() {
        bookingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Booking booking = child.getValue(Booking.class);
                    if (booking != null) {
                        booking.setBookingId(child.getKey());
                        bookingList.add(booking);
                    }
                }
                progressBar.setVisibility(View.GONE);
                if (bookingList.isEmpty()) {
                    tvNoBookings.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    tvNoBookings.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Failed to load bookings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onCancelClicked(Booking booking, int position) {
        // Can only cancel future bookings
        if (booking.getTimestamp() <= System.currentTimeMillis()) {
            Toast.makeText(requireContext(), "Cannot cancel past bookings", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Cancel Booking")
                .setMessage("Are you sure you want to cancel this booking?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    bookingsRef.child(booking.getBookingId()).removeValue()
                            .addOnSuccessListener(unused -> {
                                adapter.removeItem(position);
                                Toast.makeText(requireContext(),
                                        "Booking Cancelled Successfully", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(requireContext(),
                                            "Cancellation failed", Toast.LENGTH_SHORT).show());
                })
                .setNegativeButton("No", null)
                .show();
    }
}