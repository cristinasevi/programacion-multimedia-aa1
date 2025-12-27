package programacion.multimedia.aa1.presenter;

import java.util.List;

import programacion.multimedia.aa1.contract.MovieListContract;
import programacion.multimedia.aa1.domain.Movie;
import programacion.multimedia.aa1.model.MovieListModel;

public class MovieListPresenter implements MovieListContract.Presenter, MovieListContract.Model.OnLoadListener {

    private MovieListContract.Model model;
    private MovieListContract.View view;

    public MovieListPresenter(MovieListContract.View view) {
        this.model = new MovieListModel();
        this.view = view;
    }

    @Override
    public void loadMovies() {
        model.loadMovies(this);
    }

    @Override
    public void onLoadSuccess(List<Movie> movies) {
        view.showMovies(movies);
        view.showMessage("Las películas se han cargado con éxito");
    }

    @Override
    public void onLoadError(String message) {
        view.showError(message);
    }
}
