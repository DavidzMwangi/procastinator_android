package com.example.mwang.procastinator.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mwang.procastinator.R;
import com.example.mwang.procastinator.models.Event;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
    public void onBindViewHolder(@NonNull final EventsViewHolder holder, final int i) {
        final Event event=events.get(i);

        //convert the date

        SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd hh:mm",Locale.ENGLISH);


        SimpleDateFormat formatter5=new SimpleDateFormat("E, MMM d yyyy HH:mm a");

        try {
            Date event_all_date=formatter2.parse(event.event_date+ " "+ event.event_time);
            Date reminder_all_date=formatter2.parse(event.event_date+ " "+ event.event_time);
            holder.eventDateTime.setText(formatter5.format(event_all_date));
            holder.reminderDateTime.setText(formatter5.format(reminder_all_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        holder.name.setText(event.name);

        if (event.is_complete==1){
            holder.completeBtn.setVisibility(View.GONE);
            holder.options.setVisibility(View.VISIBLE);
        }else{
            holder.options.setVisibility(View.GONE);
        }
        holder.completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.is_complete=1;
                event.has_update=1;
                allEventsAdapterInterface.toggleEvent(event);
            }
        });

        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,holder.options);
                popupMenu.inflate(R.menu.options_menu);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.option_delete:

                                return  true;

                            case R.id.option_edit:

                                return true;

                            default:

                                return  false;
                        }

                    }
                });

                popupMenu.show();

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
        @BindView(R.id.name) TextView name;
        @BindView(R.id.options) ImageView options;
        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface AllEventsAdapterInterface{

        void toggleEvent(Event event);
    }
}
