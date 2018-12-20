package com.mbb.gk.mbbmobile.IlceBelPackage;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;


import com.mbb.gk.mbbmobile.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class IlceBelActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private IlceBelAdapter adapter;
    private ArrayList<IlceBel> ilceBelList;

    private String url = "https://www.mersin.bel.tr/ilce-belediyeler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilce_bel);

        getSupportActionBar().setTitle(R.string.ilce_bel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.ilceBel_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_ilceBel);
        ilceBelList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

        new FetchIlceBel().execute();

        adapter = new IlceBelAdapter(this,ilceBelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private class FetchIlceBel extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document doc = Jsoup.connect(url).get();
                Elements elements = doc.select("div[class=detail] tr");
                System.out.println("----------------------------------------");
                for(int i = 2; i<elements.size(); i++){
                    Element e = elements.get(i);
                    Elements ilceElements = e.getAllElements();
                    IlceBel belediye = new IlceBel(ilceElements.get(1).text(), ilceElements.get(5).text(),ilceElements.get(9).text());
                    ilceBelList.add(belediye);
                    /*for(int j = 0; j<ilceElements.size(); j++){
                        System.out.println(j + " --- " + ilceElements.get(j).text());
                    }
                    */
                }
                System.out.println("----------------------------------------");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }
}
