package programacion.multimedia.aa1.util;

import android.app.AlertDialog;
import android.content.Context;

import programacion.multimedia.aa1.R;

public class DialogUtil {

    public static AlertDialog.Builder alertDialogBuilder(Context context, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder
                .setMessage(message)
                .setNegativeButton(R.string.no, ((dialog, i) -> {
                    dialog.dismiss();
                }));
        return builder;
    }
}
