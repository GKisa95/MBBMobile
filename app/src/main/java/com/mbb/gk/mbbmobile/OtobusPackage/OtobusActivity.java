package com.mbb.gk.mbbmobile.OtobusPackage;

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

public class OtobusActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private OtobusAdapter adapter;
    private ArrayList<Otobus> otobusList;

    private String url = "https://www.mersin.bel.tr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otobus);

        getSupportActionBar().setTitle(R.string.bus_sefer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.otobus_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_bus);
        otobusList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

        new FetchOtobus().execute();

        adapter = new OtobusAdapter(this,otobusList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private class FetchOtobus extends AsyncTask<Void, Void, Void>{

        String busName;
        String busURL;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document doc = Jsoup.connect(url + "/otobus-seferleri").get();
                Elements res = doc.select("ul[class=list-menu]").select("a");
                for(Element e: res){
                    busURL = url + e.select("a").attr("href");
                    busName = e.text();

                    Otobus otobus = new Otobus();
                    otobus.setBusURL(busURL);
                    otobus.setName(busName);
                    otobusList.add(otobus);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }
}
