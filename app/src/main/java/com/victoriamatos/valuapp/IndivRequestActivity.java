package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IndivRequestActivity extends AppCompatActivity {
    private ThreadTaskHandler tth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_request_screen);
        //tth = new ThreadTaskHandler();
        //tth.postThreadTask(ThreadTaskHandler.URL_POST_BROWSE_REQUEST, "id=" + );
        //Log.w("IRA", BrowseActivity.viewReq + "");
    }

    public void bookRequest(View v){
        Log.w("IRA","Inside bookRequest");
        // do the booking, update server
        finish();

    }

}
