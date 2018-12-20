package com.mbb.gk.mbbmobile.ProjePackage;

import android.graphics.Bitmap;

public class Proje {

    private Bitmap image;
    private String imageURL;
    private String projeURL;
    private String text;
    private String tarih;

    public Proje(Bitmap image, String imageURL, String projeURL, String text, String tarih) {
        this.image = image;
        this.imageURL = imageURL;
        this.projeURL = projeURL;
        this.text = text;
        this.tarih = tarih;
    }

    public Proje() {
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getProjeURL() {
        return projeURL;
    }

    public void setProjeURL(String projeURL) {
        this.projeURL = projeURL;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
}
