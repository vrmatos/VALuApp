package com.victoriamatos.valuapp;

import android.util.Log;

public class ThreadTaskHandler {
    public static final String URL_POST_LOGIN = "http://valu.cs.loyola.edu/appLogin.php";
    public static final String URL_POST_REGISTER = "http://valu.cs.loyola.edu/appSignup.php";
    public static final String URL_POST_BROWSE_REQUEST = "http://valu.cs.loyola.edu/browseRequests.php";
    public static final String URL_POST_VIEW_REQUEST = "http://valu.cs.loyola.edu/viewRequest.php";
    public static final String URL_POST_UPDATE_ACCT = "http://valu.cs.loyola.edu/updateAccount.php";
    public static final String URL_POST_UPDATE_ACCT2 = "http://valu.cs.loyola.edu/updateAccount2.php";
    public static final String URL_POST_DELETE = "http://valu.cs.loyola.edu/deleteAccount.php";
    public static final String URL_POST_BOOK_REQUEST = "http://valu.cs.loyola.edu/bookRequest.php";
    public static final String URL_POST_BOOKED_REQUESTS = "http://valu.cs.loyola.edu/bookedRequests.php";
    public static final String URL_POST_SEARCH_REQUESTS = "http://valu.cs.loyola.edu/searchRequests.php";
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
        threadOutput = processOutput(str);
    }

    public String[] getThreadOutput(){
        return threadOutput;
    }

    public String[] processOutput(String str){
        Log.w("TTH", str);
        String[] split;
        if(str == null)
            return null;
        if(str.contains("~")) {
            split = str.split("~");
        }else if(str.contains("|"))
            split = str.split("\\|");
        else{
            split = new String[1];
            split[0] = str;
        }
        return split;
    }


}
