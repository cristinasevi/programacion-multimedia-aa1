package programacion.multimedia.aa1.model;

import java.util.List;

import programacion.multimedia.aa1.api.MovieApi;
import programacion.multimedia.aa1.api.MovieApiInterface;
import programacion.multimedia.aa1.contract.RegisterMovieContract;
import programacion.multimedia.aa1.domain.Movie;
import programacion.multimedia.aa1.domain.Studio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterMovieModel implements RegisterMovieContract.Model {

    @Override
    public void registerMovie(Movie movie, OnRegisterListener listener) {
        MovieApiInterface api = MovieApi.buildInstance();
        Call<Movie> postMovieCall = api.registerMovie(movie);
        postMovieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 201 || response.code() == 200) {
                    listener.onRegisterSuccess(response.body());
                } else if (response.code() == 400) {
                    listener.onRegisterError("Errores de validación");
                } else if (response.code() == 500) {
                    listener.onRegisterError("Error interno del servidor");
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                listener.onRegisterError("No se ha podido conectar con el servidor");
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
                    listener.onLoadSuccess(response.body());
                } else if (response.code() == 500) {
                    listener.onLoadError("Error interno del servidor");
                }
            }

            @Override
            public void onFailure(Call<List<Studio>> call, Throwable t) {
                listener.onLoadError("No se ha podido conectar con el servidor");
            }
        });
    }
}
