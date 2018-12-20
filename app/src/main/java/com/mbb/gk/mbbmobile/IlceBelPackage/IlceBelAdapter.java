package com.mbb.gk.mbbmobile.IlceBelPackage;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbb.gk.mbbmobile.R;

import java.util.ArrayList;

public class IlceBelAdapter extends RecyclerView.Adapter<IlceBelAdapter.MyIlceBelHolder> {

    private Context mContext;
    private ArrayList<IlceBel> ilceBelList;

    public IlceBelAdapter(Context mContext, ArrayList<IlceBel> ilceBelList) {
        this.mContext = mContext;
        this.ilceBelList = ilceBelList;
    }

    public class MyIlceBelHolder extends RecyclerView.ViewHolder {
        private TextView bName, bBaskan, bPhone;
        private CardView cardView;

        public MyIlceBelHolder(View view) {
            super(view);
            bName = (TextView) view.findViewById(R.id.bNameText);
            bBaskan = (TextView) view.findViewById(R.id.bBaskanText);
            bPhone = (TextView) view.findViewById(R.id.bPhoneText);
            cardView = (CardView) view.findViewById(R.id.ilceBel_cardView);
        }
    }

    @Override
    public MyIlceBelHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ilcebel_card, parent, false);
        final MyIlceBelHolder viewHolder = new MyIlceBelHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyIlceBelHolder holder, int position) {
        IlceBel ilceBel = ilceBelList.get(position);
        holder.bName.setText(ilceBel.getName());
        holder.bBaskan.setText(ilceBel.getBaskan());
        holder.bPhone.setText(ilceBel.getPhone());
    }

    @Override
    public int getItemCount() {
        return ilceBelList.size();
    }
}
