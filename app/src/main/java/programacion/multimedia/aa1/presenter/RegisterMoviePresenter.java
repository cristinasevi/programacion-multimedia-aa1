package programacion.multimedia.aa1.presenter;

import java.time.LocalDate;
import java.util.List;

import programacion.multimedia.aa1.contract.RegisterMovieContract;
import programacion.multimedia.aa1.domain.Movie;
import programacion.multimedia.aa1.domain.Studio;
import programacion.multimedia.aa1.dto.MovieCreateRequest;
import programacion.multimedia.aa1.model.RegisterMovieModel;

public class RegisterMoviePresenter implements
        RegisterMovieContract.Presenter,
        RegisterMovieContract.Model.OnRegisterListener,
        RegisterMovieContract.Model.OnLoadStudiosListener {

    private RegisterMovieContract.Model model;
    private RegisterMovieContract.View view;

    public RegisterMoviePresenter(RegisterMovieContract.View view) {
        this.model = new RegisterMovieModel();
        this.view = view;
    }

    @Override
    public void registerMovie(String title, String synopsis, String genre, LocalDate releaseDate, int duration, float rating, Studio studio) {
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

        MovieCreateRequest movieRequest = MovieCreateRequest.builder()
                .title(title)
                .synopsis(synopsis)
                .genre(genre)
                .releaseDate(releaseDate)
                .duration(duration)
                .averageRating(rating)
                .studioId(studio.getId())
                .build();

        model.registerMovie(movieRequest, this);
    }

    @Override
    public void loadStudios() {
        model.loadStudios(this);
    }

    // Callbacks de registro
    @Override
    public void onRegisterSuccess(Movie movie) {
        view.showMessage("The movie has been successfully registered");
        view.navigateToMovieList();
    }

    @Override
    public void onRegisterError(String message) {
        view.showError(message);
    }

    // Callbacks de studios
    @Override
    public void onLoadSuccess(List<Studio> studios) {
        view.showStudios(studios);
    }

    @Override
    public void onLoadError(String message) {
        view.showError(message);
    }
}
