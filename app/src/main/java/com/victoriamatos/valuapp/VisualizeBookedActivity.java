package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VisualizeBookedActivity extends AppCompatActivity {
    private ThreadTaskHandler tth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualize_requests_screen);
        tth = new ThreadTaskHandler();
        tth.postThreadTask(ThreadTaskHandler.URL_POST_BOOKED_REQUESTS, "email=" + AccountActivity.user.email + "&latitude=" + AccountActivity.user.latitude + "&longitude=" + AccountActivity.user.longitude);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        populateViewBooked();
    }

    public void viewRequest(View v){
        Log.w("VBA","Inside viewRequest");
        Button button = (Button) v;
        int viewReq = (button.getId());
        Intent intent = new Intent(this, IndivBookedRequestActivity.class);
        intent.putExtra("id",viewReq);
        startActivity(intent);
    }

    public void populateViewBooked(){
        String[] output = tth.getThreadOutput();
        String[] info;
        LinearLayout browse = findViewById(R.id.view_results);
        LayoutInflater inflater = getLayoutInflater();
        View individual;

        if(output[0] != null && !output[0].equals("noRequests")){
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
                    tv5.setText("Distance: " + info[4] + " miles");
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

}
