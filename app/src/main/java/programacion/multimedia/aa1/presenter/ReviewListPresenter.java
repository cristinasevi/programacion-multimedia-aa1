package programacion.multimedia.aa1.presenter;

import java.util.List;

import programacion.multimedia.aa1.contract.ReviewListContract;
import programacion.multimedia.aa1.domain.Review;
import programacion.multimedia.aa1.model.ReviewListModel;

public class ReviewListPresenter implements
        ReviewListContract.Presenter,
        ReviewListContract.Model.OnLoadReviewsListener {

    private ReviewListContract.Model model;
    private ReviewListContract.View view;

    public ReviewListPresenter(ReviewListContract.View view) {
        this.model = new ReviewListModel();
        this.view = view;
    }

    @Override
    public void loadReviews(long movieId) {
        model.loadReviews(movieId, this);
    }

    @Override
    public void onLoadSuccess(List<Review> reviews) {
        view.showReviews(reviews);
    }

    @Override
    public void onLoadError(String message) {
        view.showError(message);
    }
}
