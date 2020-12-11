package com.victoriamatos.valuapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IndivRequestActivity extends AppCompatActivity {
    private ThreadTaskHandler tth;
    private int bookingId;
    private HttpImageRequest hir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_request_screen);
        Bundle bundle = getIntent().getExtras();
        bookingId = bundle.getInt("id");
        tth = new ThreadTaskHandler();
        tth.postThreadTask(ThreadTaskHandler.URL_POST_VIEW_REQUEST, "id=" + bookingId + "&latitude=" + AccountActivity.user.latitude + "&longitude=" + AccountActivity.user.longitude);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] output = tth.getThreadOutput();
        Log.w("IRA","output length: " + output.length);
        if(output.length == 12)
            updateView(output);
        else
            finish();
    }

    public void bookRequest(View v){
        Log.w("IRA","Inside bookRequest");
        // do the booking, update server
        tth.postThreadTask(ThreadTaskHandler.URL_POST_BOOK_REQUEST, "id=" + bookingId + "&email=" + AccountActivity.user.email);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast toast;
        String[] output = tth.getThreadOutput();
        if(output[0].equals("success")){
            Intent intent = new Intent(this, BrowseActivity.class);
            startActivity(intent);
        }
        else {
            toast = Toast.makeText(getApplicationContext(), "Booking Failure", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void updateView(String[] info){
        TextView tv1 = findViewById(R.id.owner_email);
        tv1.setText(info[0]);
        TextView tv2 = findViewById(R.id.start_date);
        tv2.setText(info[1]);
        TextView tv3 = findViewById(R.id.end_date);
        tv3.setText(info[2]);
        TextView tv4 = findViewById(R.id.owner_name);
        tv4.setText(info[3] + " " + info[4]);
        TextView tv5 = findViewById(R.id.owner_zip_code);
        tv5.setText(info[5]);
        TextView tv6 = findViewById(R.id.pet_name);
        tv6.setText(info[6]);
        TextView tv7 = findViewById(R.id.pet_type);
        tv7.setText(info[7]);
        TextView tv8 = findViewById(R.id.pet_breed);
        tv8.setText(info[8]);
        TextView tv9 = findViewById(R.id.pet_description);
        tv9.setText(info[9]);

        if(info[10].equals("Photo link goes here")){
            Log.w("IBRA", "Default pet pic placed");
            ImageView image = findViewById(R.id.pet_pic);
            int pet_pic_id = this.getResources().getIdentifier("cat", "drawable", this.getPackageName());
            image.setImageResource(pet_pic_id);
        }else{
            ImageView iv = findViewById(R.id.pet_pic);
            hir = new HttpImageRequest();
            hir.updateView(iv);
            hir.execute(info[10]);
        }

        TextView tv11 = findViewById(R.id.distance_indiv);
        tv11.setText(info[11] + " miles");

    }

}
