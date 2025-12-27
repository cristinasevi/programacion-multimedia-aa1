package programacion.multimedia.aa1.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import programacion.multimedia.aa1.R;
import programacion.multimedia.aa1.contract.RegisterMovieContract;
import programacion.multimedia.aa1.domain.Studio;
import programacion.multimedia.aa1.presenter.RegisterMoviePresenter;
import programacion.multimedia.aa1.util.DateUtil;

public class RegisterMovieView extends AppCompatActivity implements RegisterMovieContract.View {

    private RegisterMovieContract.Presenter presenter;
    private List<Studio> studioList;
    private ArrayAdapter<Studio> studioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        presenter = new RegisterMoviePresenter(this);

        // Configurar spinner de studios
        Spinner studioSpinner = findViewById(R.id.movie_studio);
        studioList = new ArrayList<>();
        studioAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, studioList);
        studioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studioSpinner.setAdapter(studioAdapter);

        // Cargar studios
        presenter.loadStudios();
    }

    public void registerMovie(View view) {
        String title = ((EditText) findViewById(R.id.movie_title)).getText().toString();
        String synopsis = ((EditText) findViewById(R.id.movie_synopsis)).getText().toString();
        String genre = ((EditText) findViewById(R.id.movie_genre)).getText().toString();
        LocalDate releaseDate = DateUtil.parseDate(((EditText) findViewById(R.id.movie_release_date)).getText().toString());
        String durationStr = ((EditText) findViewById(R.id.movie_duration)).getText().toString();

        Spinner studioSpinner = findViewById(R.id.movie_studio);
        Studio selectedStudio = (Studio) studioSpinner.getSelectedItem();

        // Validar duración
        int duration = 0;
        try {
            duration = Integer.parseInt(durationStr);
        } catch (NumberFormatException e) {
            showValidationError("La duración debe ser un número válido");
            return;
        }

        presenter.registerMovie(title, synopsis, genre, releaseDate, duration, selectedStudio);
    }

    public void selectImage(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryActivityResultLauncher.launch(galleryIntent);
    }

    ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri imageUri = result.getData().getData();
                    ImageView moviePoster = findViewById(R.id.movie_poster);
                    moviePoster.setImageURI(imageUri);
                }
            }
    );

    @Override
    public void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showValidationError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStudios(List<Studio> studios) {
        studioList.clear();
        studioList.addAll(studios);
        studioAdapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToMovieList() {
        finish(); // Vuelve a la lista de películas
    }
}
