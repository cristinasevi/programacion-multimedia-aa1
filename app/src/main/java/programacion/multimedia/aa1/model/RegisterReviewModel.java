package programacion.multimedia.aa1.model;

import android.util.Log;

import programacion.multimedia.aa1.api.MovieApi;
import programacion.multimedia.aa1.api.MovieApiInterface;
import programacion.multimedia.aa1.contract.RegisterReviewContract;
import programacion.multimedia.aa1.domain.Review;
import programacion.multimedia.aa1.dto.ReviewCreateRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterReviewModel implements RegisterReviewContract.Model {

    @Override
    public void registerReview(long movieId, ReviewCreateRequest reviewRequest, OnRegisterListener listener) {
        MovieApiInterface api = MovieApi.buildInstance();

        Log.d("API_REGISTER_REVIEW", "Enviando reseña para la película " + movieId + ": " + reviewRequest.toString());

        Call<Review> postReviewCall = api.registerReview(movieId, reviewRequest);
        postReviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.code() == 201 || response.code() == 200) {
                    listener.onRegisterSuccess(response.body());
                } else if (response.code() == 400) {
                    listener.onRegisterError("Errores de validación");
                } else if (response.code() == 404) {
                    listener.onRegisterError("Película o usuario no encontrado");
                } else if (response.code() == 500) {
                    listener.onRegisterError("Error interno del servidor");
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                listener.onRegisterError("No se ha podido conectar con el servidor");
            }
        });
    }
}
