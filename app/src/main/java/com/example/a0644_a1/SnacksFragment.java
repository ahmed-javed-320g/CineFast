package com.example.a0644_a1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class SnacksFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_snacks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<snack> snacks = new ArrayList<>();
        snacks.add(new snack("Popcorn", "Large / Buttered", 8.99, R.drawable.popcorn));
        snacks.add(new snack("Nachos", "With Cheese Dip", 7.99, R.drawable.nachos));
        snacks.add(new snack("Soft Drink", "Large / Any Flavor", 5.99, R.drawable.soft_drink));
        snacks.add(new snack("Candy Mix", "Assorted Candies", 6.99, R.drawable.candy_mix));

        SnackAdapter adapter = new SnackAdapter(requireContext(), snacks);
        ListView listView = view.findViewById(R.id.listViewSnacks);
        listView.setAdapter(adapter);

        Button btnConfirm = view.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            ArrayList<String> snackNames = new ArrayList<>();
            ArrayList<Double> snackPrices = new ArrayList<>();
            ArrayList<Integer> snackQtys = new ArrayList<>();

            for (int i = 0; i < snacks.size(); i++) {
                int qty = adapter.getQuantities().get(i);
                if (qty > 0) {
                    snackNames.add(snacks.get(i).getName());
                    snackPrices.add(snacks.get(i).getPrice());
                    snackQtys.add(qty);
                }
            }

            MainActivity activity = (MainActivity) requireActivity();
            activity.setSnackData(snackNames, snackPrices, snackQtys);
            activity.loadFragment(new TicketSummaryFragment(), true);
        });
    }
}