package com.example.mwang.procastinator.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mwang.procastinator.R;
import com.example.mwang.procastinator.models.Event;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllEventsAdapter extends RecyclerView.Adapter<AllEventsAdapter.EventsViewHolder> {
       private Context context;
      private   List<Event> events;
        private AllEventsAdapterInterface allEventsAdapterInterface;
    public AllEventsAdapter(Context c, List<Event> events,AllEventsAdapterInterface allEventsAdapterInterface1){
        this.context=c;
        this.events=events;
        this.allEventsAdapterInterface=allEventsAdapterInterface1;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.single_event_adapter, viewGroup, false);
        return new EventsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int i) {
        final Event event=events.get(i);
        holder.eventDateTime.setText(event.event_date+ event.event_time);
        holder.reminderDateTime.setText(event.reminder_date+ event.reminder_time);


        if (event.is_complete==1){
            holder.completeBtn.setVisibility(View.GONE);
        }
        holder.completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.is_complete=1;
                event.has_update=1;
                allEventsAdapterInterface.toggleEvent(event);
            }
        });

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
        @BindView(R.id.event_time_date) TextView eventDateTime;
        @BindView(R.id.reminder_date_time) TextView reminderDateTime;
        @BindView(R.id.complete_event_btn) Button completeBtn;
        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface AllEventsAdapterInterface{

        void toggleEvent(Event event);
    }
}
