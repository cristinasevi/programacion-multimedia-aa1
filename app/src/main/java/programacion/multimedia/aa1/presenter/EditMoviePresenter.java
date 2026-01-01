package programacion.multimedia.aa1.presenter;

import java.time.LocalDate;
import java.util.List;

import programacion.multimedia.aa1.contract.EditMovieContract;
import programacion.multimedia.aa1.domain.Movie;
import programacion.multimedia.aa1.domain.Studio;
import programacion.multimedia.aa1.dto.MovieUpdateRequest;
import programacion.multimedia.aa1.model.EditMovieModel;

public class EditMoviePresenter implements
        EditMovieContract.Presenter,
        EditMovieContract.Model.OnUpdateListener,
        EditMovieContract.Model.OnLoadMovieListener,
        EditMovieContract.Model.OnLoadStudiosListener {

    private EditMovieContract.Model model;
    private EditMovieContract.View view;
    private String currentImageUrl;

    public EditMoviePresenter(EditMovieContract.View view) {
        this.model = new EditMovieModel();
        this.view = view;
    }

    @Override
    public void loadMovie(long movieId) {
        model.loadMovie(movieId, this);
    }

    @Override
    public void loadStudios() {
        model.loadStudios(this);
    }

    @Override
    public void updateMovie(long movieId, String title, String synopsis, String genre,
                            LocalDate releaseDate, int duration, float rating, Studio studio) {
        // Validaciones
        if (title.isEmpty()) {
            view.showValidationError("Title is mandatory");
            return;
        }

        if (duration <= 0) {
            view.showValidationError("Duration must be greater than 0");
            return;
        }

        if (studio == null) {
            view.showValidationError("You must select a studio");
            return;
        }

        MovieUpdateRequest movieRequest = MovieUpdateRequest.builder()
                .title(title)
                .synopsis(synopsis)
                .genre(genre)
                .imageUrl(currentImageUrl)
                .releaseDate(releaseDate)
                .duration(duration)
                .averageRating(rating)
                .studioId(studio.getId())
                .directorId(null)
                .build();

        model.updateMovie(movieId, movieRequest, this);
    }

    // Callbacks de actualización
    @Override
    public void onUpdateSuccess(Movie movie) {
        view.showMessage("Movie updated successfully");
        view.navigateToMovieDetail();
    }

    @Override
    public void onUpdateError(String message) {
        view.showError(message);
    }

    // Callbacks de carga de película
    @Override
    public void onLoadMovieSuccess(Movie movie) {
        this.currentImageUrl = movie.getImageUrl();
        view.showMovie(movie);
    }

    @Override
    public void onLoadMovieError(String message) {
        view.showError(message);
    }

    // Callbacks de studios
    @Override
    public void onLoadStudiosSuccess(List<Studio> studios) {
        view.showStudios(studios);
    }

    @Override
    public void onLoadStudiosError(String message) {
        view.showError(message);
    }
}
