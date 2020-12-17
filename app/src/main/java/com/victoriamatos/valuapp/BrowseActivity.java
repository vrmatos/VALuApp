package com.victoriamatos.valuapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

/**
 * This class represents the user browsing through requests
 */
public class BrowseActivity extends AppCompatActivity {
    /* Attributes that handle sending out and receiving data */
    private ThreadTaskHandler tth;
    private HttpImageRequest hir;

    /* Attributes that handle the search criteria */
    private int searchDistance;
    private String type;
    private String breed;

    /**
     * Initializes BrowseActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("BA", "Inside onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_requests_screen);
        tth = new ThreadTaskHandler(); //searchDistance, type, breed
        //gets the search criteria from the SearchActivity
        Intent intent = getIntent();
        searchDistance = intent.getIntExtra("searchDistance", -1);
        type = intent.getStringExtra("type");
        if(type == null)
            type = "type";
        breed = intent.getStringExtra("breed");
        if(breed == null)
            breed = "breed";
        Log.w("BA",searchDistance + " " + type + " " + breed);
        tth.postThreadTask(ThreadTaskHandler.URL_POST_SEARCH_REQUESTS, "latitude=" + AccountActivity.user.latitude + "&longitude=" + AccountActivity.user.longitude
                + "&searchDistance=" + searchDistance + "&type=" + type + "&breed=" + breed);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        populateBrowse();

    }

    /**
     * Goes to IndivRequestActivity based on the id of the request, onClick
     * @param v, of button pressed
     */
    public void viewRequest(View v){
        Button button = (Button) v;
        int viewReq = (button.getId());
        Log.w("BA", "inside viewRequest, id:" + viewReq);
        Intent intent = new Intent(this, IndivRequestActivity.class);
        intent.putExtra("id",viewReq);
        startActivity(intent);

    }

    /**
     * Goes to SearchActivity to change search criteria for the requests, onClick
     * @param v, of button pressed
     */
    public void searchBy(View v){
        Log.w("BA","Inside searchBy");
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("searchDistance", searchDistance);
        intent.putExtra("type", type);
        intent.putExtra("breed", breed);
        startActivity(intent);
    }

    /**
     * Populates the BrowseActivity with the posted requests on certain search criteria
     */
    public void populateBrowse(){
        Log.w("BA", "Inside populateBrowse");
        String[] output = tth.getThreadOutput();
        String[] info;
        LinearLayout browse = findViewById(R.id.browse_results2);
        LayoutInflater inflater = getLayoutInflater();
        View individual;
        if(output != null && !output[0].equals("noRequests") && !output[0].equals("") ){
            try {
                for (int i = 0; i < output.length; i++) {
                    Log.w("BA", "Inside populateBrowse loop");
                    info = output[i].split("\\|");
                    //id (of request), type (of pet), breed (of pet), photo (of pet), distance, [startDate], [endDate]
                    individual = inflater.inflate(R.layout.browse_request_individual, browse, false);
                    Button b1 = individual.findViewById(R.id.view_request);
                    b1.setId(Integer.parseInt(info[0]));
                    TextView tv2 = individual.findViewById(R.id.pet_type_small);
                    tv2.setText("Pet Type: " + info[1]);
                    TextView tv3 = individual.findViewById(R.id.pet_breed_small);
                    tv3.setText("Pet Breed: " + info[2]);

                    if(info[3].equals("Photo link goes here")){
                        Log.w("BA", "Default pet pic placed");
                        ImageView image = individual.findViewById(R.id.pet_pic_small);
                        int pet_pic_id = this.getResources().getIdentifier("cat", "drawable", this.getPackageName());
                        image.setImageResource(pet_pic_id);
                    }else{
                        ImageView iv = individual.findViewById(R.id.pet_pic_small);
                        hir = new HttpImageRequest();
                        hir.updateView(iv);
                        hir.execute(info[3]);
                    }
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

    /**
     * Goes to AccountActivity, onClick
     * @param v, of button pressed
     */
    public void goHome(View v){
        Log.w("BA", "Inside goHome");
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}