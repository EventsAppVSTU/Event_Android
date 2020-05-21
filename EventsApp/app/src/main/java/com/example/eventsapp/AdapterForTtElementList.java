package com.example.eventsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterForTtElementList extends RecyclerView.Adapter<AdapterForTtElementList.TtElementListViewHolder> {

    private int count;

    public AdapterForTtElementList(int count){
        this.count = count;

    }

    @NonNull
    @Override
    public AdapterForTtElementList.TtElementListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.time_table_element;


        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        AdapterForTtElementList.TtElementListViewHolder viewHolder = new AdapterForTtElementList.TtElementListViewHolder(view);





        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForTtElementList.TtElementListViewHolder holder, int position) {
        holder.bind(position);


    }

    @Override
    public int getItemCount() {
        return count;
    }

    class TtElementListViewHolder extends RecyclerView.ViewHolder {



        public TtElementListViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        void bind(int Index){

        }
    }
}
