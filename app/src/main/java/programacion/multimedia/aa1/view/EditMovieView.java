package programacion.multimedia.aa1.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import programacion.multimedia.aa1.R;
import programacion.multimedia.aa1.contract.EditMovieContract;
import programacion.multimedia.aa1.domain.Movie;
import programacion.multimedia.aa1.domain.Studio;
import programacion.multimedia.aa1.presenter.EditMoviePresenter;
import programacion.multimedia.aa1.util.DateUtil;

public class EditMovieView extends AppCompatActivity implements EditMovieContract.View {

    public static final String MOVIE_ID = "movie_id";

    private EditMovieContract.Presenter presenter;
    private List<Studio> studioList;
    private ArrayAdapter<Studio> studioAdapter;
    private long movieId;

    private EditText titleEditText;
    private EditText synopsisEditText;
    private EditText genreEditText;
    private EditText releaseDateEditText;
    private EditText durationEditText;
    private EditText ratingEditText;
    private Spinner studioSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        movieId = getIntent().getLongExtra(MOVIE_ID, -1);

        if (movieId == -1) {
            showError("Error: Movie not found");
            finish();
            return;
        }

        presenter = new EditMoviePresenter(this);

        initViews();
        setupSpinner();

        // Cargar studios y película
        presenter.loadStudios();
        presenter.loadMovie(movieId);
    }

    private void initViews() {
        titleEditText = findViewById(R.id.movie_title);
        synopsisEditText = findViewById(R.id.movie_synopsis);
        genreEditText = findViewById(R.id.movie_genre);
        releaseDateEditText = findViewById(R.id.movie_release_date);
        durationEditText = findViewById(R.id.movie_duration);
        ratingEditText = findViewById(R.id.movie_rating);
        studioSpinner = findViewById(R.id.movie_studio);
    }

    private void setupSpinner() {
        studioList = new ArrayList<>();
        studioAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, studioList);
        studioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studioSpinner.setAdapter(studioAdapter);
    }

    @Override
    public void showMovie(Movie movie) {
        titleEditText.setText(movie.getTitle());
        synopsisEditText.setText(movie.getSynopsis());
        genreEditText.setText(movie.getGenre());

        if (movie.getReleaseDate() != null) {
            releaseDateEditText.setText(DateUtil.formatDate(movie.getReleaseDate()));
        }

        durationEditText.setText(String.valueOf(movie.getDuration()));
        ratingEditText.setText(String.valueOf(movie.getAverageRating()));

        // Seleccionar el studio correcto en el Spinner
        if (movie.getStudio() != null) {
            for (int i = 0; i < studioList.size(); i++) {
                if (studioList.get(i).getId() == movie.getStudio().getId()) {
                    studioSpinner.setSelection(i);
                    break;
                }
            }
        }
    }

    @Override
    public void showStudios(List<Studio> studios) {
        studioList.clear();
        studioList.addAll(studios);
        studioAdapter.notifyDataSetChanged();
    }

    public void updateMovie(View view) {
        String title = titleEditText.getText().toString();
        String synopsis = synopsisEditText.getText().toString();
        String genre = genreEditText.getText().toString();
        LocalDate releaseDate = DateUtil.parseDate(releaseDateEditText.getText().toString());
        String durationStr = durationEditText.getText().toString();
        String ratingStr = ratingEditText.getText().toString();

        Studio selectedStudio = (Studio) studioSpinner.getSelectedItem();

        // Validar duración
        int duration = 0;
        try {
            duration = Integer.parseInt(durationStr);
        } catch (NumberFormatException e) {
            showValidationError(getString(R.string.duration_must_be_a_valid_number));
            return;
        }

        // Validar rating
        float rating = 0.0f;
        if (!ratingStr.isEmpty()) {
            try {
                rating = Float.parseFloat(ratingStr);
            } catch (NumberFormatException e) {
                showValidationError(getString(R.string.rating_must_be_a_valid_number));
                return;
            }
        }

        presenter.updateMovie(movieId, title, synopsis, genre, releaseDate, duration, rating, selectedStudio);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showValidationError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToMovieDetail() {
        finish(); // Vuelve a MovieDetailView
    }
}
