package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_criteria);
    }

    public void updateSearch(View v){
        Log.w("SA","Inside updateSearch");
        Intent intent = new Intent(this, BrowseActivity.class);
        startActivity(intent);
    }
}
