package com.mbb.gk.mbbmobile.HaberPackage;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.mbb.gk.mbbmobile.EndlessRecyclerViewScrollListener;
import com.mbb.gk.mbbmobile.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class HaberActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private HaberAdapter adapter;
    private ArrayList<Haber> haberList;
    private ArrayList<Haber> dataList;
    private Elements dataElements;
    private EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager linearLayoutManager;

    private int oneFetchDataSize = 10;
    int lastPos = 0;

    String url = "https://www.mersin.bel.tr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haber);

        getSupportActionBar().setTitle(R.string.haber);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.haber_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_haber);
        haberList = new ArrayList<>();
        dataList = new ArrayList<>();
        dataElements = new Elements();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);

        new FetchHaber().execute();

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                new LoadNextData().execute();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        adapter = new HaberAdapter(this, haberList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private class FetchHaber extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String haberURL = url + "/haber";
                Document doc = Jsoup.connect(haberURL).get();
                //Document doc = Jsoup.parse(new URL(haberURL).openStream(), "ISO-8859-9", haberURL);
                Elements res = doc.select("div[class=detail]");
                dataElements = res.select("a");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            System.out.println("siiizzeeeeeeee -----------: " + dataElements.size());
            progressBar.setVisibility(View.INVISIBLE);
            new LoadNextData().execute();
        }
    }

    private class LoadNextData extends AsyncTask<Void, Integer, Void> {

        boolean isFinished = false;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            //haberList.clear();
            //adapter.notifyDataSetChanged();
            scrollListener.resetState();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // all data has been shown
            if(dataElements.size() <= lastPos){
                isFinished = true;
                return null;
            }
            // the rest of the data will be shown
            if(dataElements.size() - lastPos < oneFetchDataSize){
                oneFetchDataSize = dataElements.size() - lastPos;
            }

            dataList = new ArrayList<>();

            for (int i = lastPos; i < (lastPos + oneFetchDataSize); i++) {
                try {
                    Element e = dataElements.get(i);

                    Haber haber = new Haber();
                    String link = url + e.select("a").attr("href");
                    String imageURL = url + e.select("img").attr("src");
                    String[] spl = e.text().split(" ");
                    String date = "";
                    String hText = "";
                    for (int j = 0; j < spl.length; j++) {
                        if (j < 4) {
                            date += spl[j] + " ";
                        } else {
                            hText += spl[j] + " ";
                        }
                    }
                    hText = hText.replace((char) 145, (char) 39);
                    hText = hText.replace((char) 146, (char) 39);
                    hText = hText.replace((char) 147, (char) 34);
                    hText = hText.replace((char) 148, (char) 34);

                    // GET IMAGE
                    InputStream input = new URL(imageURL).openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    haber.setHaberURL(link);
                    haber.setImageURL(imageURL);
                    haber.setImage(bitmap);
                    haber.setTarih(date);
                    haber.setText(hText);
                    dataList.add(haber);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            lastPos += oneFetchDataSize;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.INVISIBLE);
            //haberList.addAll(dataList);
            //adapter.notifyDataSetChanged();
            //linearLayoutManager.scrollToPosition(lastPos);
            if(isFinished){
                return;
            }
            int curSize = adapter.getItemCount();
            haberList.addAll(dataList);
            adapter.notifyItemRangeInserted(curSize, haberList.size() - 1);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }
}
