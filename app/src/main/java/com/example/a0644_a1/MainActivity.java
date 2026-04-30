package com.example.a0644_a1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private static final String PREF_NAME = "cinefast_session_pref_v3";

    private String currentMovieTitle;
    private int currentMovieImage;
    private boolean currentMovieIsComingSoon;
    private String currentMovieTrailerUrl;
    private ArrayList<String> currentSelectedSeats;
    private ArrayList<String> snackNames;
    private ArrayList<Double> snackPrices;
    private ArrayList<Integer> snackQuantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Required toast with tag "CineFAST"
        Toast.makeText(this, "CineFAST", Toast.LENGTH_SHORT).show();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                loadFragment(new HomeFragment(), false);
            } else if (id == R.id.nav_my_bookings) {
                loadFragment(new MyBookingsFragment(), false);
            } else if (id == R.id.nav_logout) {
                logout();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), false);
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().clear().apply();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        if (addToBackStack) ft.addToBackStack(null);
        ft.commit();
    }

    public void setSelectedMovie(String title, int imageRes, boolean isComingSoon, String trailerUrl) {
        this.currentMovieTitle = title;
        this.currentMovieImage = imageRes;
        this.currentMovieIsComingSoon = isComingSoon;
        this.currentMovieTrailerUrl = trailerUrl;
    }
    public String getCurrentMovieTitle() { return currentMovieTitle; }
    public int getCurrentMovieImage() { return currentMovieImage; }
    public boolean isCurrentMovieComingSoon() { return currentMovieIsComingSoon; }
    public String getCurrentMovieTrailerUrl() { return currentMovieTrailerUrl; }

    public void setSelectedSeats(ArrayList<String> seats) { this.currentSelectedSeats = seats; }
    public ArrayList<String> getCurrentSelectedSeats() { return currentSelectedSeats; }

    public void setSnackData(ArrayList<String> names, ArrayList<Double> prices, ArrayList<Integer> qtys) {
        this.snackNames = names;
        this.snackPrices = prices;
        this.snackQuantities = qtys;
    }
    public ArrayList<String> getSnackNames() { return snackNames; }
    public ArrayList<Double> getSnackPrices() { return snackPrices; }
    public ArrayList<Integer> getSnackQuantities() { return snackQuantities; }
}