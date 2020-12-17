package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the user updating the search criteria for browse request
 */
public class SearchActivity extends AppCompatActivity {
    private int searchDistance;
    private String type;
    private String breed;

    /**
     * Initializes SearchActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("SA", "Inside onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_criteria);
        Bundle bundle = getIntent().getExtras();
        searchDistance = bundle.getInt("searchDistance", -1);
        type = bundle.getString("type","type");
        breed = bundle.getString("breed","breed");
        Log.w("SA",searchDistance + " " + type + " " + breed);

    }

    /**
     * Updates the search criteria with provided values
     * @param v, of button pressed
     */
    public void updateSearch(View v){
        Log.w("SA","Inside updateSearch");
        EditText ed1 = findViewById(R.id.search_distance);
        String ed1_str = ed1.getText().toString();
        if(ed1_str.equals(""))
            searchDistance = -1;
        else
            searchDistance = Integer.parseInt(ed1_str);

        EditText ed2 = findViewById(R.id.search_pet_type);
        String ed2_str = ed2.getText().toString();
        if(ed2_str.equals(""))
            type = "type";
        else
            type = ed2_str;

        EditText ed3 = findViewById(R.id.search_pet_breed);
        String ed3_str = ed3.getText().toString();
        if(ed3_str.equals(""))
            breed = "breed";
        else
            breed = ed3_str;

        Intent intent = new Intent(this, BrowseActivity.class);
        intent.putExtra("searchDistance", searchDistance);
        intent.putExtra("type", type);
        intent.putExtra("breed", breed);
        startActivity(intent);
    }

}
