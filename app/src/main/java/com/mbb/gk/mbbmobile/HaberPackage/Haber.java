package com.mbb.gk.mbbmobile.HaberPackage;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


public class Haber implements Parcelable{

    private Bitmap image;
    private String imageURL;
    private String haberURL;
    private String text;
    private String tarih;

    public Haber(Bitmap image,String imageURL, String haberURL, String text, String tarih) {
        this.image = image;
        this.imageURL = imageURL;
        this.haberURL = haberURL;
        this.text = text;
        this.tarih = tarih;
    }

    public Haber() {
    }

    protected Haber(Parcel in) {
        image = in.readParcelable(Bitmap.class.getClassLoader());
        imageURL = in.readString();
        haberURL = in.readString();
        text = in.readString();
        tarih = in.readString();
    }

    public static final Creator<Haber> CREATOR = new Creator<Haber>() {
        @Override
        public Haber createFromParcel(Parcel in) {
            return new Haber(in);
        }

        @Override
        public Haber[] newArray(int size) {
            return new Haber[size];
        }
    };

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getHaberURL() {
        return haberURL;
    }

    public void setHaberURL(String haberURL) {
        this.haberURL = haberURL;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(image,flags);
        dest.writeString(imageURL);
        dest.writeString(haberURL);
        dest.writeString(text);
        dest.writeString(tarih);
    }
}
