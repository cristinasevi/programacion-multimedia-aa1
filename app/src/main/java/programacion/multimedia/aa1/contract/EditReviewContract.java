package programacion.multimedia.aa1.contract;

import java.time.LocalDate;

import programacion.multimedia.aa1.domain.Review;

public interface EditReviewContract {

    interface Model {
        interface OnUpdateListener {
            void onUpdateSuccess(Review review);
            void onUpdateError(String message);
        }

        interface OnLoadReviewListener {
            void onLoadReviewSuccess(Review review);
            void onLoadReviewError(String message);
        }

        void updateReview(long reviewId, Review review, OnUpdateListener listener);
        void loadReview(long reviewId, OnLoadReviewListener listener);
    }

    interface View {
        void showReview(Review review);
        void showMessage(String message);
        void showError(String message);
        void showValidationError(String message);
        void navigateToReviewList();
    }

    interface Presenter {
        void loadReview(long reviewId);
        void updateReview(long reviewId, String comment, float rating,
                          LocalDate reviewDate, boolean recommended, boolean spoiler);
    }
}
