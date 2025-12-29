package programacion.multimedia.aa1.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import programacion.multimedia.aa1.R;
import programacion.multimedia.aa1.adapter.ReviewAdapter;
import programacion.multimedia.aa1.contract.ReviewListContract;
import programacion.multimedia.aa1.domain.Review;
import programacion.multimedia.aa1.presenter.ReviewListPresenter;

public class ReviewListView extends AppCompatActivity implements ReviewListContract.View {

    public static final String MOVIE_ID = "movie_id";

    private ReviewListContract.Presenter presenter;
    private ReviewAdapter reviewAdapter;
    private List<Review> reviewList;
    private long movieId;

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

        presenter = new ReviewListPresenter(this);
        reviewList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.reviews_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewAdapter = new ReviewAdapter(this, reviewList);
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

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
