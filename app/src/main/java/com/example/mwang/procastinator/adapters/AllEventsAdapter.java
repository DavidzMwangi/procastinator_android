package com.example.mwang.procastinator.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mwang.procastinator.R;
import com.example.mwang.procastinator.models.Event;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllEventsAdapter extends RecyclerView.Adapter<AllEventsAdapter.EventsViewHolder> {
       private Context context;
      private   List<Event> events;

    public AllEventsAdapter(Context c, List<Event> events){
        this.context=c;
        this.events=events;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.single_event_adapter, viewGroup, false);
        return new EventsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder eventsViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void updateData(List<Event> updatedEvents){
        this.events=updatedEvents;
        this.notifyDataSetChanged();
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder{
//        @BindView(R.id.)
        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
