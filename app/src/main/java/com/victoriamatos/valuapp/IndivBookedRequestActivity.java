package com.victoriamatos.valuapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IndivBookedRequestActivity extends AppCompatActivity {
    private ThreadTaskHandler tth;
    private int bookingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_booked_request_screen);
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
        Log.w("IBRA","output length: " + output.length);
        if(output.length == 12)
            updateView(output);
        else
            finish();
    }

    public void updateView(String[] info){
        Log.w("IBRA", "TEST 1");
        TextView tv1 = findViewById(R.id.owner_email);
        tv1.setText(info[0]);
        Log.w("IBRA", "TEST 2");
        TextView tv2 = findViewById(R.id.start_date);
        tv2.setText(info[1]);
        Log.w("IBRA", "TEST 3");
        TextView tv3 = findViewById(R.id.end_date);
        tv3.setText(info[2]);
        Log.w("IBRA", "TEST 4");
        TextView tv4 = findViewById(R.id.owner_name);
        tv4.setText(info[3] + " " + info[4]);
        Log.w("IBRA", "TEST 5");
        TextView tv5 = findViewById(R.id.owner_zip_code);
        tv5.setText(info[5]);
        Log.w("IBRA", "TEST 6");
        TextView tv6 = findViewById(R.id.pet_name);
        tv6.setText(info[6]);
        Log.w("IBRA", "TEST 7");
        TextView tv7 = findViewById(R.id.pet_type);
        tv7.setText(info[7]);
        Log.w("IBRA", "TEST 8");
        TextView tv8 = findViewById(R.id.pet_breed);
        tv8.setText(info[8]);
        Log.w("IBRA", "TEST 9");
        TextView tv9 = findViewById(R.id.pet_description);
        tv9.setText(info[9]);

        //pet_pic with info[10]
        ImageView image = findViewById(R.id.pet_pic);
        int pet_pic_id = this.getResources().getIdentifier("cat", "drawable", this.getPackageName());
        image.setImageResource(pet_pic_id);
        Log.w("IBRA", "TEST 10");
        TextView tv11 = findViewById(R.id.distance_indiv);
        tv11.setText(info[11]);

    }

    //eventually a rate method
    public void rate(View v){
        //do the rating - go to a rate screen?
        finish();
    }
}
