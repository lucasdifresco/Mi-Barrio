package com.example.mibarrio.soporte;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.widget.Toast;

import com.example.mibarrio.MainActivity;
import com.example.mibarrio.actividades.inicio.Inicio_Home;
import com.example.mibarrio.actividades.reclamos.Reclamos_Nuevo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Helper {
    public static void Toast(String mensaje) { Toast.makeText(MainActivity.getContext(), mensaje,Toast.LENGTH_LONG).show(); }
    public static Bitmap GetBitmap(){ return Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888); }
    public static Boolean isWifiOn() {
        ConnectivityManager connManager = (ConnectivityManager) MainActivity.getContext().getSystemService(Reclamos_Nuevo.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) { return true; } else { return false; }
    }
    public static String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = MainActivity.getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
    public static byte[] ReadBytes(Uri uri, ContentResolver resolver) throws IOException {
        // this dynamically extends to take the bytes you read
        InputStream inputStream = resolver.openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the
        // byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }
}
