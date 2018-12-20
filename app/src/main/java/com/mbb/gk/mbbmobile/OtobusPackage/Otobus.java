package com.mbb.gk.mbbmobile.OtobusPackage;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ProgressBar;

public class Otobus implements Parcelable{
    private String busURL;
    private String imgURL;
    private String name;

    public Otobus() {
    }

    protected Otobus(Parcel in) {
        busURL = in.readString();
        imgURL = in.readString();
        name = in.readString();
    }

    public static final Creator<Otobus> CREATOR = new Creator<Otobus>() {
        @Override
        public Otobus createFromParcel(Parcel in) {
            return new Otobus(in);
        }

        @Override
        public Otobus[] newArray(int size) {
            return new Otobus[size];
        }
    };

    public String getBusURL() {
        return busURL;
    }

    public void setBusURL(String busURL) {
        this.busURL = busURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(busURL);
        dest.writeString(imgURL);
        dest.writeString(name);

    }
}
