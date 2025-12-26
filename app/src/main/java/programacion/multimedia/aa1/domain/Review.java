package programacion.multimedia.aa1.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private long id;
    private String comment;
    private float rating;
    private LocalDate reviewDate;
    private boolean recommended;
    private boolean spoiler;
    private User user;
    private Movie movie;
}
