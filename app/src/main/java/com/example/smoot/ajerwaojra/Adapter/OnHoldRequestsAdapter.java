package com.example.smoot.ajerwaojra.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Models.ServiceInfo;
import com.example.smoot.ajerwaojra.R;

import java.util.ArrayList;

public class OnHoldRequestsAdapter extends RecyclerView.Adapter<OnHoldRequestsAdapter.MyHolder>{
    private ArrayList<ServiceInfo> listServices;
    Context context;

    public OnHoldRequestsAdapter(ArrayList<ServiceInfo> listServices, Context context) {
        this.listServices = listServices;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_info, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        final ServiceInfo items = listServices.get(i);
        myHolder.doerName.setText(items.getDoerName());
    }

    @Override
    public int getItemCount() {
        return listServices.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder  {
        TextView doerName, numCompletedOrder, date, omraName;
        String review;

        public MyHolder(View itemView) {
            super(itemView);
            doerName = itemView.findViewById(R.id.viewDoerName);


        }

    }
}
