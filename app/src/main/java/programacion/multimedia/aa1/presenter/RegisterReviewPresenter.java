package programacion.multimedia.aa1.presenter;

import java.time.LocalDate;

import programacion.multimedia.aa1.contract.RegisterReviewContract;
import programacion.multimedia.aa1.domain.Review;
import programacion.multimedia.aa1.dto.ReviewCreateRequest;
import programacion.multimedia.aa1.model.RegisterReviewModel;

public class RegisterReviewPresenter implements
        RegisterReviewContract.Presenter,
        RegisterReviewContract.Model.OnRegisterListener {

    private RegisterReviewContract.Model model;
    private RegisterReviewContract.View view;

    public RegisterReviewPresenter(RegisterReviewContract.View view) {
        this.model = new RegisterReviewModel();
        this.view = view;
    }

    @Override
    public void registerReview(long movieId, String comment, float rating,
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
            reviewDate = LocalDate.now(); // Default a hoy
        }

        ReviewCreateRequest reviewRequest = ReviewCreateRequest.builder()
                .comment(comment)
                .rating(rating)
                .reviewDate(reviewDate)
                .recommended(recommended)
                .spoiler(spoiler)
                .userId(1L)
                .build();

        model.registerReview(movieId, reviewRequest, this);
    }

    @Override
    public void onRegisterSuccess(Review review) {
        view.showMessage("Review registered successfully");
        view.navigateToReviewList();
    }

    @Override
    public void onRegisterError(String message) {
        view.showError(message);
    }
}
