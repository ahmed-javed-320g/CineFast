package com.example.a0644_a1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    public interface OnCancelClickListener {
        void onCancelClick(Booking booking, int position);
    }

    private ArrayList<Booking> bookings;
    private OnCancelClickListener listener;
    private Context context;

    public BookingAdapter(Context context, ArrayList<Booking> bookings, OnCancelClickListener listener) {
        this.context = context;
        this.bookings = bookings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookings.get(position);
        holder.tvMovie.setText(booking.getMovieName());
        holder.tvDateTime.setText("📅 " + booking.getDateTime());
        holder.tvTickets.setText("🎟 Tickets: " + booking.getSeats());
        holder.tvPrice.setText("💰 PKR " + String.format("%.2f", booking.getTotalPrice()));

        int resId = context.getResources().getIdentifier(
                booking.getMoviePoster(), "drawable", context.getPackageName());
        holder.ivPoster.setImageResource(resId != 0 ? resId : android.R.drawable.ic_menu_gallery);

        holder.btnCancel.setOnClickListener(v -> listener.onCancelClick(booking, position));
    }

    @Override
    public int getItemCount() { return bookings.size(); }

    public void removeItem(int position) {
        bookings.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bookings.size());
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvMovie, tvDateTime, tvTickets, tvPrice;
        Button btnCancel;

        BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.IVbookingPoster);
            tvMovie = itemView.findViewById(R.id.TVbookingMovie);
            tvDateTime = itemView.findViewById(R.id.TVbookingDateTime);
            tvTickets = itemView.findViewById(R.id.TVbookingTickets);
            tvPrice = itemView.findViewById(R.id.TVbookingPrice);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}