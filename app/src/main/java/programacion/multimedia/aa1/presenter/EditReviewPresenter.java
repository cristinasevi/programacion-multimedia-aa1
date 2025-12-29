package programacion.multimedia.aa1.presenter;

import java.time.LocalDate;

import programacion.multimedia.aa1.contract.EditReviewContract;
import programacion.multimedia.aa1.domain.Review;
import programacion.multimedia.aa1.model.EditReviewModel;

public class EditReviewPresenter implements
        EditReviewContract.Presenter,
        EditReviewContract.Model.OnUpdateListener,
        EditReviewContract.Model.OnLoadReviewListener {

    private EditReviewContract.Model model;
    private EditReviewContract.View view;

    public EditReviewPresenter(EditReviewContract.View view) {
        this.model = new EditReviewModel();
        this.view = view;
    }

    @Override
    public void loadReview(long reviewId) {
        model.loadReview(reviewId, this);
    }

    @Override
    public void updateReview(long reviewId, String comment, float rating,
                             LocalDate reviewDate, boolean recommended, boolean spoiler) {
        // Validaciones
        if (comment == null || comment.trim().isEmpty()) {
            view.showValidationError("Comment is required");
            return;
        }

        if (rating < 1.0f || rating > 10.0f) {
            view.showValidationError("Rating must be between 1 and 10");
            return;
        }

        if (reviewDate == null) {
            reviewDate = LocalDate.now();
        }

        Review review = Review.builder()
                .id(reviewId)
                .comment(comment)
                .rating(rating)
                .reviewDate(reviewDate)
                .recommended(recommended)
                .spoiler(spoiler)
                .build();

        model.updateReview(reviewId, review, this);
    }

    @Override
    public void onUpdateSuccess(Review review) {
        view.showMessage("Review updated successfully");
        view.navigateToReviewList();
    }

    @Override
    public void onUpdateError(String message) {
        view.showError(message);
    }

    @Override
    public void onLoadReviewSuccess(Review review) {
        view.showReview(review);
    }

    @Override
    public void onLoadReviewError(String message) {
        view.showError(message);
    }
}
