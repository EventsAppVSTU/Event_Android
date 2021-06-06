package com.example.eventsapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static java.lang.String.valueOf;

public class AdapterForMyEventsList extends RecyclerView.Adapter<AdapterForMyEventsList.MyEventsViewHolder>{

    private List<ChosenEventsData> data;
    private Context context;


    public AdapterForMyEventsList(List<ChosenEventsData> Data, Context Context){
        this.data = Data;
        this.context = Context;


    }

    @NonNull
    @Override
    public AdapterForMyEventsList.MyEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        int layoutIdForListItem = R.layout.bids_element;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        AdapterForMyEventsList.MyEventsViewHolder viewHolder = new AdapterForMyEventsList.MyEventsViewHolder(view);




        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForMyEventsList.MyEventsViewHolder holder, final int position) {
        holder.bind(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Event_Information_Page eventInfo = new Event_Information_Page();
                Bundle args = new Bundle();
                args.putString("id", valueOf(data.get(position).getEvent_id()));
                args.putInt("state", 2);
                eventInfo.setArguments(args);
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, eventInfo);
                fragmentTransaction.addToBackStack("myEventsPage").commit();
            }
        });






    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyEventsViewHolder extends RecyclerView.ViewHolder {
        TextView event_name;
        ImageView event_image;

        public MyEventsViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
            event_image = itemView.findViewById(R.id.event_image);
        }

        void bind(int Index) {
            event_name.setText(data.get(Index).getEvent_name());
            event_image.setVisibility(View.GONE);
        }
    }
}
