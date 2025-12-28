package programacion.multimedia.aa1.contract;

import programacion.multimedia.aa1.domain.Movie;

public interface MovieDetailContract {

    interface Model {
        interface OnLoadMovieListener {
            void onLoadSuccess(Movie movie);
            void onLoadError(String message);
        }

        interface OnDeleteMovieListener {
            void onDeleteSuccess();
            void onDeleteError(String message);
        }

        void loadMovie(long movieId, OnLoadMovieListener listener);
        void deleteMovie(long movieId, OnDeleteMovieListener listener);
    }

    interface View {
        void showMovie(Movie movie);
        void showMessage(String message);
        void showError(String message);
        void navigateToMovieList();
        void showDeleteConfirmationDialog();
    }

    interface Presenter {
        void loadMovie(long movieId);
        void deleteMovie(long movieId);
        void onDeleteConfirmed(long movieId);
    }
}
