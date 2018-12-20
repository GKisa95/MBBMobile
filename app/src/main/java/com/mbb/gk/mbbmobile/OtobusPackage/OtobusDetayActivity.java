package com.mbb.gk.mbbmobile.OtobusPackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mbb.gk.mbbmobile.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class OtobusDetayActivity extends AppCompatActivity {

    private ImageView lineImage;
    private Otobus otobus;
    private ProgressBar progressBar;
    private TableLayout tableLayout;
    private ArrayList<String> busTimeData;
    private String url = "https://www.mersin.bel.tr";
    private boolean isStandard = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otobus_detay);

        lineImage = (ImageView) findViewById(R.id.otobusLineImage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_oDetay);
        tableLayout = (TableLayout) findViewById(R.id.busTimeTable);


        otobus = (Otobus) getIntent().getExtras().getParcelable("selected_otobus");
        busTimeData = new ArrayList<>();

        getSupportActionBar().setTitle(otobus.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        new FetchOtobusDetay().execute();
    }

    private class FetchOtobusDetay extends AsyncTask<Void, Void, Void>{

        String imgURL;
        ArrayList<String> start = new ArrayList<>();
        ArrayList<String> end = new ArrayList<>();
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document doc = Jsoup.connect(otobus.getBusURL()).get();
                Elements allElements = doc.select("div[class=detail]");
                imgURL =  url + allElements.select("img").attr("src");
                Elements tableElements = allElements.select("table");

                // Sade Tablolar
                if(tableElements.size() == 1){
                    busTimeData = new ArrayList<>();
                    Elements table = tableElements.get(0).select("td");
                    if(table.get(0).text().startsWith("HAT ")){
                        table.remove(0);
                    }
                    for(Element e: table){
                        busTimeData.add(e.text());
                    }
                }else{
                    busTimeData = new ArrayList<>();
                    start.addAll(Arrays.asList(tableElements.select("td[align=right]").html().split("\n")));
                    end.addAll(Arrays.asList(tableElements.select("td[align=left]").html().split("\n")));
                    int max = start.size() > end.size() ? start.size() : end.size();
                    for(int i = 0; i< max; i++){
                        try{
                            busTimeData.add(start.get(i));
                            busTimeData.add(end.get(i));
                        }catch(Exception ee){
                            busTimeData.add("");
                        }
                    }
                }
                InputStream input = new URL(imgURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.INVISIBLE);
            lineImage.setImageBitmap(bitmap);
            constructTable();
        }
    }


    public void constructTable() {
        tableLayout.removeAllViews();
        /*DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int tablePX = displayMetrics.widthPixels;
        float tableDP = tablePX / ((float)displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);*/

        for (int i = 0; i < busTimeData.size(); i = i + 2){

            final TextView tvStart = new TextView(this);
            tvStart.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            tvStart.setGravity(Gravity.RIGHT);
            tvStart.setPadding(0,10,25,10);
            if(i == 0){
                tvStart.setBackgroundColor(Color.parseColor("#C0C0C0"));
            }else{
                tvStart.setBackgroundColor(Color.parseColor("#E0E0E0"));
            }
            try{
                tvStart.setText(busTimeData.get(i));
            } catch (Exception ex){
                tvStart.setText("");
            }


            final TextView tvEnd = new TextView(this);
            tvEnd.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            tvEnd.setGravity(Gravity.LEFT);
            tvEnd.setPadding(25,10,0,10);
            if(i == 0){
                tvEnd.setBackgroundColor(Color.parseColor("#C0C0C0"));
            }else{
                tvEnd.setBackgroundColor(Color.parseColor("#E0E0E0"));
            }
            try{
                tvEnd.setText(busTimeData.get(i+1));
            } catch (Exception ex){
                tvStart.setText("");
            }

            final TableRow tr = new TableRow(this);
            tr.setId(i+1);
            TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            trParams.setMargins(0, 0, 0, 0);
            tr.setPadding(0,0,0,0);
            tr.setLayoutParams(trParams);

            tr.addView(tvStart);
            tr.addView(tvEnd);

            tableLayout.addView(tr,trParams);

            if(i > 0){
                final TableRow trVerSep = new TableRow(this);
                TableLayout.LayoutParams trParamsVerSep = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                trParamsVerSep.setMargins(0, 0, 0, 0);
                trVerSep.setLayoutParams(trParamsVerSep);
                TextView tvVerSep = new TextView(this);
                TableRow.LayoutParams tvSepLay = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                tvSepLay.span = 2;
                tvVerSep.setLayoutParams(tvSepLay);
                tvVerSep.setBackgroundColor(Color.parseColor("#000000"));
                tvVerSep.setHeight(1);
                trVerSep.addView(tvVerSep);
                tableLayout.addView(trVerSep, trParamsVerSep);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }
}
