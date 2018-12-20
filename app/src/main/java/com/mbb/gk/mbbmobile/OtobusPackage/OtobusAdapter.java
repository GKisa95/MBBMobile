package com.mbb.gk.mbbmobile.OtobusPackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbb.gk.mbbmobile.R;

import java.util.ArrayList;

public class OtobusAdapter extends RecyclerView.Adapter<OtobusAdapter.MyOtobusHolder> {

    private Context mContext;
    private ArrayList<Otobus> otobusList;

    public OtobusAdapter(Context mContext, ArrayList<Otobus> otobusList) {
        this.mContext = mContext;
        this.otobusList = otobusList;
    }

    public class MyOtobusHolder extends RecyclerView.ViewHolder{

        private TextView otobusName;
        private CardView otobusCardView;

        public MyOtobusHolder(View view){
            super(view);
            otobusName = (TextView) view.findViewById(R.id.otobusCardText);
            otobusCardView = (CardView) view.findViewById(R.id.otobus_cardView);
            otobusCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = MyOtobusHolder.this.getAdapterPosition();
                    Otobus selectedOtobus = otobusList.get(pos);

                    Intent otobusDetayIntent = new Intent(mContext, OtobusDetayActivity.class);
                    otobusDetayIntent.putExtra("selected_otobus", selectedOtobus);
                    mContext.startActivity(otobusDetayIntent);
                }
            });
        }
    }

    @Override
    public MyOtobusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.otobus_card, parent, false);
        final MyOtobusHolder viewHolder = new MyOtobusHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyOtobusHolder holder, int position) {
        Otobus otobus = otobusList.get(position);
        holder.otobusName.setText(otobus.getName());
    }

    @Override
    public int getItemCount() {
        return otobusList.size();
    }


}
