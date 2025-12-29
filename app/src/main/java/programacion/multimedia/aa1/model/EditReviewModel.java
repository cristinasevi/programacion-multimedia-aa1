package programacion.multimedia.aa1.model;

import java.io.IOException;

import programacion.multimedia.aa1.api.MovieApi;
import programacion.multimedia.aa1.api.MovieApiInterface;
import programacion.multimedia.aa1.contract.EditReviewContract;
import programacion.multimedia.aa1.domain.Review;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditReviewModel implements EditReviewContract.Model {

    @Override
    public void updateReview(long reviewId, Review review, OnUpdateListener listener) {
        MovieApiInterface api = MovieApi.buildInstance();

        Call<Review> putReviewCall = api.updateReview(reviewId, review);
        putReviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.code() == 200) {
                    listener.onUpdateSuccess(response.body());
                } else if (response.code() == 400) {
                    try {
                        String errorBody = response.errorBody().string();
                        listener.onUpdateError("Errores de validación: " + errorBody);
                    } catch (IOException e) {
                        listener.onUpdateError("Errores de validación");
                    }
                } else if (response.code() == 404) {
                    listener.onUpdateError("Review no encontrada");
                } else if (response.code() == 500) {
                    listener.onUpdateError("Error interno del servidor");
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                listener.onUpdateError("No se ha podido conectar con el servidor");
            }
        });
    }

    @Override
    public void loadReview(long reviewId, OnLoadReviewListener listener) {
        MovieApiInterface api = MovieApi.buildInstance();

        Call<Review> getReviewCall = api.getReview(reviewId);
        getReviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.code() == 200) {
                    listener.onLoadReviewSuccess(response.body());
                } else if (response.code() == 404) {
                    listener.onLoadReviewError("Review no encontrada");
                } else if (response.code() == 500) {
                    listener.onLoadReviewError("Error interno del servidor");
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                listener.onLoadReviewError("No se ha podido conectar con el servidor");
            }
        });
    }
}
