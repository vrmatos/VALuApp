package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BrowseActivity extends AppCompatActivity {
    private ThreadTaskHandler tth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_requests_screen);
        tth = new ThreadTaskHandler();


        //get requests

        tth.postThreadTask(ThreadTaskHandler.URL_POST_BROWSE_REQUEST, "latitude=" + LoginActivity.user.latitude + "&longitude=" + LoginActivity.user.longitude);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        populateBrowse();

        /*
        LinearLayout browse = findViewById(R.id.browse_results2);
        LayoutInflater inflater = getLayoutInflater();
        View individual = inflater.inflate(R.layout.browse_request_individual, browse, false);

        ImageView image = individual.findViewById(R.id.pet_pic_small);
        int pet_pic_id = this.getResources().getIdentifier("cat", "drawable", this.getPackageName());
        image.setImageResource(pet_pic_id);
        TextView tv1 = individual.findViewById(R.id.pet_type_small);
        tv1.setText("Dog");
        TextView tv2 = individual.findViewById(R.id.pet_breed_small);
        tv2.setText("Poodle");
        TextView tv3 = individual.findViewById(R.id.distance);
        tv3.setText("3 miles");
        TextView tv4 = individual.findViewById(R.id.date_start);
        tv4.setText("12/04/2020");
        TextView tv5 = individual.findViewById(R.id.date_end);
        tv5.setText("12/05/2020");
        browse.addView(individual);


        View individual2 = inflater.inflate(R.layout.browse_request_individual, browse, false);

        ImageView image2 = individual.findViewById(R.id.pet_pic_small);
        int pet_pic_id2 = this.getResources().getIdentifier("cat", "drawable", this.getPackageName());
        image2.setImageResource(pet_pic_id);
        TextView tv6 = individual2.findViewById(R.id.pet_type_small);
        tv6.setText("Dog");
        TextView tv7 = individual2.findViewById(R.id.pet_breed_small);
        tv7.setText("Golden");
        TextView tv8 = individual2.findViewById(R.id.distance);
        tv8.setText("8 miles");
        TextView tv9 = individual2.findViewById(R.id.date_start);
        tv9.setText("11/04/2020");
        TextView tv10 = individual2.findViewById(R.id.date_end);
        tv10.setText("11/05/2020");
        browse.addView(individual2);
        */

    }


    public void viewRequest(View v){
        Log.w("BrowseAct","Inside viewRequest");
        Intent intent = new Intent(this, IndivRequestActivity.class);
        startActivity(intent);
    }

    public void searchBy(View v){
        Log.w("BrowseAct","Inside searchBy");
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void populateBrowse(){
        String[] output = tth.getThreadOutput();
        String[] info;
        LinearLayout browse = findViewById(R.id.browse_results2);
        LayoutInflater inflater = getLayoutInflater();
        View individual;

        for(int i=0; i < 2; i++){
            info = output[i].split("\\|");
            //id (of request), type (of pet), breed (of pet), photo (of pet), distance, [startDate], [endDate]
            individual = inflater.inflate(R.layout.browse_request_individual, browse, false);
            TextView tv1 = individual.findViewById(R.id.request_id);
            tv1.setText(info[0]);
            TextView tv2 = individual.findViewById(R.id.pet_type_small);
            tv2.setText(info[1]);
            TextView tv3 = individual.findViewById(R.id.pet_breed_small);
            tv3.setText(info[2]);

            //info[3] will have whatever we have for image

            TextView tv5 = individual.findViewById(R.id.distance);
            tv5.setText(info[4]);
            TextView tv6 = individual.findViewById(R.id.start_date);
            tv6.setText(info[5]);
            TextView tv7 = individual.findViewById(R.id.end_date);
            tv7.setText(info[6]);

        }
    }

}
