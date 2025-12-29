package programacion.multimedia.aa1.contract;

import java.util.List;
import programacion.multimedia.aa1.domain.Review;

public interface ReviewListContract {

    interface Model {
        interface OnLoadReviewsListener {
            void onLoadSuccess(List<Review> reviews);
            void onLoadError(String message);
        }

        void loadReviews(long movieId, OnLoadReviewsListener listener);
    }

    interface View {
        void showReviews(List<Review> reviews);
        void showMessage(String message);
        void showError(String message);
    }

    interface Presenter {
        void loadReviews(long movieId);
    }
}
