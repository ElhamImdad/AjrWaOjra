package com.example.smoot.ajerwaojra.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Models.UmraRequest;
import com.example.smoot.ajerwaojra.R;

import java.util.ArrayList;





    public class RecyclerAdapterHD extends  RecyclerView.Adapter<RecyclerAdapterHD.MyViewHolder> {
        Context context;
        ArrayList<UmraRequest> requestList ;

        public RecyclerAdapterHD(Context context, ArrayList<UmraRequest> requestList) {
            this.context = context;
            this.requestList = requestList;
        }


        @NonNull
        @Override
        public RecyclerAdapterHD.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.umrarequestcard,viewGroup,false);
            return new MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int i) {
            // holder.countryFlag.setImageResource(requestList.get(i).getCountryFlagImagePath());
            holder.requesterName.setText(requestList.get(i).getRequesterName());
            holder.countryName.setText(requestList.get(i).getCountry());
            holder.date.setText(requestList.get(i).getDate());
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return requestList.size();
        }

        /*  @Override
          public View getView(int position, View view, ViewGroup parent) {
              LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              if (view==null){
                  view=inflater.inflate(R.layout.umrarequestcard, null); }

              TextView requesterName = (TextView) view.findViewById(R.id.requesterName);
              TextView countryName = (TextView) view.findViewById(R.id.country);
              TextView date = (TextView) view.findViewById(R.id.date);
              // put data int text views
              requesterName.setText(requestList.get(position).getRequesterName().toString());
              countryName.setText(requestList.get(position).getCountry().toString());
              date.setText(requestList.get(position).getDate().toString());
              return view;
          }*/
        public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView requesterName;
            TextView countryName;
            TextView date ;
            ImageView countryFlag;


            public MyViewHolder(View itemView) {
                super(itemView);
                countryFlag= itemView.findViewById(R.id.countryFlag);
                date = itemView.findViewById(R.id.date);
                countryName = itemView.findViewById(R.id.country);
                requesterName = itemView.findViewById(R.id.requesterName);

                itemView.setOnClickListener(this);}

            @Override
            public void onClick(View v){

            }
        }
    }
