package com.victoriamatos.valuapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpImageRequest extends AsyncTask<String, Void, Bitmap> {
    public static final String URL_GET_IMAGE_PREFIX = "http://valu.cs.loyola.edu/uploads/";
    private ImageView img;

    @Override
    protected Bitmap doInBackground(String... strings) {
        try{
            URL url = new URL(URL_GET_IMAGE_PREFIX + strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            Log.w("HIR","Got img");
            return bitmap;
        } catch (Exception e){
            Log.w("TTH", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        img.setImageBitmap(result);
        Log.w("HIR","set img_view");
    }

    public void updateView(ImageView iv){
        img = iv;
    }
}
