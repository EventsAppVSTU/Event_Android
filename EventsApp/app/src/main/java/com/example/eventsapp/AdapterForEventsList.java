package com.example.eventsapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.internal.LinkedTreeMap;
import com.squareup.picasso.Picasso;

import java.util.List;

import static java.lang.String.valueOf;

public class AdapterForEventsList extends RecyclerView.Adapter<AdapterForEventsList.EventsListViewHolder> {

    private Context context;
    private List obj;
    private TextView headerState;
    public AdapterForEventsList( Context Context, List Obj, TextView HeaderState){
        this.context = Context;
        this.obj = Obj;
        this.headerState = HeaderState;
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
        if(headerState.getText().toString().equals("События")) {
            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(position);
                    Event_Information_Page eventInfo = new Event_Information_Page();
                    Bundle args = new Bundle();
//                    args.putString("name", linkedTreeMap.get("name").toString());
//                    args.putString("description", linkedTreeMap.get("description").toString());
//                    args.putString("image", linkedTreeMap.get("image").toString());
//                    args.putString("startDate", linkedTreeMap.get("startDate").toString());
//                    args.putString("endDate", linkedTreeMap.get("endDate").toString());
//                    args.putString("place", linkedTreeMap.get("place").toString());
                    args.putString("id", linkedTreeMap.get("id").toString());
                    args.putInt("state", 2);
                    //args.putInt("private", Integer.parseInt(linkedTreeMap.get("private").toString()));

                    eventInfo.setArguments(args);
                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_main, eventInfo);
                    fragmentTransaction.addToBackStack("tag").commit();
                }
            });
        }




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
        TextView NewsCast;
        CardView ImageHolder;
        LinearLayout details;
        TextView detailsState;
        ImageView detailsChevron;
        ImageView privateIndicator;
        ChipGroup tags;
        Chip orgName;
        Chip eventType;


        public EventsListViewHolder(@NonNull View itemView) {
            super(itemView);
            EventName = itemView.findViewById(R.id.EventName);
            EventDate = itemView.findViewById(R.id.EventDate);
            EventDescription = itemView.findViewById(R.id.EventDescription);
            EventImage = itemView.findViewById(R.id.EventImage);
            NewsCast = itemView.findViewById(R.id.NewsCast);
            ImageHolder = itemView.findViewById(R.id.ImageHolder);
            details = itemView.findViewById(R.id.details);
            detailsState = itemView.findViewById(R.id.detailsState);
            detailsChevron = itemView.findViewById(R.id.detailsChevron);
            privateIndicator = itemView.findViewById(R.id.privateIndicator);
            tags = itemView.findViewById(R.id.tags);
            orgName = itemView.findViewById(R.id.orgName);
            eventType = itemView.findViewById(R.id.eventType);


        }
        void bind(int Index){
            if(headerState.getText().toString().equals("События")) {
                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(Index);
                if (linkedTreeMap.get("private").toString().equals("1")){
                    privateIndicator.setVisibility(View.VISIBLE);
                } else privateIndicator.setVisibility(View.GONE);

                if (linkedTreeMap.get("organization_name") != null){
                    if (!linkedTreeMap.get("organization_name").equals("")){
                        orgName.setText(linkedTreeMap.get("organization_name").toString());
                        if (orgName.getVisibility() == View.GONE) orgName.setVisibility(View.VISIBLE);
                    } else orgName.setVisibility(View.GONE);
                } else orgName.setVisibility(View.GONE);

                if (linkedTreeMap.get("category_name") != null){
                    if (!linkedTreeMap.get("category_name").equals("")){
                        eventType.setText(linkedTreeMap.get("category_name").toString());
                        if (eventType.getVisibility() == View.GONE) eventType.setVisibility(View.VISIBLE);
                    } else eventType.setVisibility(View.GONE);
                } else eventType.setVisibility(View.GONE);

                EventName.setText(linkedTreeMap.get("name").toString());
                String Describe = linkedTreeMap.get("description").toString();
                String[] descr = Describe.split(" ");
                String resultDescr = "";
                if (descr.length > 12) {
                    for (int i = 0; i < 12; i++) {
                        resultDescr += descr[i] + " ";
                        if (i == 11) {
                            resultDescr += "...";
                        }
                    }
                    EventDescription.setText(resultDescr);
                } else {
                    EventDescription.setText(linkedTreeMap.get("description").toString());
                }

                DateWork dateWork = new DateWork();


                String dateResult = dateWork.getDate(linkedTreeMap.get("startDate").toString(), linkedTreeMap.get("endDate").toString());

                EventDate.setText(dateResult);
                if (!linkedTreeMap.get("image").toString().equals("")) {
                    Picasso.get().load(linkedTreeMap.get("image").toString()).resize(0, 1000).into(EventImage);
                }
            }

            else{
                final LinkedTreeMap linkedTreeMap1 = (LinkedTreeMap) obj.get(Index);
                EventName.setText(linkedTreeMap1.get("name").toString());
//                NewsCast.setVisibility(View.VISIBLE);
                EventDate.setVisibility(View.GONE);
                tags.setVisibility(View.GONE);

                String Describe = linkedTreeMap1.get("description").toString();
                String[] descr = Describe.split(" ");
                String resultDescr = "";
                if (descr.length > 19) {
                    for (int i = 0; i < 19; i++) {
                        resultDescr += descr[i] + " ";
                        if (i == 18) {
                            resultDescr += "...";
                        }
                    }
                    EventDescription.setText(resultDescr);

                } else {
                    EventDescription.setText(linkedTreeMap1.get("description").toString());
                    details.setVisibility(View.GONE);
                }

                if (!linkedTreeMap1.get("image").toString().equals("")){
                    Picasso.get().load(linkedTreeMap1.get("image").toString()).resize(0, 1000).into(EventImage);
                }
                else {
                    ImageHolder.setVisibility(View.GONE);
                }


                final String finalResultDescr = resultDescr;
                details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(detailsState.getText().toString().equals("Подробнее")) {
                            EventDescription.setText(linkedTreeMap1.get("description").toString());
                            detailsState.setText("Скрыть");
                            detailsChevron.setImageResource(R.drawable.ic_chevron_left_black_24dp);
                        }
                        else {
                            EventDescription.setText(finalResultDescr);
                            detailsState.setText("Подробнее");
                            detailsChevron.setImageResource(R.drawable.ic_chevron_right_black_24dp);
                        }

                    }
                });
            }

        }

    }





}
