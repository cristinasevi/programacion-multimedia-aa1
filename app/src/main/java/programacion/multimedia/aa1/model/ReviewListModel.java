package programacion.multimedia.aa1.model;

import java.util.List;

import programacion.multimedia.aa1.api.MovieApi;
import programacion.multimedia.aa1.api.MovieApiInterface;
import programacion.multimedia.aa1.contract.ReviewListContract;
import programacion.multimedia.aa1.domain.Review;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewListModel implements ReviewListContract.Model {

    @Override
    public void loadReviews(long movieId, OnLoadReviewsListener listener) {
        MovieApiInterface api = MovieApi.buildInstance();
        Call<List<Review>> getReviewsCall = api.getMovieReviews(movieId);

        getReviewsCall.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.code() == 200) {
                    listener.onLoadSuccess(response.body());
                } else if (response.code() == 404) {
                    listener.onLoadError("Película no encontrada");
                } else if (response.code() == 500) {
                    listener.onLoadError("Error interno del servidor");
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                listener.onLoadError("No se ha podido conectar con el servidor");
            }
        });
    }
}
