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
public class ReviewCreateRequest {
    private String comment;
    private float rating;
    private LocalDate reviewDate;
    private boolean recommended;
    private boolean spoiler;
    private Long userId;
}
