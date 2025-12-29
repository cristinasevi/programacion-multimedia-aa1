package programacion.multimedia.aa1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import programacion.multimedia.aa1.R;
import programacion.multimedia.aa1.domain.Review;
import programacion.multimedia.aa1.util.DateUtil;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private Context context;
    private List<Review> reviewList;

    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ReviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        Review review = reviewList.get(position);

        if (review.getUser() != null) {
            holder.reviewUser.setText(review.getUser().getUsername());
        } else {
            holder.reviewUser.setText("Anonymous");
        }

        holder.reviewRating.setText("★ " + String.format("%.1f", review.getRating()));
        holder.reviewComment.setText(review.getComment());

        if (review.getReviewDate() != null) {
            holder.reviewDate.setText(DateUtil.formatDate(review.getReviewDate()));
        }

        holder.reviewRecommended.setText(review.isRecommended() ? context.getString(R.string.tick_recommended) : context.getString(R.string.not_recommended));
        holder.reviewSpoiler.setVisibility(review.isSpoiler() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        private TextView reviewUser;
        private TextView reviewRating;
        private TextView reviewComment;
        private TextView reviewDate;
        private TextView reviewRecommended;
        private TextView reviewSpoiler;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);

            reviewUser = itemView.findViewById(R.id.review_user);
            reviewRating = itemView.findViewById(R.id.review_rating);
            reviewComment = itemView.findViewById(R.id.review_comment);
            reviewDate = itemView.findViewById(R.id.review_date);
            reviewRecommended = itemView.findViewById(R.id.review_recommended);
            reviewSpoiler = itemView.findViewById(R.id.review_spoiler);
        }
    }
}
