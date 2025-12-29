package programacion.multimedia.aa1.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "favorite_movies")
public class FavoriteMovie {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "synopsis")
    private String synopsis;

    @ColumnInfo(name = "release_date")
    private LocalDate releaseDate;

    @ColumnInfo(name = "duration")
    private int duration;

    @ColumnInfo(name = "genre")
    private String genre;

    @ColumnInfo(name = "average_rating")
    private float averageRating;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "studio_name")
    private String studioName;

    @ColumnInfo(name = "director_name")
    private String directorName;

    // Constructor vacío (requerido por Room)
    public FavoriteMovie() {
    }
    
    @Ignore
    public FavoriteMovie(String title, String synopsis, LocalDate releaseDate, int duration,
                         String genre, float averageRating, String imageUrl,
                         String studioName, String directorName) {
        this.title = title;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genre = genre;
        this.averageRating = averageRating;
        this.imageUrl = imageUrl;
        this.studioName = studioName;
        this.directorName = directorName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }
}
