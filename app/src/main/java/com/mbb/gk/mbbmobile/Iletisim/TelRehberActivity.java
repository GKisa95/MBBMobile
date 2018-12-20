package com.mbb.gk.mbbmobile.Iletisim;

import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.mbb.gk.mbbmobile.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class TelRehberActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TelRehberAdapter adapter;
    private ArrayList<Personel> personelList;
    String url = "https://erehber.mersin.bel.tr/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_rehber);

        recyclerView = (RecyclerView) findViewById(R.id.rehber_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_rehber);
        personelList = new ArrayList<>();

        getSupportActionBar().setTitle(R.string.mbb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

        new FetchTelRehber().execute();

        adapter = new TelRehberAdapter(this,personelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private class FetchTelRehber extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(url).maxBodySize(0).get();
                Elements allElements = doc.select("td[style=  text-align: left]");
                Elements tableElements = allElements.select("table");   //personel listesi
                for (Element tableElement : tableElements) {
                    Elements ee = tableElement.select("td");    // bir personelin bilgileri
                    String[] splt = ee.html().split("\n");
                    Personel personel = new Personel();
                    personel.setName(splt[1]);
                    personel.setPhone(splt[3]);
                    personel.setEmail(splt[5]);
                    personel.setDepartment(splt[7]);
                    personel.setJob(splt[9]);
                    personel.setDeptPhone(splt[11]);
                    personel.setDeptMail(splt[13]);
                    personel.setConnectedTo(splt[15]);
                    personelList.add(personel);
                }
            } catch (IOException ex) {
                System.out.println("IOexception " + ex.getMessage());
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rehber, menu);

        MenuItem search = menu.findItem(R.id.search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        /*
        *   SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        * */
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
