package programacion.multimedia.aa1.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import programacion.multimedia.aa1.domain.FavoriteMovie;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movies")
    List<FavoriteMovie> findAll();

    @Query("SELECT * FROM favorite_movies WHERE movie_id = :movieId")
    FavoriteMovie findByMovieId(long movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteMovie favoriteMovie);

    @Update
    void update(FavoriteMovie favoriteMovie);

    @Delete
    void delete(FavoriteMovie favoriteMovie);

    @Query("DELETE FROM favorite_movies WHERE movie_id = :movieId")
    void deleteByMovieId(long movieId);
}
