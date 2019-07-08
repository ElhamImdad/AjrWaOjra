package com.example.smoot.ajerwaojra.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Models.OmraInfo;
import com.example.smoot.ajerwaojra.R;

import java.util.ArrayList;

public class RequestsAdapter  extends RecyclerView.Adapter<RequestsAdapter.MyViewHolder>{

    ArrayList<OmraInfo> umraListInProgress;
    Context context;
    MyViewHolder.onCardClick2 onCardClick;
    View.OnClickListener onClickListener;

    public RequestsAdapter(ArrayList<OmraInfo> umraListInProgress, MyViewHolder.onCardClick2 onCardClick) {
        this.onCardClick = onCardClick;
        this.umraListInProgress = umraListInProgress;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textName, date;
        ImageView personIcon, calIcon;
        CardView cardView;
        onCardClick2 onCardClick;


        public MyViewHolder( View itemView, onCardClick2 onCardClick) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            textName = itemView.findViewById(R.id.umraName);
            date = itemView.findViewById(R.id.dateTextView);
            personIcon = itemView.findViewById(R.id.personIcon);
            calIcon = itemView.findViewById(R.id.calendreIcon);
            this.onCardClick=onCardClick;
            itemView.setOnClickListener(this);
            /*textName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("details", textName.getText());
                    context.startActivity(intent);
                }
            });*/
        }
        @Override
        public void onClick(View v){
            onCardClick.onCardClickLis(getAdapterPosition());


        }
        public interface onCardClick2 {
            void onCardClickLis(int position);
        }

    }

    @NonNull
    @Override
    public RequestsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_list_of_requests, viewGroup, false);
        return new MyViewHolder(view, onCardClick);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.MyViewHolder viewHolder, int i) {
      //  viewHolder.textName.setText(umraListInProgress.get(i).getUmraName());
        final OmraInfo item = umraListInProgress.get(i);
        viewHolder.textName.setText(item.getUmraName());
        viewHolder.date.setText(item.getStatus());

    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return umraListInProgress.size();
    }


}
