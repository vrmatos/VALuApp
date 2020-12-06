package com.victoriamatos.valuapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class ThreadTaskUrlPost extends Thread {
    private String postUrl;
    private String toWrite;
    private ThreadTaskHandler tth;

    public ThreadTaskUrlPost(ThreadTaskHandler handler, String newUrl, String newWrite){
        tth = handler;
        postUrl = newUrl;
        toWrite = newWrite;
    }

    public void run(){
        //update View
        Log.w("TTUP", "Inside run");

        try {
            //create a URL (a class)
            URL url = new URL(postUrl);
            //create a URLConnection
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            //get output stream
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            osw.write(toWrite);
            osw.flush();

            //create an input stream for the URL
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            //read from that input stream
            String line = "";
            String s = "";
            while ( (line = br.readLine()) != null)
                s += line;
            //activity.updateView(s);
            Log.w("TTUP", "s= " + s);
            tth.setThreadOutput(s);
        } catch (Exception e){
            Log.w("TTUP", "exception: " + e.getMessage());
        }
    }

}