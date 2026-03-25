package com.example.a0644_a1;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public interface OnBookClickListener {
        void onBookClick(movie movie);
    }

    private ArrayList<movie> movies;
    private OnBookClickListener listener;

    public MovieAdapter(ArrayList<movie> movies, OnBookClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        movie movie = movies.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvGenre.setText(movie.getGenre());
        holder.ivPoster.setImageResource(movie.getPosterResId());

        holder.btnTrailer.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getTrailerUrl()));
            v.getContext().startActivity(intent);
        });

        holder.btnBook.setOnClickListener(v -> listener.onBookClick(movie));
    }

    @Override
    public int getItemCount() { return movies.size(); }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvGenre;
        Button btnBook, btnTrailer;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.IVmovie);
            tvTitle = itemView.findViewById(R.id.TVmovieTitle);
            tvGenre = itemView.findViewById(R.id.TVmovieGenre);
            btnBook = itemView.findViewById(R.id.btnBook);
            btnTrailer = itemView.findViewById(R.id.btnTrailer);
        }
    }
}