package com.example.eventsapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.LinkedTreeMap;
import com.squareup.picasso.Picasso;

import java.util.List;

import static java.lang.String.valueOf;

public class AdapterForBidsPage extends RecyclerView.Adapter<AdapterForBidsPage.BidsViewHolder>{

    private List<EventServerData> data;
    private Context context;
    private boolean state;

    public AdapterForBidsPage(List<EventServerData> Data, Context Context, boolean State){
        this.data = Data;
        this.context = Context;
        this.state = State;

    }

    @NonNull
    @Override
    public AdapterForBidsPage.BidsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        int layoutIdForListItem = R.layout.bids_element;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        AdapterForBidsPage.BidsViewHolder viewHolder = new AdapterForBidsPage.BidsViewHolder(view);




        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForBidsPage.BidsViewHolder holder, final int position) {
        holder.bind(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Event_Information_Page eventInfo = new Event_Information_Page();
                Bundle args = new Bundle();
                args.putString("name", data.get(position).getName());
                args.putString("description", data.get(position).getDescription());
                args.putString("image", data.get(position).getImage());
                args.putString("startDate", data.get(position).getStartDate());
                args.putString("endDate", data.get(position).getEndDate());
                args.putString("place", data.get(position).getPlace());
                args.putString("id", valueOf(data.get(position).getId()));
                args.putInt("state", (state ? 1 : 0));
                args.putInt("private", 2);
                eventInfo.setArguments(args);
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, eventInfo);
                fragmentTransaction.addToBackStack("bidsPage").commit();
            }
        });






    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class BidsViewHolder extends RecyclerView.ViewHolder {
        TextView event_name;
        ImageView event_image;
        public BidsViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
            event_image = itemView.findViewById(R.id.event_image);
        }
        void bind(int Index){
            event_name.setText(data.get(Index).getName());
            Picasso.get().load(data.get(Index).getImage()).resize(0, 1000).into(event_image);
        }
    }
}
