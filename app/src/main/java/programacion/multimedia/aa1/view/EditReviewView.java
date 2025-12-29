package programacion.multimedia.aa1.view;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;

import programacion.multimedia.aa1.R;
import programacion.multimedia.aa1.contract.EditReviewContract;
import programacion.multimedia.aa1.domain.Review;
import programacion.multimedia.aa1.presenter.EditReviewPresenter;
import programacion.multimedia.aa1.util.DateUtil;

public class EditReviewView extends AppCompatActivity implements EditReviewContract.View {

    public static final String REVIEW_ID = "review_id";

    private EditReviewContract.Presenter presenter;
    private long reviewId;

    private EditText commentEditText;
    private EditText ratingEditText;
    private EditText dateEditText;
    private CheckBox recommendedCheckBox;
    private CheckBox spoilerCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_review);

        reviewId = getIntent().getLongExtra(REVIEW_ID, -1);

        if (reviewId == -1) {
            showError("Error: Review no encontrada");
            finish();
            return;
        }

        presenter = new EditReviewPresenter(this);

        initViews();
        presenter.loadReview(reviewId);
    }

    private void initViews() {
        commentEditText = findViewById(R.id.review_comment);
        ratingEditText = findViewById(R.id.review_rating);
        dateEditText = findViewById(R.id.review_date);
        recommendedCheckBox = findViewById(R.id.review_recommended);
        spoilerCheckBox = findViewById(R.id.review_spoiler);
    }

    @Override
    public void showReview(Review review) {
        commentEditText.setText(review.getComment());
        ratingEditText.setText(String.valueOf(review.getRating()));

        if (review.getReviewDate() != null) {
            dateEditText.setText(DateUtil.formatDate(review.getReviewDate()));
        }

        recommendedCheckBox.setChecked(review.isRecommended());
        spoilerCheckBox.setChecked(review.isSpoiler());
    }

    public void updateReview(View view) {
        String comment = commentEditText.getText().toString();
        String ratingStr = ratingEditText.getText().toString();
        LocalDate reviewDate = DateUtil.parseDate(dateEditText.getText().toString());
        boolean recommended = recommendedCheckBox.isChecked();
        boolean spoiler = spoilerCheckBox.isChecked();

        // Validar rating
        float rating = 0.0f;
        if (!ratingStr.isEmpty()) {
            try {
                rating = Float.parseFloat(ratingStr);
            } catch (NumberFormatException e) {
                showValidationError(getString(R.string.rating_must_be_a_valid_number));
                return;
            }
        }

        presenter.updateReview(reviewId, comment, rating, reviewDate, recommended, spoiler);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showValidationError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToReviewList() {
        setResult(RESULT_OK);
        finish();
    }
}
