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
import programacion.multimedia.aa1.contract.RegisterReviewContract;
import programacion.multimedia.aa1.presenter.RegisterReviewPresenter;
import programacion.multimedia.aa1.util.DateUtil;

public class RegisterReviewView extends AppCompatActivity implements RegisterReviewContract.View {

    public static final String MOVIE_ID = "movie_id";

    private RegisterReviewContract.Presenter presenter;
    private long movieId;

    private EditText commentEditText;
    private EditText ratingEditText;
    private EditText dateEditText;
    private CheckBox recommendedCheckBox;
    private CheckBox spoilerCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_review);

        movieId = getIntent().getLongExtra(MOVIE_ID, -1);

        if (movieId == -1) {
            showError("Error: Película no encontrada");
            finish();
            return;
        }

        presenter = new RegisterReviewPresenter(this);

        initViews();

        // Pre-rellenar fecha actual
        dateEditText.setText(DateUtil.formatDate(LocalDate.now()));
    }

    private void initViews() {
        commentEditText = findViewById(R.id.review_comment);
        ratingEditText = findViewById(R.id.review_rating);
        dateEditText = findViewById(R.id.review_date);
        recommendedCheckBox = findViewById(R.id.review_recommended);
        spoilerCheckBox = findViewById(R.id.review_spoiler);
    }

    public void submitReview(View view) {
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

        presenter.registerReview(movieId, comment, rating, reviewDate, recommended, spoiler);
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
        finish(); // Vuelve a ReviewListView
    }
}
