package com.mbb.gk.mbbmobile.EczanePackage;

import android.support.v7.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.mbb.gk.mbbmobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EczaneActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EczaneAdapter adapter;
    private ArrayList<Eczane> eczaneList;

    //TextView eczaneText;
    StringBuilder urlBuilder = new StringBuilder("http://www.mersineczaciodasi.org.tr/Nobet/NobetListesi?NobetTarih=");
    // URL Format =>> http://www.mersineczaciodasi.org.tr/Nobet/NobetListesi?NobetTarih=2018-04-18

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eczane);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.nobetci_eczane);

        //eczaneText = (TextView) findViewById(R.id.eczaneText);
        recyclerView = (RecyclerView) findViewById(R.id.pharmacy_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_eczane);
        eczaneList = new ArrayList<>();

        /*  //1111111111111111111111111
        adapter = new EczaneAdapter(this, eczaneList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

        new FetchPharmacy().execute();

        adapter = new EczaneAdapter(this, eczaneList);

        /*adapter = new EczaneAdapter(this, eczaneList, new EczaneAdapter.PharmacyAdapterListener() {
            @Override
            public void onItemClick(View v, int position) {
                // Specify what to do on item click
                Eczane pharmacy = eczaneList.get(position);
                Toast.makeText(getApplicationContext(), pharmacy.getName(), Toast.LENGTH_LONG);

            }
        });*/

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private class FetchPharmacy extends AsyncTask<Void, Void, Void> {

        String result;

        @Override
        protected Void doInBackground(Void... voids) {

            Calendar c = Calendar.getInstance();
            int sYear = c.get(Calendar.YEAR);
            int sMonth = c.get(Calendar.MONTH) + 1;
            int sDay = c.get(Calendar.DAY_OF_MONTH);

            String finalDate = (sYear<=9?"0"+sYear:String.valueOf(sYear)) + "-" + (sMonth<=9?"0"+sMonth:String.valueOf(sMonth)) + "-" + (sDay<=9?"0"+sDay:String.valueOf(sDay));
            urlBuilder.append(finalDate);


            try{
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(urlBuilder.toString())
                        .build();
                Response response = client.newCall(request).execute();
                result = response.body().string();

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

            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i<jsonArray.length(); i++){
                    // I need "eczane_adi", "adres", "telefon1"
                    JSONObject jsonPharmacy = jsonArray.getJSONObject(i);
                    String pName = jsonPharmacy.getString("eczane_adi").toString();
                    String pAddress = jsonPharmacy.getString("adres").toString();
                    String pPhone = jsonPharmacy.getString("telefon1").toString();
                    String pDistrict = jsonPharmacy.getString("ilce").toString();
                    String coordinates = jsonPharmacy.getString("konum").toString();
                    coordinates = coordinates.replace(" ", "");
                    String[] split = coordinates.split(",");
                    double latitude = Double.parseDouble(split[0]);
                    double longitude = Double.parseDouble(split[1]);
                    eczaneList.add(new Eczane(pName, pAddress, pPhone, pDistrict, latitude, longitude));
                }
            } catch (JSONException e) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EczaneActivity.this);
                dialogBuilder.setTitle("HATA");
                dialogBuilder.setMessage("Nöbetçi eczaneler listesine şu anda ulaşılamıyor.");
                dialogBuilder.setPositiveButton("Kapat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }
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

    /*
        // 1111111111111111111111111111111111111
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration{


        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge){
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }


        /**
         * RecyclerView item decoration - give equal margin around grid item

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    */
}
