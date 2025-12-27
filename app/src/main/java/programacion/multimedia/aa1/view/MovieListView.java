package programacion.multimedia.aa1.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import programacion.multimedia.aa1.R;
import programacion.multimedia.aa1.adapter.MovieAdapter;
import programacion.multimedia.aa1.contract.MovieListContract;
import programacion.multimedia.aa1.domain.Movie;
import programacion.multimedia.aa1.presenter.MovieListPresenter;

public class MovieListView extends AppCompatActivity implements MovieListContract.View {

    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private MovieListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MovieListPresenter(this);

        movieList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.movies_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        movieAdapter = new MovieAdapter(this, movieList);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.loadMovies();
    }

    @Override
    public void showMovies(List<Movie> movies) {
        movieList.clear();
        movieList.addAll(movies);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_register_movie) {
            Intent intent = new Intent(this, RegisterMovieView.class);
            startActivity(intent);

            return true;
        } else if (item.getItemId() == R.id.action_preferences_item) {
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
        }

        return false;
    }
}
