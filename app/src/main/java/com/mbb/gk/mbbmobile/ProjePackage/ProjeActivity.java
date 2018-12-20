package com.mbb.gk.mbbmobile.ProjePackage;

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

public class ProjeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ProjeAdapter adapter;
    private ArrayList<Proje> projeList;
    private ArrayList<Proje> dataList;
    private Elements dataElements;
    private EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager linearLayoutManager;

    private int oneFetchDataSize = 10;
    int lastPos = 0;

    String url = "https://www.mersin.bel.tr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proje);

        getSupportActionBar().setTitle(R.string.projeler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.proje_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_proje);
        projeList = new ArrayList<>();
        dataList = new ArrayList<>();
        dataElements = new Elements();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);

        new FetchProje().execute();

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                new LoadNextData().execute();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        adapter = new ProjeAdapter(this, projeList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private class FetchProje extends AsyncTask<Void, Integer, Void> {

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
                String projeURL = url + "/proje";
                Document doc = Jsoup.connect(projeURL).get();
                Elements res = doc.select("div[class=detail]");
                dataElements = res.select("a");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.INVISIBLE);
            new LoadNextData().execute();
        }
    }


    private class LoadNextData extends AsyncTask<Void, Integer, Void> {

        boolean isFinished = false;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            scrollListener.resetState();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(dataElements.size() <= lastPos){
                isFinished = true;
                return null;
            }
            if(dataElements.size() - lastPos < oneFetchDataSize){
                oneFetchDataSize = dataElements.size() - lastPos;
            }

            dataList = new ArrayList<>();

            for (int i = lastPos; i < (lastPos + oneFetchDataSize); i++) {
                try {
                    Element e = dataElements.get(i);

                    Proje proje = new Proje();
                    String link = url + e.select("a").attr("href");
                    String imageURL = url + e.select("img").attr("src");
                    String[] spl = e.text().split(" ");
                    String date = "";
                    String pText = "";
                    for (int j = 0; j < spl.length; j++) {
                        if (j < 4) {
                            date += spl[j] + " ";
                        } else {
                            pText += spl[j] + " ";
                        }
                    }
                    pText = pText.replace((char) 145, (char) 39);
                    pText = pText.replace((char) 146, (char) 39);
                    pText = pText.replace((char) 147, (char) 34);
                    pText = pText.replace((char) 148, (char) 34);

                    // GET IMAGE
                    InputStream input = new URL(imageURL).openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    proje.setProjeURL(link);
                    proje.setImageURL(imageURL);
                    proje.setImage(bitmap);
                    proje.setTarih(date);
                    proje.setText(pText);
                    dataList.add(proje);
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
            if(isFinished){
                return;
            }
            int curSize = adapter.getItemCount();
            projeList.addAll(dataList);
            adapter.notifyItemRangeInserted(curSize, projeList.size() - 1);
        }
    }




    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }
}
