package com.mbb.gk.mbbmobile.ProjePackage;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbb.gk.mbbmobile.R;

import java.util.ArrayList;

public class ProjeAdapter extends RecyclerView.Adapter<ProjeAdapter.MyProjeHolder>  {

    private Context mContext;
    private ArrayList<Proje> projeList;

    public ProjeAdapter(Context mContext, ArrayList<Proje> projeList) {
        this.mContext = mContext;
        this.projeList = projeList;
    }

    public class MyProjeHolder extends RecyclerView.ViewHolder {

        private ImageView projeImage;
        private TextView projeCardText, tarihText;
        private CardView cardView;

        public MyProjeHolder(View view) {
            super(view);
            projeImage = (ImageView) view.findViewById(R.id.projeImage);
            projeCardText = (TextView) view.findViewById(R.id.projeCardText);
            tarihText = (TextView) view.findViewById(R.id.projeTarihText);
            cardView = (CardView) view.findViewById(R.id.proje_cardView);
        }
    }

    @Override
    public ProjeAdapter.MyProjeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.proje_card, parent, false);
        final MyProjeHolder viewHolder = new MyProjeHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProjeAdapter.MyProjeHolder holder, int position) {
        Proje proje = projeList.get(position);
        holder.projeImage.setImageBitmap(proje.getImage());
        holder.projeCardText.setText(proje.getText());
        holder.tarihText.setText(proje.getTarih());
    }

    @Override
    public int getItemCount() {
        return projeList.size();
    }


}
