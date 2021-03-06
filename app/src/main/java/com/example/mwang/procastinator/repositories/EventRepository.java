package com.example.mwang.procastinator.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mwang.procastinator.dao.AuthorizationDao;
import com.example.mwang.procastinator.dao.EventsDao;
import com.example.mwang.procastinator.models.Event;
import com.example.mwang.procastinator.models.access.Authorization;
import com.example.mwang.procastinator.services.EventService;
import com.example.mwang.procastinator.utils.CoreUtils;
import com.example.mwang.procastinator.utils.NetworkResponse;
import com.example.mwang.procastinator.utils.ProcastinatorRoomDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class EventRepository {
    EventsDao eventsDao;
   private LiveData<List<Event>> inCompleteEventsList;
   private LiveData<List<Event>> completeEventsList;
    public MutableLiveData<NetworkResponse> monitor;
    private LiveData<List<Event>> getUnsyncedAndUnUpdatedEvents;



    public EventRepository(Application application){
        ProcastinatorRoomDatabase procastinatorRoomDatabase=ProcastinatorRoomDatabase.getDatabase(application);
        eventsDao=procastinatorRoomDatabase.eventsDao();
        inCompleteEventsList=eventsDao.getAllEvents(0);
        completeEventsList=eventsDao.getAllEvents(1);
        getUnsyncedAndUnUpdatedEvents=eventsDao.getUnsyncedAndUnUpdatedEvents(0,1);
        monitor=new MutableLiveData<>();

    }
    
    public LiveData<List<Event>> allInCompleteEvents(){
        return  inCompleteEventsList;
    }
    public LiveData<List<Event>> allCompleteEvents(){
        return  completeEventsList;
    }

    public LiveData<List<Event>> unsyncedAndUnUpdatedEvents(){
        return  getUnsyncedAndUnUpdatedEvents;
    }



    public void getEventsOnline(String token) {
        Call<List<Event>> call=CoreUtils.getAuthRetrofitClient(token).create(EventService.class).getAllEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {

                monitor.postValue(new NetworkResponse(false,"Events synced with online data",response.code()));

                if (response.body()!=null){

                    for (Event event:response.body()){
                        eventsDao.insert(event);


                    }
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {


                try{
                    monitor.postValue(new NetworkResponse(false,"Check your internet connection for the information to be updated online",((HttpException) t).code()));
                }catch (Exception e){
                    monitor.postValue(new NetworkResponse(false,"Check your internet connection for the information to be updated online",0));
                }
            }
        });

    }

    public void toggleEventStatusOnline(String token,  int event_id){

        Call<Event> call=CoreUtils.getAuthRetrofitClient(token).create(EventService.class).toggleEventStatus(event_id);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                monitor.postValue(new NetworkResponse(false,"Events changed online",response.code()));

                if (response.body()!=null){
                    eventsDao.insert(response.body());

                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                try{
                    monitor.postValue(new NetworkResponse(false,"Check your internet connection for the information to be updated online",((HttpException) t).code()));
                }catch (Exception e){
                    monitor.postValue(new NetworkResponse(false,"Check your internet connection for the information to be updated online",0));
                }
            }
        });
    }

    public void changeEventStatus(Event event) {

        new ChangeEventAsync(eventsDao).execute(event);


    }

    public void newUpdateEventsOnline(List<Event> eventUpdateList, final String access_token){
        monitor.setValue(new NetworkResponse(true));
        JSONArray array = new JSONArray();
        try {
            for (Event event : eventUpdateList) {
                array.put(event.getJsonObject());

            }
        } catch (JSONException e) {
            Log.e("JSON", e.getMessage());
        }

            Call<JSONObject> call=CoreUtils.getAuthRetrofitClient(access_token).create(EventService.class).updateEvents(array);
            call.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    monitor.postValue(new NetworkResponse(false,"Events updated online",response.code()));


                    //delete the records that are unsynced and un updated as they have already been updated here and get the updated data online

                    eventsDao.deleteSyncedAndUnUpdated(0,1);
                    //get the data online
                    getEventsOnline(access_token);
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {

                    try{
                        monitor.postValue(new NetworkResponse(false,"Check your internet connection for the information to be updated online",((HttpException) t).code()));
                    }catch (Exception e){
                        monitor.postValue(new NetworkResponse(false,"Check your internet connection for the information to be updated online",0));
                    }
                }
            });



    }

    public void newEventOffline(Event newEvent) {
        new ChangeEventAsync(eventsDao).execute(newEvent);
    }

    public void deleteEventLocally(int id) {

            new DeleteEventLocallyAsync(eventsDao).execute(id);
    }

    public void deleteEventOnLine(String token, final int event_ider) {
        Call<List<Event>> call=CoreUtils.getAuthRetrofitClient(token).create(EventService.class).deleteEvent(event_ider);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                monitor.postValue(new NetworkResponse(false,"Events deleted online",response.code()));

                //delete the event locally
                eventsDao.deleteUsingId(event_ider);

                //update the events from online server
                if (response.body()!=null) {
                    for (Event event : response.body()) {
                        eventsDao.insert(event);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                try{
                    monitor.postValue(new NetworkResponse(false,"Check your internet connection for the information to be updated online",((HttpException) t).code()));
                }catch (Exception e){
                    monitor.postValue(new NetworkResponse(false,"Check your internet connection for the information to be updated online",0));
                }
            }
        });

    }

    private static class ChangeEventAsync extends AsyncTask<Event,Void,Void>{

        private EventsDao eventsDao;

        public ChangeEventAsync(EventsDao eventsDao1){
            this.eventsDao=eventsDao1;

        }
        @Override
        protected Void doInBackground(Event... events) {
            eventsDao.insert(events[0]);
            return null;
        }
    }


    private static class DeleteEventLocallyAsync extends AsyncTask<Integer,Void,Void>{

        EventsDao eventsDao;
        public DeleteEventLocallyAsync(EventsDao eventsDao1){
            this.eventsDao=eventsDao1;
        }
        @Override
        protected Void doInBackground(Integer... integers) {

            eventsDao.deleteUsingId(integers[0]);
            return null;
        }
    }

}
