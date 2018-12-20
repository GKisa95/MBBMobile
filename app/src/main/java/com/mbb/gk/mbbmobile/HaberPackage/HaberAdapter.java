package com.mbb.gk.mbbmobile.HaberPackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbb.gk.mbbmobile.R;

import java.util.ArrayList;

public class HaberAdapter extends RecyclerView.Adapter<HaberAdapter.MyHaberHolder> {

    private Context mContext;
    private ArrayList<Haber> haberList;

    public HaberAdapter(Context mContext, ArrayList<Haber> haberList) {
        this.mContext = mContext;
        this.haberList = haberList;
    }

    public class MyHaberHolder extends RecyclerView.ViewHolder {
        private ImageView haberImage;
        private TextView haberCardText, tarihText;
        private CardView cardView;

        public MyHaberHolder(View view) {
            super(view);
            haberImage = (ImageView) view.findViewById(R.id.haberImage);
            haberCardText = (TextView) view.findViewById(R.id.haberCardText);
            tarihText = (TextView) view.findViewById(R.id.haberTarihText);
            cardView = (CardView) view.findViewById(R.id.haber_cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = MyHaberHolder.this.getAdapterPosition();
                    Haber selectedHaber = haberList.get(pos);

                    Intent haberDetayIntent = new Intent(mContext, HaberDetayActivity.class);
                    haberDetayIntent.putExtra("selected_haber", selectedHaber);
                    mContext.startActivity(haberDetayIntent);
                }
            });
        }
    }

    @Override
    public MyHaberHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.haber_card, parent, false);
        final MyHaberHolder viewHolder = new MyHaberHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyHaberHolder holder, int position) {
        Haber haber = haberList.get(position);
        //holder.haberImage.setImageURI();
        holder.haberImage.setImageBitmap(haber.getImage());
        holder.haberCardText.setText(haber.getText());
        holder.tarihText.setText(haber.getTarih());
    }

    @Override
    public int getItemCount() {
        return haberList.size();
    }
}
