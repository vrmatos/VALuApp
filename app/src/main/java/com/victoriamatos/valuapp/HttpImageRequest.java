package com.victoriamatos.valuapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class represents getting images from the server through a url
 */
public class HttpImageRequest extends AsyncTask<String, Void, Bitmap> {
    public static final String URL_GET_IMAGE_PREFIX = "http://valu.cs.loyola.edu/uploads/";
    private ImageView img;

    /**
     * Accesses the image and turns it into a bitmap
     * @param strings, the image name
     * @return Bitmap representing the image
     */
    @Override
    protected Bitmap doInBackground(String... strings) {
        Log.w("HIR", "Inside doInBackground");
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

    /**
     * After doInBackground, sets a specific ImageView as itself
     * @param result, the Bitmap the ImageView will be set to
     */
    @Override
    protected void onPostExecute(Bitmap result) {
        Log.w("HIR","inside onPostExecute, set img_view");
        img.setImageBitmap(result);
    }

    /**
     * Changes which ImageView the class updates
     * @param iv, the ImageView changing to
     */
    public void updateView(ImageView iv){
        Log.w("HIR", "Inside updateView");
        img = iv;
    }
}
