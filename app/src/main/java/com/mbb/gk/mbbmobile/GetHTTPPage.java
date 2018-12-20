package com.mbb.gk.mbbmobile;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetHTTPPage extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];

        /*
        // OKHTTP ile
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            return result;

        }catch (IOException e) {
            e.printStackTrace();
        }*/




        return null;
    }

    @Override
    protected void onPostExecute(String s) {

    }


}
