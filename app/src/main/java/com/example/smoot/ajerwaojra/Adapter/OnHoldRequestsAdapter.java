package com.example.smoot.ajerwaojra.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Activities.OmrahDetailsActivity;
import com.example.smoot.ajerwaojra.Fragments.OnholdRequestsFragment;
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
         ServiceInfo items = listServices.get(i);
         float review = Float.parseFloat(items.getRating());
        Log.e("numberofStar is" , String.valueOf(review));
        myHolder.doerName.setText(items.getDoerName());
        Log.e("cooooo", String.valueOf(items.getNoCompletedOrder()));
        myHolder.numCompletedOrder.setText(" عدد الطلبات " +items.getNoCompletedOrder());
        myHolder.date.setText(items.getDate());
        myHolder.omraName.setText("\n سيقوم بالحج عن "+items.getOmraName());
        myHolder.ratingBar.setRating(review);
        final  int orderId = items.getOrder_id();
     //   final Bundle bundle = new Bundle();
        myHolder.positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("yeeeees", "yes");
                OnholdRequestsFragment on = new OnholdRequestsFragment();
                on.acceptOrder(orderId);

                Intent intent = new Intent(context, OmrahDetailsActivity.class);
                intent.putExtra("option", "positive");
                intent.putExtra("OrderIdP", orderId);

                context.startActivity(intent);
            }
        });
        myHolder.negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnholdRequestsFragment on2 = new OnholdRequestsFragment();
                on2.rejectOrder(orderId);

                Intent intent = new Intent(context, OmrahDetailsActivity.class);
                intent.putExtra("option", "negative");
                intent.putExtra("OrderIdN", orderId);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listServices.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder  {
        TextView doerName, numCompletedOrder, date, omraName;
        RatingBar ratingBar;
        Button positiveBtn, negativeBtn;

        public MyHolder(View itemView) {
            super(itemView);
            doerName = itemView.findViewById(R.id.viewDoerName);
            numCompletedOrder = itemView.findViewById(R.id.nomberOfOmra);
            date = itemView.findViewById(R.id.approximateDate);
            omraName = itemView.findViewById(R.id.textViewDoerName);
            ratingBar = itemView.findViewById(R.id.ratingBarPic);

            positiveBtn = itemView.findViewById(R.id.acceptBtn);
            negativeBtn = itemView.findViewById(R.id.notAcceptBtn);

        }

    }
}
