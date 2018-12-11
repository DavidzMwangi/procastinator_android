package com.example.mwang.procastinator.fragments.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.mwang.procastinator.R;


public class CollectionTimeDialog extends DialogFragment {
       private AppCompatButton select_time;
        private TimePicker timePicker;
        CollectionTimeInterface  collectionTimeInterface;

    int openType=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.collection_time_dialog,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            collectionTimeInterface = (CollectionTimeInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity " + getActivity().toString() + " must implement EnterEmailInterface");
        }

        if (getArguments() != null ) {
            openType=getArguments().getInt("time");
        }else{
            getDialog().dismiss();
        }


        select_time=view.findViewById(R.id.select_time);
        timePicker=view.findViewById(R.id.collection_time);
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.setIs24HourView(true);
                final int hour=timePicker.getCurrentHour();
        int min=timePicker.getCurrentMinute();
//        String time=timeConverter(hour,min);
//                Toast.makeText(getContext(),timeConverter(hour,min),Toast.LENGTH_SHORT).show();


                switch (openType){
                    case 0:

                        //bundle not got. dismiss dialog
                        getDialog().dismiss();
                        break;

                    case 1:
                        //event time
                        collectionTimeInterface.collectionTime(timeConverter(hour,min),1);
                        break;
                    case 2:
                        //reminder time

                        collectionTimeInterface.collectionTime(timeConverter(hour,min),2);
                        break;

                    default:
                        getDialog().dismiss();
                        break;
                }


                getDialog().dismiss();

            }
        });
    }

    public String timeConverter(int hour, int min){
      String min_s;
        if(min<10){
            min_s="0"+min;
        }else{
            min_s=String.valueOf(min);
        }
      return hour+":"+min_s+":00";
    }

    public interface CollectionTimeInterface{
        void collectionTime(String time,int opened_as);
    }
}
