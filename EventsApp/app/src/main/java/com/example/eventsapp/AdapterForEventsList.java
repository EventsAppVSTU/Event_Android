package com.example.eventsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterForEventsList extends RecyclerView.Adapter<AdapterForEventsList.EventsListViewHolder> {

    private int count;
    private Context context;
    public AdapterForEventsList(int count, Context Context){
        this.count = count;
        this.context = Context;
    }

    @NonNull
    @Override
    public AdapterForEventsList.EventsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.events_page_element;


        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        AdapterForEventsList.EventsListViewHolder viewHolder = new AdapterForEventsList.EventsListViewHolder(view);





        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForEventsList.EventsListViewHolder holder, int position) {
        holder.bind(position);
        LinearLayout details = holder.itemView.findViewById(R.id.details);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event_Information_Page eventInfo = new Event_Information_Page();
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container, eventInfo);
                fragmentTransaction.commit();
            }
        });




    }

    @Override
    public int getItemCount() {
        return count;
    }

    class EventsListViewHolder extends RecyclerView.ViewHolder {



        public EventsListViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        void bind(int Index){

        }
    }





}
