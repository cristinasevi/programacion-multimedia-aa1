package programacion.multimedia.aa1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import programacion.multimedia.aa1.R;
import programacion.multimedia.aa1.domain.FavoriteMovie;
import programacion.multimedia.aa1.util.DateUtil;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    private Context context;
    private List<FavoriteMovie> favoriteList;
    private OnDeleteClickListener deleteListener;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(FavoriteMovie favorite);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(FavoriteMovie favorite);
    }

    public FavoriteAdapter(Context context, List<FavoriteMovie> favoriteList,
                           OnDeleteClickListener deleteListener,
                           OnItemClickListener itemClickListener) {
        this.context = context;
        this.favoriteList = favoriteList;
        this.deleteListener = deleteListener;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item, parent, false);
        return new FavoriteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        FavoriteMovie favorite = favoriteList.get(position);

        holder.favoriteTitle.setText(favorite.getTitle());
        holder.favoriteGenre.setText(favorite.getGenre());

        if (favorite.getReleaseDate() != null) {
            holder.favoriteReleaseDate.setText(DateUtil.formatDate(favorite.getReleaseDate()));
        }

        if (favorite.getAverageRating() > 0) {
            holder.favoriteRating.setText("★ " + String.format("%.1f", favorite.getAverageRating()));
        } else {
            holder.favoriteRating.setText("★ N/A");
        }

        if (favorite.getImageUrl() != null && !favorite.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(favorite.getImageUrl())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_gallery)
                    .into(holder.favoriteImage);
        } else {
            holder.favoriteImage.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // Click en el item completo para abrir detalle
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(favorite);
            }
        });

        // Click en el botón delete
        holder.favoriteDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteClick(favorite);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder {

        private ImageView favoriteImage;
        private TextView favoriteTitle;
        private TextView favoriteGenre;
        private TextView favoriteReleaseDate;
        private TextView favoriteRating;
        private Button favoriteDelete;

        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);

            favoriteImage = itemView.findViewById(R.id.favorite_image);
            favoriteTitle = itemView.findViewById(R.id.favorite_title);
            favoriteGenre = itemView.findViewById(R.id.favorite_genre);
            favoriteReleaseDate = itemView.findViewById(R.id.favorite_release_date);
            favoriteRating = itemView.findViewById(R.id.favorite_rating);
            favoriteDelete = itemView.findViewById(R.id.favorite_delete);
        }
    }
}
