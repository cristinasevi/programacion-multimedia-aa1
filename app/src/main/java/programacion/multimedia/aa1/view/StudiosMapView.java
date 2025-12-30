package programacion.multimedia.aa1.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;

import java.util.List;

import programacion.multimedia.aa1.R;
import programacion.multimedia.aa1.api.MovieApi;
import programacion.multimedia.aa1.api.MovieApiInterface;
import programacion.multimedia.aa1.domain.Studio;
import programacion.multimedia.aa1.util.MapUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudiosMapView extends AppCompatActivity implements Style.OnStyleLoaded {

    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private long studioId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studios_map);

        studioId = getIntent().getLongExtra("STUDIO_ID", -1);

        mapView = findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, this);

        pointAnnotationManager = MapUtils.buildAnnotationManager(mapView);
    }

    @Override
    public void onStyleLoaded(@NonNull Style style) {
        loadStudios();
    }

    private void loadStudios() {
        MovieApiInterface api = MovieApi.buildInstance();
        Call<List<Studio>> call = api.getStudios();

        call.enqueue(new Callback<List<Studio>>() {
            @Override
            public void onResponse(@NonNull Call<List<Studio>> call, @NonNull Response<List<Studio>> response) {
                if (response.code() == 200 && response.body() != null) {
                    List<Studio> studios = response.body();

                    if (studioId != -1) {
                        Studio targetStudio = null;
                        for (Studio s : studios) {
                            if (s.getId() == studioId) {
                                targetStudio = s;
                                break;
                            }
                        }

                        if (targetStudio != null) {
                            showStudioOnMap(targetStudio);
                        } else {
                            Toast.makeText(StudiosMapView.this, "Studio no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!studios.isEmpty()) {
                            Studio studio = studios.get(0);
                            showStudioOnMap(studio);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Studio>> call, @NonNull Throwable t) {
                Toast.makeText(StudiosMapView.this, "Error loading studios", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showStudioOnMap(Studio studio) {
        if (studio.getLatitude() != 0.0 && studio.getLongitude() != 0.0) {
            Point point = Point.fromLngLat(studio.getLongitude(), studio.getLatitude());

            // Añadir marcador con MapUtils
            MapUtils.addMarker(this, pointAnnotationManager, point, studio.getName());

            // Centrar cámara del mapa
            CameraOptions cameraOptions = new CameraOptions.Builder()
                    .center(point)
                    .zoom(12.0)
                    .build();
            mapView.getMapboxMap().setCamera(cameraOptions);

            Toast.makeText(this, "Studio: " + studio.getName(), Toast.LENGTH_LONG).show();
        }
    }
}
