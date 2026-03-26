package com.example.a0644_a1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import android.content.SharedPreferences;
import android.content.Context;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_last_booking) {
                    showLastBooking();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        HomePagerAdapter adapter = new HomePagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(position == 0 ? "Now Showing" : "Coming Soon");
        }).attach();
    }

    private void showLastBooking() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("CineFAST", Context.MODE_PRIVATE);
        String movie = prefs.getString("last_movie", null);

        if (movie == null) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Last Booking")
                    .setMessage("No previous booking found.")
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            int seats = prefs.getInt("last_seats", 0);
            double price = prefs.getFloat("last_price", 0f);
            String msg = "Movie: " + movie + "\nSeats: " + seats + "\nTotal Price: $" + String.format("%.2f", price);
            new AlertDialog.Builder(requireContext())
                    .setTitle("Last Booking")
                    .setMessage(msg)
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}