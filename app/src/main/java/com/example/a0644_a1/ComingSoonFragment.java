package com.example.a0644_a1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ComingSoonFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<movie> movies = new ArrayList<>();
        movies.add(new movie("Dune: Part Three", "Sci-Fi / TBA", R.drawable.dune3,
                "https://www.youtube.com/watch?v=3_9vCamtuPY", true));
        movies.add(new movie("Project Hail Mary", "Action / TBA", R.drawable.project_hail_mary,
                "https://www.youtube.com/watch?v=m08TxIsFTRI", true));
        movies.add(new movie("The RIP", "Drama / TBA", R.drawable.the_rip,
                "https://www.youtube.com/watch?v=yeR5bcbRPak", true));

        RecyclerView rv = view.findViewById(R.id.recyclerViewMovies);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new MovieAdapter(movies, movie -> {
            MainActivity activity = (MainActivity) requireActivity();
            activity.setSelectedMovie(movie.getTitle(), movie.getPosterResId(), true, movie.getTrailerUrl());
            activity.loadFragment(new SeatSelectionFragment(), true);
        }));
    }
}