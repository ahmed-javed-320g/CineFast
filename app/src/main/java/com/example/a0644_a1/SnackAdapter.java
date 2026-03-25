package com.example.a0644_a1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class SnackAdapter extends ArrayAdapter<Snack> {

    private ArrayList<Integer> quantities;

    public SnackAdapter(Context context, ArrayList<Snack> snacks) {
        super(context, 0, snacks);
        quantities = new ArrayList<>();
        for (int i = 0; i < snacks.size(); i++) quantities.add(0);
    }

    public ArrayList<Integer> getQuantities() { return quantities; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_snack, parent, false);
        }
        Snack snack = getItem(position);
        ImageView iv = convertView.findViewById(R.id.IVsnack);
        TextView tvName = convertView.findViewById(R.id.tvSnackName);
        TextView tvPrice = convertView.findViewById(R.id.tvSnackPrice);
        TextView tvQty = convertView.findViewById(R.id.tvQty);
        Button btnPlus = convertView.findViewById(R.id.btnPlus);
        Button btnMinus = convertView.findViewById(R.id.btnMinus);

        iv.setImageResource(snack.getImageResId());
        tvName.setText(snack.getName());
        tvPrice.setText(String.format("$%.2f", snack.getPrice()));
        tvQty.setText(String.valueOf(quantities.get(position)));

        btnPlus.setOnClickListener(v -> {
            quantities.set(position, quantities.get(position) + 1);
            tvQty.setText(String.valueOf(quantities.get(position)));
        });

        btnMinus.setOnClickListener(v -> {
            if (quantities.get(position) > 0) {
                quantities.set(position, quantities.get(position) - 1);
                tvQty.setText(String.valueOf(quantities.get(position)));
            }
        });

        return convertView;
    }
}