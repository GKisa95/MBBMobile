package com.mbb.gk.mbbmobile.Iletisim;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mbb.gk.mbbmobile.R;

public class IletisimActivity extends AppCompatActivity {

    private TextView addressTV;
    private Button buttonTelRehber;
    private Button buttonFacebook;
    private Button buttonTwitter;
    private Button buttonYoutube;
    private Button buttonInstagram;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iletisim);

        getSupportActionBar().setTitle(R.string.iletisim);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        addressTV = (TextView) findViewById(R.id.addressText);
        fillAddress();

        buttonTelRehber = (Button) findViewById(R.id.buttonTelRehber);
        buttonTelRehber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telRehberIntent = new Intent(IletisimActivity.this, TelRehberActivity.class);
                startActivity(telRehberIntent);
            }
        });

        buttonFacebook = (Button) findViewById(R.id.buttonFacebook);
        buttonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.facebook_link);
                openWebPage(url);
            }
        });

        buttonTwitter = (Button) findViewById(R.id.buttonTwitter);
        buttonTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.twitter_link);
                openWebPage(url);
            }
        });

        buttonYoutube = (Button) findViewById(R.id.buttonYoutube);
        buttonYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.youtube_link);
                openWebPage(url);
            }
        });

        buttonInstagram = (Button) findViewById(R.id.buttonInstagram);
        buttonInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.instagram_link);
                openWebPage(url);
            }
        });

    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }

    public void fillAddress(){
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Adres: ");
        strBuilder.append(getString(R.string.bel_adres) + "\n");
        strBuilder.append("Telefon: ");
        strBuilder.append(getString(R.string.bel_tel) + "\n");
        strBuilder.append("E-posta: ");
        strBuilder.append(getString(R.string.bel_eposta));

        addressTV.setText(strBuilder.toString());
    }




    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }
}
