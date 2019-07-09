package com.example.smoot.ajerwaojra.Adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Models.UmraRequest;
import com.example.smoot.ajerwaojra.R;

import java.io.InputStream;
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
            return position;
        }

        @Override
        public int getItemCount() {
            return requestList.size();
        }
        @Override
        public int getItemViewType(int position) {
            return position;
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
            public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                }

                @Override
                protected Bitmap doInBackground(String... URL) {

                    String imageURL = URL[0];

                    Bitmap bitmap = null;
                    try {
                        // Download Image from URL
                        InputStream input = new java.net.URL(imageURL).openStream();
                        // Decode Bitmap
                        bitmap = BitmapFactory.decodeStream(input);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return bitmap;
                }

                @Override
                protected void onPostExecute(Bitmap result) {
                    // Set the bitmap into ImageView
                    // image.setImageBitmap(result);

                }
            }
        }
        // DownloadImage AsyncTask

    }
