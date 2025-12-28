package programacion.multimedia.aa1.model;

import programacion.multimedia.aa1.api.MovieApi;
import programacion.multimedia.aa1.api.MovieApiInterface;
import programacion.multimedia.aa1.contract.MovieDetailContract;
import programacion.multimedia.aa1.domain.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailModel implements MovieDetailContract.Model {

    @Override
    public void loadMovie(long movieId, OnLoadMovieListener listener) {
        MovieApiInterface api = MovieApi.buildInstance();

        Call<Movie> getMovieCall = api.getMovie(movieId);
        getMovieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {
                    listener.onLoadSuccess(response.body());
                } else if (response.code() == 404) {
                    listener.onLoadError("Película no encontrada");
                } else if (response.code() == 500) {
                    listener.onLoadError("Error interno del servidor");
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                listener.onLoadError("No se ha podido conectar con el servidor");
            }
        });
    }

    @Override
    public void deleteMovie(long movieId, OnDeleteMovieListener listener) {
        MovieApiInterface api = MovieApi.buildInstance();
        Call<Void> deleteMovieCall = api.deleteMovie(movieId);
        deleteMovieCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 204 || response.code() == 200) {
                    listener.onDeleteSuccess();
                } else if (response.code() == 404) {
                    listener.onDeleteError("Película no encontrada");
                } else if (response.code() == 500) {
                    listener.onDeleteError("Error interno del servidor");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onDeleteError("No se ha podido conectar con el servidor");
            }
        });
    }
}
