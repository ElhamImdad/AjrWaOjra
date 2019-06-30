package com.example.smoot.ajerwaojra.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Helpers.OmraInfo;
import com.example.smoot.ajerwaojra.R;

import java.util.ArrayList;

public class RequestsAdapter  extends RecyclerView.Adapter<RequestsAdapter.MyViewHolder>{

    ArrayList<OmraInfo> umraServicesList;
    Context c;

    public RequestsAdapter(Context context, ArrayList<OmraInfo> umraRequestsList) {
        this.c=context;
        this.umraServicesList = umraRequestsList;
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView textName;


        public MyViewHolder( View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.umraName);


        }

    }

    @NonNull
    @Override
    public RequestsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_list_of_requests, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.textName.setText(umraServicesList.get(i).getDoerName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/////////*****************************************************************************************
            }
        });
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return umraServicesList.size();
    }


}
