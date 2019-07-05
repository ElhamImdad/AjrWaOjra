package com.example.smoot.ajerwaojra.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Fragments.RequestDetailFragment;
import com.example.smoot.ajerwaojra.Models.UmraRequest;
import com.example.smoot.ajerwaojra.R;
import java.util.ArrayList;

    public class RecyclerAdapterHD extends  RecyclerView.Adapter<RecyclerAdapterHD.MyViewHolder> {
        Context context;
        ArrayList<UmraRequest> requestList ;
        MyViewHolder.onCardClick onCardClick;

        public RecyclerAdapterHD( ArrayList<UmraRequest> requestList,MyViewHolder.onCardClick onCardClick) {
            this.requestList = requestList;
            this.onCardClick = onCardClick;
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.umrarequestcard,viewGroup,false);
            return new MyViewHolder(view , onCardClick);

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
            return requestList.size()-1;
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView requesterName;
            TextView countryName;
            TextView date ;
            ImageView countryFlag;
            onCardClick onCardClick;


            public MyViewHolder(View itemView , onCardClick onCardClick) {
                super(itemView);
                countryFlag= itemView.findViewById(R.id.countryFlag);
                date = itemView.findViewById(R.id.date);
                countryName = itemView.findViewById(R.id.country);
                requesterName = itemView.findViewById(R.id.requesterName);
                this.onCardClick=onCardClick;
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v){
           onCardClick.onCardClickLis(getAdapterPosition());


            }
            public interface onCardClick {
                void onCardClickLis(int position);
            }
        }
    }
