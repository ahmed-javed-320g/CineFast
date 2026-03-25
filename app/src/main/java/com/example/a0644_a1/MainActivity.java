package com.example.a0644_a1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String currentMovieTitle;
    private int currentMovieImage;
    private boolean currentMovieIsComingSoon;
    private ArrayList<String> currentSelectedSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), false);
        }
    }

    public void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        if (addToBackStack) ft.addToBackStack(null);
        ft.commit();
    }

    // Mediator methods for passing data between fragments
    public void setSelectedMovie(String title, int imageRes, boolean isComingSoon) {
        this.currentMovieTitle = title;
        this.currentMovieImage = imageRes;
        this.currentMovieIsComingSoon = isComingSoon;
    }

    public String getCurrentMovieTitle() { return currentMovieTitle; }
    public int getCurrentMovieImage() { return currentMovieImage; }
    public boolean isCurrentMovieComingSoon() { return currentMovieIsComingSoon; }

    public void setSelectedSeats(ArrayList<String> seats) {
        this.currentSelectedSeats = seats;
    }
    public ArrayList<String> getCurrentSelectedSeats() { return currentSelectedSeats; }

    // ADD these fields
    private ArrayList<String> snackNames;
    private ArrayList<Double> snackPrices;
    private ArrayList<Integer> snackQuantities;

    // ADD these methods
    public void setSnackData(ArrayList<String> names, ArrayList<Double> prices, ArrayList<Integer> qtys) {
        this.snackNames = names;
        this.snackPrices = prices;
        this.snackQuantities = qtys;
    }
    public ArrayList<String> getSnackNames() { return snackNames; }
    public ArrayList<Double> getSnackPrices() { return snackPrices; }
    public ArrayList<Integer> getSnackQuantities() { return snackQuantities; }
}

