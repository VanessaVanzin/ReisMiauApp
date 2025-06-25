package com.example.reismiauapp.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImagemHelper {

    public static Uri salvarImagemTemp(Context context, Bitmap bitmap) {
        try {
            File file = new File(context.getCacheDir(), "foto_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            return FileProvider.getUriForFile(
                    context,
                    context.getPackageName() + ".fileprovider",
                    file
            );
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro ao salvar imagem", Toast.LENGTH_SHORT).show();
            return null;
        }


    }
}
