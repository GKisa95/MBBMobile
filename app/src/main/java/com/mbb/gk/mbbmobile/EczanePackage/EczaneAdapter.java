package com.mbb.gk.mbbmobile.EczanePackage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbb.gk.mbbmobile.R;

import java.util.ArrayList;

public class EczaneAdapter extends RecyclerView.Adapter<EczaneAdapter.MyEczaneHolder> {

    private Context mContext;
    private ArrayList<Eczane> eczaneList;
    private PharmacyAdapterListener listener;


    /*public EczaneAdapter(Context mContext, List<Eczane> eczaneList, PharmacyAdapterListener listener){
        this.mContext = mContext;
        this.eczaneList = eczaneList;
        this.listener = listener;
    }*/

    public EczaneAdapter(Context mContext, ArrayList<Eczane> eczaneList) {
        this.mContext = mContext;
        this.eczaneList = eczaneList;
        this.listener = listener;
    }

    public class MyEczaneHolder extends RecyclerView.ViewHolder {
        public TextView pName, pAddress, pPhone, pDistrict;
        public CardView cardView;

        public MyEczaneHolder(View view) {
            super(view);
            pName = (TextView) view.findViewById(R.id.pNameText);
            pAddress = (TextView) view.findViewById(R.id.pAddressText);
            pPhone = (TextView) view.findViewById(R.id.pPhoneText);
            pDistrict = (TextView) view.findViewById(R.id.pDistrict);
            cardView = (CardView) view.findViewById(R.id.pharmacy_cardView);
            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = pName.getText().toString();
                    //String address = pAddress.getText().toString();
                    final String phone = pPhone.getText().toString();
                    //String district = pDistrict.getText().toString();
                    //final Eczane eczane = new Eczane(name, address, phone, district);
                    int selectedItemPosition = 0;
                    for (int i = 0; i < eczaneList.size(); i++){
                        if(eczaneList.get(i).getName().equalsIgnoreCase(name) && eczaneList.get(i).getPhone().equalsIgnoreCase(phone)){
                            selectedItemPosition = i;
                            break;
                        }
                    }
                    final Eczane selectedEczane = eczaneList.get(selectedItemPosition);

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                    dialogBuilder.setTitle(name);
                    dialogBuilder.setMessage(selectedEczane.getAddress() + "\n" + selectedEczane.getPhone());
                    dialogBuilder.setNeutralButton("Haritada Göster", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            double latitude = selectedEczane.getLatitude();
                            double longitude = selectedEczane.getLongitude();
                            Uri coordinatesUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "&z=21");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, coordinatesUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            mContext.startActivity(mapIntent);
                        }
                    });
                    dialogBuilder.setPositiveButton("Ara", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.out.println("-------------------- CAAAALLLLLL");
                            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                            String callNumber = phone.replace(" ", "");
                            if(!callNumber.startsWith("0")){
                                callNumber = "0" + callNumber;
                            }
                            phoneIntent.setData(Uri.parse("tel:" + callNumber));
                            mContext.startActivity(phoneIntent);
                        }
                    });

                    dialogBuilder.show();
                }
            });
        }
    }

    @Override
    public MyEczaneHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pharmacy_card, parent, false);
        final MyEczaneHolder viewHolder = new MyEczaneHolder(itemView);
        /*itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });*/
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyEczaneHolder holder, int position) {
        Eczane eczane = eczaneList.get(position);
        holder.pName.setText(eczane.getName());
        holder.pAddress.setText(eczane.getAddress());
        holder.pPhone.setText(eczane.getPhone());
        holder.pDistrict.setText(eczane.getDistrict());

        /*  //11111111111111111111
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCardSelected(position);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return eczaneList.size();
    }


    public interface PharmacyAdapterListener{

        //void onItemClick(View v,int position);

    /*  // 111111111111111111111111
        void onAddToFavoriteSelected(int position);

        void onPlayNextSelected(int position);

        void onCardSelected(int position);
    */
    }
}
