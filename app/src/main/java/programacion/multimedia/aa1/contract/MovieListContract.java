package programacion.multimedia.aa1.contract;

import java.util.List;

import programacion.multimedia.aa1.domain.Movie;

public interface MovieListContract {

    interface Model {
        interface OnLoadListener {
            void onLoadSuccess(List<Movie> movies);
            void onLoadError(String message);
        }

        void loadMovies(OnLoadListener listener);
    }

    interface View {
        void showMovies(List<Movie> movies);
        void showMessage(String message);
        void showError(String message);
    }

    interface Presenter {
        void loadMovies();
    }
}
