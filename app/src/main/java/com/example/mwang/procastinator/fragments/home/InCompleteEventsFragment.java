package com.example.mwang.procastinator.fragments.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.mwang.procastinator.R;
import com.example.mwang.procastinator.adapters.AllEventsAdapter;
import com.example.mwang.procastinator.models.Event;
import com.example.mwang.procastinator.models.access.Authorization;
import com.example.mwang.procastinator.views.EventViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InCompleteEventsFragment extends Fragment implements AllEventsAdapter.AllEventsAdapterInterface {
    EventViewModel eventViewModel;
    AllEventsAdapter allEventsAdapter;
    Authorization authorization;
    @BindView(R.id.all_events_recycler) RecyclerView allEventsRecycler;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipe_refresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup auth_view = (ViewGroup) inflater.inflate(
                R.layout.incomplete_events_fragment, container, false
        );
        ButterKnife.bind(this, auth_view);
        return auth_view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        allEventsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        allEventsAdapter=new AllEventsAdapter(getActivity(),new ArrayList<Event>(),this);
        allEventsRecycler.setAdapter(allEventsAdapter);


        eventViewModel=ViewModelProviders.of(this).get(EventViewModel.class);


        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(authorization!=null){

                    //load the data online when the error is resolved

                    eventViewModel.unsyncedAndUnUpdatedEventsList.observe(getActivity(), new Observer<List<Event>>() {
                        @Override
                        public void onChanged(@Nullable List<Event> events) {
                            if (authorization!=null ){

                                if (events!=null && events.size()!=0){
                                    eventViewModel.newUpdateEventsOnline(events,authorization.access_token);
                                }else{
                                    eventViewModel.getEventsOnline(authorization.access_token);
                                }

                            }else{

                                Toast.makeText(getActivity(),"Unable to sync your events with online content",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });



        eventViewModel.inCompleteEventsList.observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> events) {
                if (events!=null){
                    allEventsAdapter.updateData(events);
                }

            }
        });

    }


    public void updateEventsData(){

        eventViewModel.inCompleteEventsList.observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> events) {
                if (events!=null){
                    allEventsAdapter.updateData(events);
                }

            }
        });


    }

    @Override
    public void toggleEvent(final Event event) {
        eventViewModel.changeEventStatus( event);
        eventViewModel.mAuth.observe(this, new Observer<Authorization>() {
            @Override
            public void onChanged(@Nullable Authorization authorization) {
                if (authorization!=null) {
                   eventViewModel.toggleEventStatusOnline(authorization.access_token,event.getId());
                }
            }
        });

    }

    @Override
    public void deleteEvent(Event event) {

    }
}
