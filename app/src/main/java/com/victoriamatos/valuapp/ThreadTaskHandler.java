package com.victoriamatos.valuapp;

import android.util.Log;

public class ThreadTaskHandler {
    public static final String URL_POST_LOGIN = "";
    public static final String URL_POST_REGISTER = "http://valu.cs.loyola.edu/insertSitter.php";
    public static final String URL_POST_BROWSE_REQUEST = "http://valu.cs.loyola.edu/browseRequests.php";
    public static final String URL_GET_SELECT_PET = "http://valu.cs.loyola.edu/selectPet.php";

    private String[] threadOutput;

    public void postThreadTask(String url, String toWrite){
        ThreadTaskUrlPost task = new ThreadTaskUrlPost(this, url, toWrite);
        task.start();
    }

    public void getThreadTask(String url){
        ThreadTaskUrl task = new ThreadTaskUrl(this, url);
        task.start();
    }

    public void setThreadOutput(String str){
        Log.w("TTH", str);
        threadOutput = processOutput(str);
    }

    public String[] getThreadOutput(){
        return threadOutput;
    }

    public String[] processOutput(String str){
        String[] split;
        if(str == null)
            return null;
        if(str.contains("~"))
            split = str.split("~");
        else if(str.contains("|"))
            split = str.split("\\|");
        else{
            split = new String[1];
            split[0] = str;
        }
        return split;
    }


}
