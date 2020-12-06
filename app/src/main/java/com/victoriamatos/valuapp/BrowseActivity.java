package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
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
        tth.postThreadTask(ThreadTaskHandler.URL_POST_BROWSE_REQUEST, "latitude=" + AccountActivity.user.latitude + "&longitude=" + AccountActivity.user.longitude);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        populateBrowse();

    }
    public void viewRequest(View v){
        Button button = (Button) v;
        int viewReq = (button.getId());
        Log.w("BA", "id:" + viewReq);
        Intent intent = new Intent(this, IndivRequestActivity.class);
        intent.putExtra("id",viewReq);
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

        try {
            for (int i = 0; i < output.length; i++) {
                info = output[i].split("\\|");
                //id (of request), type (of pet), breed (of pet), photo (of pet), distance, [startDate], [endDate]
                individual = inflater.inflate(R.layout.browse_request_individual, browse, false);
                Button b1 = individual.findViewById(R.id.view_request);
                b1.setId(Integer.parseInt(info[0]));
                TextView tv2 = individual.findViewById(R.id.pet_type_small);
                tv2.setText("Pet Type: " + info[1]);
                TextView tv3 = individual.findViewById(R.id.pet_breed_small);
                tv3.setText("Pet Breed: " + info[2]);

                //info[3] will have whatever we have for image
                ImageView image = individual.findViewById(R.id.pet_pic_small);
                int pet_pic_id = this.getResources().getIdentifier("cat", "drawable", this.getPackageName());
                image.setImageResource(pet_pic_id);

                TextView tv5 = individual.findViewById(R.id.distance);
                tv5.setText("Distance: " + info[4]);
                TextView tv6 = individual.findViewById(R.id.date_start);
                tv6.setText(info[5]);
                TextView tv7 = individual.findViewById(R.id.date_end);
                tv7.setText(info[6]);

                browse.addView(individual);

            }
        }catch (NullPointerException npe){
            npe.printStackTrace();
        }
    }

}
