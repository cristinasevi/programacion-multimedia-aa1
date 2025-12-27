package programacion.multimedia.aa1.model;

import java.util.List;

import programacion.multimedia.aa1.api.MovieApi;
import programacion.multimedia.aa1.api.MovieApiInterface;
import programacion.multimedia.aa1.contract.MovieListContract;
import programacion.multimedia.aa1.domain.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListModel implements MovieListContract.Model {

    @Override
    public void loadMovies(OnLoadListener listener) {
        MovieApiInterface movieApi = MovieApi.buildInstance();
        Call<List<Movie>> getMoviesCall = movieApi.getMovies();
        getMoviesCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.code() == 200) {
                    List<Movie> movies = response.body();
                    listener.onLoadSuccess(movies);
                } else if (response.code() == 500) {
                    listener.onLoadError("Error interno del servidor");
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                listener.onLoadError("No se ha podido conectar con el servidor");
            }
        });
    }
}
