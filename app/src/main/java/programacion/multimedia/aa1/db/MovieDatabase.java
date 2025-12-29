package programacion.multimedia.aa1.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import programacion.multimedia.aa1.domain.FavoriteMovie;
import programacion.multimedia.aa1.util.Converters;

@Database(entities = {FavoriteMovie.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MovieDatabase extends RoomDatabase {
    public abstract FavoriteMovieDao favoriteMovieDao();
}
