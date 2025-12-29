package programacion.multimedia.aa1.contract;

import java.time.LocalDate;
import programacion.multimedia.aa1.domain.Review;
import programacion.multimedia.aa1.dto.ReviewCreateRequest;

public interface RegisterReviewContract {

    interface Model {
        interface OnRegisterListener {
            void onRegisterSuccess(Review review);
            void onRegisterError(String message);
        }

        void registerReview(long movieId, ReviewCreateRequest reviewRequest, OnRegisterListener listener);
    }

    interface View {
        void showMessage(String message);
        void showError(String message);
        void showValidationError(String message);
        void navigateToReviewList();
    }

    interface Presenter {
        void registerReview(long movieId, String comment, float rating,
                            LocalDate reviewDate, boolean recommended, boolean spoiler);
    }
}
