package programacion.multimedia.aa1.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieCreateRequest {
    private String title;
    private String synopsis;
    private LocalDate releaseDate;
    private int duration;
    private String genre;
    private String imageUrl;
    private float averageRating;
    private Long studioId;
    private Long directorId;
}
