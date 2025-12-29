package programacion.multimedia.aa1.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import programacion.multimedia.aa1.R;
import programacion.multimedia.aa1.adapter.ReviewAdapter;
import programacion.multimedia.aa1.api.MovieApi;
import programacion.multimedia.aa1.api.MovieApiInterface;
import programacion.multimedia.aa1.contract.ReviewListContract;
import programacion.multimedia.aa1.domain.Review;
import programacion.multimedia.aa1.presenter.ReviewListPresenter;
import programacion.multimedia.aa1.util.DialogUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewListView extends AppCompatActivity implements ReviewListContract.View {

    public static final String MOVIE_ID = "movie_id";

    private ReviewListContract.Presenter presenter;
    private ReviewAdapter reviewAdapter;
    private List<Review> reviewList;
    private long movieId;

    private ActivityResultLauncher<Intent> editReviewLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        movieId = getIntent().getLongExtra(MOVIE_ID, -1);

        if (movieId == -1) {
            showError("Error: Película no encontrada");
            finish();
            return;
        }

        editReviewLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        presenter.loadReviews(movieId);
                    }
                }
        );

        presenter = new ReviewListPresenter(this);
        reviewList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.reviews_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewAdapter = new ReviewAdapter(this, reviewList, new ReviewAdapter.OnReviewActionListener() {
            @Override
            public void onEditClick(Review review) {
                editReview(review);
            }

            @Override
            public void onDeleteClick(Review review) {
                showDeleteDialog(review);
            }
        });
        recyclerView.setAdapter(reviewAdapter);

        FloatingActionButton fab = findViewById(R.id.fab_add_review);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterReviewView.class);
            intent.putExtra(RegisterReviewView.MOVIE_ID, movieId);
            startActivity(intent);
        });

        presenter.loadReviews(movieId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadReviews(movieId);
    }

    @Override
    public void showReviews(List<Review> reviews) {
        reviewList.clear();
        reviewList.addAll(reviews);
        reviewAdapter.notifyDataSetChanged();

        if (reviews.isEmpty()) {
            showMessage(getString(R.string.no_reviews_yet));
        }
    }

    private void editReview(Review review) {
        Intent intent = new Intent(this, EditReviewView.class);
        intent.putExtra(EditReviewView.REVIEW_ID, review.getId());
        editReviewLauncher.launch(intent);
    }

    private void showDeleteDialog(Review review) {
        AlertDialog.Builder builder = DialogUtil.alertDialogBuilder(
                this,
                getString(R.string.confirm_delete_review)
        );

        builder.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
            deleteReview(review.getId());
        });

        builder.show();
    }

    private void deleteReview(long reviewId) {
        MovieApiInterface api = MovieApi.buildInstance();
        Call<Void> deleteCall = api.deleteReview(reviewId);

        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 204 || response.code() == 200) {
                    showMessage(getString(R.string.review_deleted_successfully));
                    presenter.loadReviews(movieId);
                } else if (response.code() == 404) {
                    showError(getString(R.string.review_not_found));
                } else {
                    showError(getString(R.string.error_deleting_review));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showError("Could not connect to server");
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
