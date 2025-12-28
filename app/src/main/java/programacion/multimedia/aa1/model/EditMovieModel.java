package programacion.multimedia.aa1.model;

import android.util.Log;

import java.util.List;

import programacion.multimedia.aa1.api.MovieApi;
import programacion.multimedia.aa1.api.MovieApiInterface;
import programacion.multimedia.aa1.contract.EditMovieContract;
import programacion.multimedia.aa1.domain.Movie;
import programacion.multimedia.aa1.domain.Studio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMovieModel implements EditMovieContract.Model {

    @Override
    public void updateMovie(long movieId, Movie movie, OnUpdateListener listener) {
        MovieApiInterface api = MovieApi.buildInstance();

        Log.d("API_UPDATE", "Actualizando película: " + movie.toString());

        Call<Movie> putMovieCall = api.updateMovie(movieId, movie);
        putMovieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {
                    listener.onUpdateSuccess(response.body());
                } else if (response.code() == 400) {
                    listener.onUpdateError("Errores de validación");
                } else if (response.code() == 404) {
                    listener.onUpdateError("Película no encontrada");
                } else if (response.code() == 500) {
                    listener.onUpdateError("Error interno del servidor");
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                listener.onUpdateError("No se ha podido conectar con el servidor");
            }
        });
    }

    @Override
    public void loadMovie(long movieId, OnLoadMovieListener listener) {
        MovieApiInterface api = MovieApi.buildInstance();

        Call<Movie> getMovieCall = api.getMovie(movieId);
        getMovieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {
                    listener.onLoadMovieSuccess(response.body());
                } else if (response.code() == 404) {
                    listener.onLoadMovieError("Película no encontrada");
                } else if (response.code() == 500) {
                    listener.onLoadMovieError("Error interno del servidor");
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                listener.onLoadMovieError("No se ha podido conectar con el servidor");
            }
        });
    }

    @Override
    public void loadStudios(OnLoadStudiosListener listener) {
        MovieApiInterface api = MovieApi.buildInstance();
        Call<List<Studio>> getStudiosCall = api.getStudios();
        getStudiosCall.enqueue(new Callback<List<Studio>>() {
            @Override
            public void onResponse(Call<List<Studio>> call, Response<List<Studio>> response) {
                if (response.code() == 200) {
                    listener.onLoadStudiosSuccess(response.body());
                } else if (response.code() == 500) {
                    listener.onLoadStudiosError("Error interno del servidor");
                }
            }

            @Override
            public void onFailure(Call<List<Studio>> call, Throwable t) {
                listener.onLoadStudiosError("No se ha podido conectar con el servidor");
            }
        });
    }
}
