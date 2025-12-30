package programacion.multimedia.aa1.contract;

import java.time.LocalDate;
import java.util.List;

import programacion.multimedia.aa1.domain.Movie;
import programacion.multimedia.aa1.domain.Studio;
import programacion.multimedia.aa1.dto.MovieUpdateRequest;

public interface EditMovieContract {

    interface Model {
        interface OnUpdateListener {
            void onUpdateSuccess(Movie movie);
            void onUpdateError(String message);
        }

        interface OnLoadMovieListener {
            void onLoadMovieSuccess(Movie movie);
            void onLoadMovieError(String message);
        }

        interface OnLoadStudiosListener {
            void onLoadStudiosSuccess(List<Studio> studios);
            void onLoadStudiosError(String message);
        }

        void updateMovie(long movieId, MovieUpdateRequest movieRequest, OnUpdateListener listener);
        void loadMovie(long movieId, OnLoadMovieListener listener);
        void loadStudios(OnLoadStudiosListener listener);
    }

    interface View {
        void showMovie(Movie movie);
        void showStudios(List<Studio> studios);
        void showMessage(String message);
        void showError(String message);
        void showValidationError(String message);
        void navigateToMovieDetail();
    }

    interface Presenter {
        void loadMovie(long movieId);
        void loadStudios();
        void updateMovie(long movieId, String title, String synopsis, String genre,
                         LocalDate releaseDate, int duration, float rating, Studio studio);
    }
}
