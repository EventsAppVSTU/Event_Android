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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.LinkedTreeMap;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterForEventsList extends RecyclerView.Adapter<AdapterForEventsList.EventsListViewHolder> {

    private Context context;
    private List obj;
    public AdapterForEventsList( Context Context, List Obj){
        this.context = Context;
        this.obj = Obj;
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
    public void onBindViewHolder(@NonNull AdapterForEventsList.EventsListViewHolder holder, final int position) {
        holder.bind(position);
        LinearLayout details = holder.itemView.findViewById(R.id.details);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(position);
                Event_Information_Page eventInfo = new Event_Information_Page();
                Bundle args = new Bundle();
                args.putString("name", linkedTreeMap.get("name").toString());
                eventInfo.setArguments(args);
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
//                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container, eventInfo);
                fragmentTransaction.commit();
            }
        });




    }

    @Override
    public int getItemCount() {
        return obj.size();
    }

    class EventsListViewHolder extends RecyclerView.ViewHolder {
        TextView EventName;
        TextView EventDate;
        TextView EventDescription;
        ImageView EventImage;


        public EventsListViewHolder(@NonNull View itemView) {
            super(itemView);
            EventName = itemView.findViewById(R.id.EventName);
            EventDate = itemView.findViewById(R.id.EventDate);
            EventDescription = itemView.findViewById(R.id.EventDescription);
            EventImage = itemView.findViewById(R.id.EventImage);

        }
        void bind(int Index){
            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(Index);
             EventName.setText(linkedTreeMap.get("name").toString());
             String Describe = linkedTreeMap.get("description").toString();
             String[] descr = Describe.split(" ");
             String resultDescr = "";
             if(descr.length>12){
                 for(int i = 0; i<12; i++){
                 resultDescr += descr[i]+ " ";
                 if(i==11){
                     resultDescr+="...";
                 }}
                 EventDescription.setText(resultDescr);
             }
             else {
                 EventDescription.setText(linkedTreeMap.get("description").toString());
             }
             String dateResult = "";
             String [] sDate = linkedTreeMap.get("startDate").toString().split(" ");
             String [] eDate = linkedTreeMap.get("endDate").toString().split(" ");
             dateResult += sDate[0] + " / " + eDate[0];
             EventDate.setText(dateResult);
             if(!linkedTreeMap.get("image").toString().equals("")){
             Picasso.get().load(linkedTreeMap.get("image").toString()).resize(0, 1000).into(EventImage);}

        }
    }





}
