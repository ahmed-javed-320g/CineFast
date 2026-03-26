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
        // Use whatever posters/images you have — these are placeholders
        movies.add(new movie("Dune: Part Three", "Sci-Fi / TBA", R.drawable.interstellar,
                "https://www.youtube.com/watch?v=zSWdZVtXT7E", true));
        movies.add(new movie("Avatar 3", "Action / TBA", R.drawable.batman,
                "https://www.youtube.com/watch?v=EXeTwQWrcwY", true));
        movies.add(new movie("The Sequel", "Drama / TBA", R.drawable.inception,
                "https://www.youtube.com/watch?v=YoHD9XEInc0", true));

        RecyclerView rv = view.findViewById(R.id.recyclerViewMovies);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new MovieAdapter(movies, movie -> {
            MainActivity activity = (MainActivity) requireActivity();
            activity.setSelectedMovie(movie.getTitle(), movie.getPosterResId(), true, movie.getTrailerUrl());
            activity.loadFragment(new SeatSelectionFragment(), true);
        }));
    }
}