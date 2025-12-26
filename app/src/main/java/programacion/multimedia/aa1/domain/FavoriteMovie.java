package programacion.multimedia.aa1.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "favorite_movies")
public class FavoriteMovie {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    private String synopsis;
    private LocalDate releaseDate;
    private int duration;
    private String genre;
    private float averageRating;
    private String imageUrl;
    private String studioName;
    private String directorName;
}
