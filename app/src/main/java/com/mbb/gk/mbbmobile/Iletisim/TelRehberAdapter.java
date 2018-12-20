package com.mbb.gk.mbbmobile.Iletisim;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mbb.gk.mbbmobile.R;

import java.util.ArrayList;

public class TelRehberAdapter extends RecyclerView.Adapter<TelRehberAdapter.MyRehberHolder> implements Filterable{

    private Context mContext;
    private ArrayList<Personel> personelList;
    private ArrayList<Personel> filteredPersonelList;

    public TelRehberAdapter(Context mContext, ArrayList<Personel> personelList) {
        this.mContext = mContext;
        this.personelList = personelList;
        this.filteredPersonelList = personelList;
    }


    public class MyRehberHolder extends RecyclerView.ViewHolder{

        private TextView name, phone, email, department, job, deptPhone, deptMail, connectedTo;
        private CardView rehberCardView;

        public MyRehberHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.perNameText);
            phone = (TextView) view.findViewById(R.id.perPhoneText);
            email = (TextView) view.findViewById(R.id.perEmailText);
            department = (TextView) view.findViewById(R.id.perDeptText);
            job = (TextView) view.findViewById(R.id.perJobText);
            deptPhone = (TextView) view.findViewById(R.id.perDPhoneText);
            deptMail = (TextView) view.findViewById(R.id.perDMailText);
            connectedTo = (TextView) view.findViewById(R.id.perConnText);
            rehberCardView = (CardView) view.findViewById(R.id.personel_cardView);
        }
    }

    @Override
    public MyRehberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.personel_card, parent, false);
        final MyRehberHolder viewHolder = new MyRehberHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRehberHolder holder, int position) {
        Personel personel = filteredPersonelList.get(position);
        holder.name.setText(personel.getName());
        holder.phone.setText(personel.getPhone());
        holder.email.setText(personel.getEmail());
        holder.department.setText(personel.getDepartment());
        holder.job.setText(personel.getJob());
        holder.deptPhone.setText(personel.getDeptPhone());
        holder.deptMail.setText(personel.getDeptMail());
        holder.connectedTo.setText(personel.getConnectedTo());

    }

    @Override
    public int getItemCount() {
        return filteredPersonelList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if(charString.isEmpty()){
                    filteredPersonelList  = personelList;
                } else{
                    ArrayList<Personel> tempFilteredList   = new ArrayList<>();
                    for (Personel personel : personelList){
                        if(personel.getName().toLowerCase().contains(charString.toLowerCase()) || personel.getDepartment().toLowerCase().contains(charString.toLowerCase())){
                            tempFilteredList .add(personel);
                        }
                    }
                    filteredPersonelList = tempFilteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredPersonelList ;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredPersonelList = (ArrayList<Personel>) results.values;
                notifyDataSetChanged();
            }
        };
    }


}
