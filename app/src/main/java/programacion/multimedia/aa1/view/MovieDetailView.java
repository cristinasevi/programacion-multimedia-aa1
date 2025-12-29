package programacion.multimedia.aa1.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import programacion.multimedia.aa1.R;
import programacion.multimedia.aa1.contract.MovieDetailContract;
import programacion.multimedia.aa1.db.FavoriteMovieDao;
import programacion.multimedia.aa1.db.DatabaseUtil;
import programacion.multimedia.aa1.domain.FavoriteMovie;
import programacion.multimedia.aa1.domain.Movie;
import programacion.multimedia.aa1.presenter.MovieDetailPresenter;
import programacion.multimedia.aa1.util.DateUtil;
import programacion.multimedia.aa1.util.DialogUtil;

public class MovieDetailView extends AppCompatActivity implements MovieDetailContract.View {

    public static final String movie_id = "movie_id";

    private MovieDetailContract.Presenter presenter;
    private FavoriteMovieDao favoriteMovieDao;
    private long movieId;
    private Movie currentMovie;

    private ImageView posterImageView;
    private TextView titleTextView;
    private TextView ratingTextView;
    private TextView genreTextView;
    private TextView durationTextView;
    private TextView releaseDateTextView;
    private TextView studioTextView;
    private TextView synopsisTextView;
    private CheckBox favoriteCheckBox;
    private Button editButton;
    private Button deleteButton;
    private Button reviewsButton;
    private Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieId = getIntent().getLongExtra(movie_id, -1);

        if (movieId == -1) {
            showError("Error: No se encontró la película");
            finish();
            return;
        }

        presenter = new MovieDetailPresenter(this);
        favoriteMovieDao = DatabaseUtil.getDb(this).favoriteMovieDao();

        initViews();
        setupListeners();
        presenter.loadMovie(movieId);
    }

    private void initViews() {
        posterImageView = findViewById(R.id.movie_detail_poster);
        titleTextView = findViewById(R.id.movie_detail_title);
        ratingTextView = findViewById(R.id.movie_detail_rating);
        genreTextView = findViewById(R.id.movie_detail_genre);
        durationTextView = findViewById(R.id.movie_detail_duration);
        releaseDateTextView = findViewById(R.id.movie_detail_release_date);
        studioTextView = findViewById(R.id.movie_detail_studio);
        synopsisTextView = findViewById(R.id.movie_detail_synopsis);
        favoriteCheckBox = findViewById(R.id.checkbox_favorite);
        editButton = findViewById(R.id.button_edit_movie);
        deleteButton = findViewById(R.id.button_delete_movie);
        reviewsButton = findViewById(R.id.button_view_reviews);
        mapButton = findViewById(R.id.button_view_map);
    }

    private void setupListeners() {
        editButton.setOnClickListener(v -> editMovie());
        deleteButton.setOnClickListener(v -> presenter.deleteMovie(movieId));
        reviewsButton.setOnClickListener(v -> viewReviews());
        mapButton.setOnClickListener(v -> viewStudioOnMap());
        favoriteCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> toggleFavorite(isChecked));
    }

    @Override
    public void showMovie(Movie movie) {
        this.currentMovie = movie;

        titleTextView.setText(movie.getTitle());

        if (movie.getAverageRating() > 0) {
            ratingTextView.setText("★ " + String.format("%.1f", movie.getAverageRating()));
        } else {
            ratingTextView.setText("★ N/A");
        }

        genreTextView.setText(getString(R.string.genre) + ": " + (movie.getGenre() != null ? movie.getGenre() : "N/A"));
        durationTextView.setText(movie.getDuration() + " min");

        if (movie.getReleaseDate() != null) {
            releaseDateTextView.setText(DateUtil.formatDate(movie.getReleaseDate()));
        } else {
            releaseDateTextView.setText("N/A");
        }

        if (movie.getStudio() != null) {
            studioTextView.setText(getString(R.string.studio) + ": " + movie.getStudio().getName());
        } else {
            studioTextView.setText(getString(R.string.studio) + ": N/A");
        }

        if (movie.getSynopsis() != null && !movie.getSynopsis().isEmpty()) {
            synopsisTextView.setText(movie.getSynopsis());
        } else {
            synopsisTextView.setText("No synopsis available");
        }

        if (movie.getImageUrl() != null && !movie.getImageUrl().isEmpty()) {
            Glide.with(this)
                    .load(movie.getImageUrl())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_gallery)
                    .into(posterImageView);
        } else {
            posterImageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // Cargar estado de favorito
        boolean isFavorite = favoriteMovieDao.findByTitle(movie.getTitle()) != null;
        favoriteCheckBox.setChecked(isFavorite);
    }

    @Override
    public void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = DialogUtil.alertDialogBuilder(
                this,
                getString(R.string.confirm_delete)
        );

        builder.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
            presenter.onDeleteConfirmed(movieId);
        });

        builder.show();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToMovieList() {
        finish();
    }

    private void editMovie() {
        Intent intent = new Intent(this, EditMovieView.class);
        intent.putExtra(EditMovieView.MOVIE_ID, movieId);
        startActivity(intent);
    }

    private void viewReviews() {
        Intent intent = new Intent(this, ReviewListView.class);
        intent.putExtra(ReviewListView.MOVIE_ID, movieId);
        startActivity(intent);
    }

    private void viewStudioOnMap() {
        Toast.makeText(this, "View map - To be implemented", Toast.LENGTH_SHORT).show();
    }

    private void toggleFavorite(boolean isChecked) {
        if (currentMovie == null) return;

        if (isChecked) {
            // Añadir a favoritos
            FavoriteMovie favorite = new FavoriteMovie(
                    currentMovie.getTitle(),
                    currentMovie.getSynopsis(),
                    currentMovie.getReleaseDate(),
                    currentMovie.getDuration(),
                    currentMovie.getGenre(),
                    currentMovie.getAverageRating(),
                    currentMovie.getImageUrl(),
                    currentMovie.getStudio() != null ? currentMovie.getStudio().getName() : null,
                    currentMovie.getDirector() != null ? currentMovie.getDirector().getName() : null
            );

            favoriteMovieDao.insert(favorite);
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            // Eliminar de favoritos
            favoriteMovieDao.deleteByTitle(currentMovie.getTitle());
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        }
    }
}
