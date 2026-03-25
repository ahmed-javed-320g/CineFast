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

public class NowShowingFragment extends Fragment {

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
        movies.add(new movie("The Dark Knight", "Action / 152 min", R.drawable.batman,
                "https://www.youtube.com/watch?v=EXeTwQWrcwY", false));
        movies.add(new movie("Inception", "Sci-Fi / 148 min", R.drawable.inception,
                "https://www.youtube.com/watch?v=YoHD9XEInc0", false));
        movies.add(new movie("Interstellar", "Sci-Fi / 169 min", R.drawable.interstellar,
                "https://www.youtube.com/watch?v=zSWdZVtXT7E", false));
        movies.add(new movie("The Shawshank Redemption", "Drama / 142 min",
                R.drawable.the_shawshank_redemption,
                "https://www.youtube.com/watch?v=PLl99DlL6b4", false));

        RecyclerView rv = view.findViewById(R.id.recyclerViewMovies);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new MovieAdapter(movies, movie -> {
            MainActivity activity = (MainActivity) requireActivity();
            activity.setSelectedMovie(movie.getTitle(), movie.getPosterResId(), false);
            activity.loadFragment(new SeatSelectionFragment(), true);
        }));
    }
}