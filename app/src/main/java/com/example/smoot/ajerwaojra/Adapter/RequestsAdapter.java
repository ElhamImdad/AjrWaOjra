package com.example.smoot.ajerwaojra.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Fragments.RequestDetailsFragment;
import com.example.smoot.ajerwaojra.Models.OmraInfo;
import com.example.smoot.ajerwaojra.R;

import java.util.ArrayList;

public class RequestsAdapter  extends RecyclerView.Adapter<RequestsAdapter.MyViewHolder>{

    ArrayList<OmraInfo> umraListInProgress;
    Context c;

    public RequestsAdapter(Context context, ArrayList<OmraInfo> umraListInProgress) {
        this.c=context;
        this.umraListInProgress = umraListInProgress;
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView textName;
        TextView date;
        ImageView personIcon;
        ImageView calIcon;
        CardView cardView;


        public MyViewHolder( View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            textName = itemView.findViewById(R.id.umraName);
            date = itemView.findViewById(R.id.dateTextView);
            personIcon = itemView.findViewById(R.id.personIcon);
            calIcon = itemView.findViewById(R.id.calendreIcon);
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
      //  viewHolder.textName.setText(umraListInProgress.get(i).getUmraName());
        final OmraInfo item = umraListInProgress.get(i);
        viewHolder.textName.setText(item.getUmraName());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
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
        return umraListInProgress.size();
    }

    private void switchFragment(OmraInfo omraInfo){
        Fragment mFragment = new RequestDetailsFragment();
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("item_selected_key", (Parcelable) omraInfo);
        mFragment.setArguments(mBundle);

        switchContent(R.id.container, mFragment);

    }
    public void switchContent(int id, Fragment fragment) {

    }

}
