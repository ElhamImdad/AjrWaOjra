package com.example.smoot.ajerwaojra.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Activities.OmrahDetailsActivity;
import com.example.smoot.ajerwaojra.Models.OmraInfo;
import com.example.smoot.ajerwaojra.R;

import java.util.ArrayList;

public class RequestsAdapter  extends RecyclerView.Adapter<RequestsAdapter.MyViewHolder>{

    private ArrayList<OmraInfo> umraList;
     Context context;
  //  MyViewHolder.onCardClick2 onCardClick;
    View.OnClickListener onClickListener;

   /* public RequestsAdapter(ArrayList<OmraInfo> umraList, MyViewHolder.onCardClick2 onCardClick) {
        this.onCardClick = onCardClick;
        this.umraList = umraList;
    }*/
    public RequestsAdapter(ArrayList<OmraInfo> umraList,Context context) {
        this.context = context;
        this.umraList = umraList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView textName, date, time;
        ImageView personIcon, calIcon, verticleLine, timeIcon;
        CardView cardView;
        //onCardClick2 onCardClick;


        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            textName = itemView.findViewById(R.id.umraName);
            date = itemView.findViewById(R.id.dateTextView);
            personIcon = itemView.findViewById(R.id.personIcon);
            calIcon = itemView.findViewById(R.id.calendreIcon);
            timeIcon = itemView.findViewById(R.id.timeIcon);
            timeIcon.setVisibility(View.INVISIBLE);
            verticleLine = itemView.findViewById(R.id.verticleLineCard);
            verticleLine.setVisibility(View.INVISIBLE);
            time = itemView.findViewById(R.id.viewTime);
            time.setVisibility(View.INVISIBLE);
           // this.onCardClick=onCardClick;
           // itemView.setOnClickListener(this);
            /*textName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("stausinside adapter",date.getText()+"");
                    Intent intent = new Intent(context, OmrahDetailsActivity.class);
                    intent.putExtra("omraStatus", date.getText());
                    context.startActivity(intent);
                }
            });*/

        }
    /*    @Override
        public void onClick(View v){
            onCardClick.onCardClickLis(getAdapterPosition());


        }
        public interface onCardClick2 {
            void onCardClickLis(int position);
        }*/

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_list_of_requests, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.MyViewHolder viewHolder, int i) {
      //  viewHolder.textName.setText(umraListInProgress.get(i).getUmraName());
        final OmraInfo item = umraList.get(i);
        viewHolder.textName.setText(item.getUmraName());
        viewHolder.date.setText(item.getDate());
        if (item.getStatus().equals("1")|| item.getStatus().equals("4")){
            viewHolder.timeIcon.setVisibility(View.INVISIBLE);
            viewHolder.verticleLine.setVisibility(View.INVISIBLE);
            viewHolder.time.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.timeIcon.setVisibility(View.VISIBLE);
            viewHolder.verticleLine.setVisibility(View.VISIBLE);
            viewHolder.time.setVisibility(View.VISIBLE);
            viewHolder.time.setText(item.getTime());
            String isStartOmra = item.getIsStartOmra();
            if (isStartOmra.equalsIgnoreCase("started")){
                viewHolder.time.setTextColor(Color.parseColor("#0E9447"));
            }
        }
        viewHolder.textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("stausinside adapter",item.getStatus());
                Intent intent = new Intent(context, OmrahDetailsActivity.class);
                intent.putExtra("omraStatus", item.getStatus());

                intent.putExtra("omraName", item.getUmraName());
                intent.putExtra("doerName", item.getDoerOmraName());
                intent.putExtra("date", item.getDate());
                intent.putExtra("time", item.getTime());
                intent.putExtra("doaa", item.getDoaa());
                intent.putExtra("photos",item.getPhotos());
                Log.e("date", item.getDate());
       //         Log.e("photos", String.valueOf(item.getPhotos().size()));
                /*Log.e("photo size", String.valueOf(item.getPhotos().size()));
                for (int i = 0; i< item.getPhotos().size(); i++){
                    Log.e("photo url",item.getPhotos().get(i));
                    intent.putExtra("photos",item.getPhotos().get(i));
                }*/
                context.startActivity(intent);
            }
        });

    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return umraList.size();
    }
    public void updateData(ArrayList<OmraInfo> viewModels) {
        umraList.clear();
        umraList.addAll(viewModels);
        notifyDataSetChanged();
    }
}
