package programacion.multimedia.aa1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import programacion.multimedia.aa1.R;
import programacion.multimedia.aa1.domain.Movie;
import programacion.multimedia.aa1.util.DateUtil;
import programacion.multimedia.aa1.view.MovieDetailView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.movieTitle.setText(movie.getTitle());
        holder.movieGenre.setText(movie.getGenre());

        if (movie.getReleaseDate() != null) {
            holder.movieReleaseDate.setText(DateUtil.formatDate(movie.getReleaseDate()));
        }

        if (movie.getAverageRating() > 0) {
            holder.movieRating.setText("★ " + String.format("%.1f", movie.getAverageRating()));
        } else {
            holder.movieRating.setText("★ N/A");
        }

        if (movie.getImageUrl() != null && !movie.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(movie.getImageUrl())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_gallery)
                    .into(holder.movieImage);
        } else {
            holder.movieImage.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // Navegar a MovieDetail
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailView.class);
            intent.putExtra(MovieDetailView.movie_id, movie.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {

        private ImageView movieImage;
        private TextView movieTitle;
        private TextView movieGenre;
        private TextView movieReleaseDate;
        private TextView movieRating;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.movie_image);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieGenre = itemView.findViewById(R.id.movie_genre);
            movieReleaseDate = itemView.findViewById(R.id.movie_release_date);
            movieRating = itemView.findViewById(R.id.movie_rating);
        }
    }
}
