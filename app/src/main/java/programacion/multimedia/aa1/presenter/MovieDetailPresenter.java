package programacion.multimedia.aa1.presenter;

import programacion.multimedia.aa1.contract.MovieDetailContract;
import programacion.multimedia.aa1.domain.Movie;
import programacion.multimedia.aa1.model.MovieDetailModel;

public class MovieDetailPresenter implements
        MovieDetailContract.Presenter,
        MovieDetailContract.Model.OnLoadMovieListener,
        MovieDetailContract.Model.OnDeleteMovieListener {

    private MovieDetailContract.Model model;
    private MovieDetailContract.View view;

    public MovieDetailPresenter(MovieDetailContract.View view) {
        this.model = new MovieDetailModel();
        this.view = view;
    }

    @Override
    public void loadMovie(long movieId) {
        model.loadMovie(movieId, this);
    }

    @Override
    public void deleteMovie(long movieId) {
        view.showDeleteConfirmationDialog();
    }

    @Override
    public void onDeleteConfirmed(long movieId) {
        model.deleteMovie(movieId, this);
    }

    // Callbacks de carga
    @Override
    public void onLoadSuccess(Movie movie) {
        view.showMovie(movie);
    }

    @Override
    public void onLoadError(String message) {
        view.showError(message);
    }

    // Callbacks de eliminación
    @Override
    public void onDeleteSuccess() {
        view.showMessage("Película eliminada con éxito");
        view.navigateToMovieList();
    }

    @Override
    public void onDeleteError(String message) {
        view.showError(message);
    }
}
