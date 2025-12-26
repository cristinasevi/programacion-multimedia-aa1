package programacion.multimedia.aa1.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Studio {
    private long id;
    private String name;
    private String country;
    private int foundationYear;
    private String email;
    private String phone;
    private boolean active;
    private double latitude;
    private double longitude;
}
