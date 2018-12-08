package com.example.mwang.procastinator.fragments.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mwang.procastinator.R;
import com.example.mwang.procastinator.adapters.AllEventsAdapter;
import com.example.mwang.procastinator.models.Event;
import com.example.mwang.procastinator.models.access.Authorization;
import com.example.mwang.procastinator.views.EventViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InCompleteEventsFragment extends Fragment {
    EventViewModel eventViewModel;
    AllEventsAdapter allEventsAdapter;
    @BindView(R.id.all_events_recycler) RecyclerView allEventsRecycler;
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
        allEventsAdapter=new AllEventsAdapter(getActivity(),new ArrayList<Event>());
        allEventsRecycler.setAdapter(allEventsAdapter);


        eventViewModel=ViewModelProviders.of(this).get(EventViewModel.class);
       updateEventsData();
       eventViewModel.mAuth.observe(this, new Observer<Authorization>() {
           @Override
           public void onChanged(@Nullable Authorization authorization) {

               if (authorization!=null){
                   eventViewModel.getEventsOnline(authorization.access_token);
                   updateEventsData();
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
}
