package programacion.multimedia.aa1.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import programacion.multimedia.aa1.R;
import programacion.multimedia.aa1.adapter.FavoriteAdapter;
import programacion.multimedia.aa1.db.FavoriteMovieDao;
import programacion.multimedia.aa1.db.DatabaseUtil;
import programacion.multimedia.aa1.domain.FavoriteMovie;
import programacion.multimedia.aa1.util.DialogUtil;

public class FavoritesView extends AppCompatActivity {

    private FavoriteAdapter favoriteAdapter;
    private List<FavoriteMovie> favoriteList;
    private FavoriteMovieDao favoriteMovieDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoriteMovieDao = DatabaseUtil.getDb(this).favoriteMovieDao();
        favoriteList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.favorites_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        favoriteAdapter = new FavoriteAdapter(
                this,
                favoriteList,
                this::showDeleteDialog,
                this::openMovieDetail
        );
        recyclerView.setAdapter(favoriteAdapter);

        loadFavorites();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        favoriteList.clear();
        favoriteList.addAll(favoriteMovieDao.findAll());
        favoriteAdapter.notifyDataSetChanged();

        if (favoriteList.isEmpty()) {
            Toast.makeText(this, getString(R.string.no_favorites_yet), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteDialog(FavoriteMovie favorite) {
        AlertDialog.Builder builder = DialogUtil.alertDialogBuilder(
                this,
                getString(R.string.remove_from_favorites)
        );

        builder.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
            favoriteMovieDao.delete(favorite);
            loadFavorites();
            Toast.makeText(this, R.string.removed_from_favorites, Toast.LENGTH_SHORT).show();
        });

        builder.show();
    }

    private void openMovieDetail(FavoriteMovie favorite) {
        Intent intent = new Intent(this, MovieDetailView.class);
        intent.putExtra(MovieDetailView.movie_id, favorite.getMovieId());
        startActivity(intent);
    }
}
