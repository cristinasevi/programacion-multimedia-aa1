package programacion.multimedia.aa1.db;

import static programacion.multimedia.aa1.util.Constants.DATABASE_NAME;

import android.content.Context;

import androidx.room.Room;

public class DatabaseUtil {
    public static MovieDatabase getDb(Context context) {
        return Room.databaseBuilder(context, MovieDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
}
