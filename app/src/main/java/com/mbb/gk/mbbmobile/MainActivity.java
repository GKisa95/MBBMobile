package com.mbb.gk.mbbmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mbb.gk.mbbmobile.EczanePackage.EczaneActivity;
import com.mbb.gk.mbbmobile.HaberPackage.HaberActivity;
import com.mbb.gk.mbbmobile.IlceBelPackage.IlceBelActivity;
import com.mbb.gk.mbbmobile.Iletisim.IletisimActivity;
import com.mbb.gk.mbbmobile.OtobusPackage.OtobusActivity;
import com.mbb.gk.mbbmobile.ProjePackage.ProjeActivity;

public class MainActivity extends AppCompatActivity {


    Button buttonBaskan;
    Button buttonHaber;
    Button buttonProje;
    Button buttonNobEczane;
    Button buttonIlceBel;
    Button buttonOtobusSefer;
    Button buttonVR;
    Button buttonIletisim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBaskan = (Button) findViewById(R.id.buttonBaskan);
        buttonBaskan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent baskanIntent = new Intent(MainActivity.this, BaskanActivity.class);
                startActivity(baskanIntent);
            }
        });

        buttonHaber = (Button) findViewById(R.id.buttonHaber);
        buttonHaber.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent haberIntent = new Intent(MainActivity.this,HaberActivity.class);
                startActivity(haberIntent);
            }
        });

        buttonProje = (Button) findViewById(R.id.buttonProje);
        buttonProje.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent projeIntent = new Intent(MainActivity.this, ProjeActivity.class);
                startActivity(projeIntent);
            }
        });


        buttonNobEczane = (Button) findViewById(R.id.buttonNobEczane);
        buttonNobEczane.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eczaneIntent = new Intent(MainActivity.this, EczaneActivity.class);
                startActivity(eczaneIntent);
            }
        });

        buttonIlceBel = (Button) findViewById(R.id.buttonIlceBel);
        buttonIlceBel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ilceBelIntent = new Intent(MainActivity.this, IlceBelActivity.class);
                startActivity(ilceBelIntent);
            }
        });

        buttonOtobusSefer = (Button) findViewById(R.id.buttonBusSefer);
        buttonOtobusSefer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otobusIntent = new Intent(MainActivity.this, OtobusActivity.class);
                startActivity(otobusIntent);
            }
        });

        buttonVR = (Button) findViewById(R.id.buttonVR);
        buttonVR.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vrIntent = new Intent(MainActivity.this, VRActivity.class);
                startActivity(vrIntent);
            }
        });

        buttonIletisim = (Button) findViewById(R.id.buttonIletisim);
        buttonIletisim.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iletisimIntent = new Intent(MainActivity.this, IletisimActivity.class);
                startActivity(iletisimIntent);
            }
        });

    }
}
