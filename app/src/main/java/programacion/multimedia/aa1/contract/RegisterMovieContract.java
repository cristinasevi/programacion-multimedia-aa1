package programacion.multimedia.aa1.contract;

import java.time.LocalDate;
import java.util.List;

import programacion.multimedia.aa1.domain.Movie;
import programacion.multimedia.aa1.domain.Studio;
import programacion.multimedia.aa1.dto.MovieCreateRequest;

public interface RegisterMovieContract {

    interface Model {
        interface OnRegisterListener {
            void onRegisterSuccess(Movie movie);
            void onRegisterError(String message);
        }

        interface OnLoadStudiosListener {
            void onLoadSuccess(List<Studio> studios);
            void onLoadError(String message);
        }

        void registerMovie(MovieCreateRequest movieRequest, OnRegisterListener listener);
        void loadStudios(OnLoadStudiosListener listener);
    }

    interface View {
        void showMessage(String message);
        void showError(String message);
        void showValidationError(String message);
        void showStudios(List<Studio> studios);
        void navigateToMovieList();
    }

    interface Presenter {
        void registerMovie(String title, String synopsis, String genre, LocalDate releaseDate,
                           int duration, float rating, Studio studio);
        void loadStudios();
    }
}
