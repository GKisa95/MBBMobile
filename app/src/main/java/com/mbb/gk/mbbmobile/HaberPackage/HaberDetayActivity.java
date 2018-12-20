package com.mbb.gk.mbbmobile.HaberPackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mbb.gk.mbbmobile.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HaberDetayActivity extends AppCompatActivity {

    private ImageView haberImage;
    private TextView haberDetayText;
    private Haber haber;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haber_detay);

        haberImage = (ImageView) findViewById(R.id.haberDetayImage);
        haberDetayText = (TextView) findViewById(R.id.haberDetayText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_hDetay);

        haber = (Haber) getIntent().getExtras().getParcelable("selected_haber");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        new FetchHaberDetay().execute();
    }

    private class FetchHaberDetay extends AsyncTask<Void, Void, Void>{

        String title;
        String content;
        Bitmap bitmap;

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document doc = Jsoup.connect(haber.getHaberURL()).get();
                Elements elements = doc.select("div[class=detail] p");
                StringBuilder sb = new StringBuilder();
                for (Element e: elements){
                    sb.append(e.text() + "\n\n");
                }
                content = sb.toString();

                String imageUrl = haber.getImageURL();
                imageUrl = imageUrl.replace("/sm/","/lg/");
                InputStream input = new URL(imageUrl).openStream();
                bitmap = BitmapFactory.decodeStream(input);


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
            //bitmap = haber.getImage();
            progressBar.setVisibility(View.INVISIBLE);
            haberImage.setImageBitmap(bitmap);
            title = haber.getText();
            CharSequence finalText = TextUtils.concat(title, "\n", content);
            haberDetayText.setText("");
            haberDetayText.setText(finalText);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
