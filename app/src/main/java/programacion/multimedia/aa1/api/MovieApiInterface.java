package programacion.multimedia.aa1.api;

import java.util.List;

import programacion.multimedia.aa1.domain.Movie;
import programacion.multimedia.aa1.domain.Review;
import programacion.multimedia.aa1.domain.Studio;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiInterface {

    // Movies
    @GET("movies")
    Call<List<Movie>> getMovies();

    @GET("movies")
    Call<List<Movie>> getMoviesByGenre(@Query("genre") String genre);

    @GET("movies/{id}")
    Call<Movie> getMovie(@Path("id") long id);

    @POST("movies")
    Call<Movie> registerMovie(@Body Movie movie);

    @PUT("movies/{id}")
    Call<Movie> updateMovie(@Path("id") long id, @Body Movie movie);

    @DELETE("movies/{id}")
    Call<Void> deleteMovie(@Path("id") long id);

    // Reviews
    @GET("reviews")
    Call<List<Review>> getReviews();

    @GET("movies/{movieId}/reviews")
    Call<List<Review>> getMovieReviews(@Path("movieId") long movieId);

    @GET("reviews/{id}")
    Call<Review> getReview(@Path("id") long id);

    @POST("movies/{movieId}/reviews")
    Call<Review> registerReview(@Path("movieId") long movieId, @Body Review review);

    @PUT("reviews/{id}")
    Call<Review> updateReview(@Path("id") long id, @Body Review review);

    @DELETE("reviews/{id}")
    Call<Void> deleteReview(@Path("id") long id);

    // Studios
    @GET("studios")
    Call<List<Studio>> getStudios();

    @GET("studios/{id}")
    Call<Studio> getStudio(@Path("id") long id);
}
