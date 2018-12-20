package com.mbb.gk.mbbmobile;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class BaskanActivity extends AppCompatActivity {

    TextView baskanText;
    StringBuilder mSB;
    final String url = "https://www.mersin.bel.tr/belediye-baskanimiz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baskan);
        baskanText = (TextView) findViewById(R.id.baskanText);
        mSB = new StringBuilder();
        new FetchContent().execute();

        /*
        OKHTPP İLE

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mData = new GetHTTPPage().execute(url).get();
                    if(mData!=null){
                        baskanText.setText(mData);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitleEnabled(false);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    // COLLAPSED
                    toolbar.setTitle(R.string.baskan_isim);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorMBB)));

                }
                else
                {
                    // EXPANDED
                    toolbar.setTitle("");
                    getSupportActionBar().setBackgroundDrawable(null);


                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.baskan_site);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.baskan_website)));
                startActivity(siteIntent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private class FetchContent extends AsyncTask<Void, Void, Void>{

        String title;
        String content;

        @Override
        protected Void doInBackground(Void... voids) {
            // JSOUP ile
            try {
                Document doc = Jsoup.connect(url).get();
                //title = doc.title().substring(0, title.indexOf("-"));
                //System.out.println("TITLE\n"+title);
                //mSB.append(title + "\n");

                //Elements elements = doc.select("meta[name=description]");
                Elements elements = doc.select("div[class=detail] p");
                StringBuilder sb = new StringBuilder();
                for (Element e: elements){
                    sb.append(e.text() + "\n\n");
                }
                content = sb.toString();
                /*text = info.html();
                text = text.replaceAll("<p>","\n");
                text = text.replaceAll("</p>","");
                text = text.replaceAll("<br>","");*/
                //System.out.println("CONTENT\n"+text);
                //mSB.append(text);

            /*
            Elements info = doc.select("div#yourDivName");
            Elements info = doc.select("div[id=yourDivName"); // alternatif olarak bunuda kullanabilirsiniz.

            Elements info = doc.select("div.yourClassName");
            Elements info = doc.select("div[class=yourClassName"); // alternatif olarak bunuda kullanabilirsiniz.
             */


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            title = getString(R.string.baskan_isim);
            SpannableString spanTitle = new SpannableString(title);
            spanTitle.setSpan(new AbsoluteSizeSpan(55), 0, title.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spanTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            SpannableString spanContent = new SpannableString(content);
            spanContent.setSpan(new AbsoluteSizeSpan(40), 0, content.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            CharSequence finalText = TextUtils.concat(spanTitle, "\n", spanContent);
            baskanText.setText("");
            baskanText.setText(finalText);

        }
    }


}
