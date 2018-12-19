package com.example.mwang.procastinator.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;


import com.example.mwang.procastinator.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CollectionDateDialog extends DialogFragment {

//    @BindView(R.id.select) AppCompatButton select_date;
//    @BindView(R.id.collection_date) DatePicker datePicker;
    private DatePicker datePicker;
    public AppCompatButton select_date;
    CollectionDateInterface collectionDateInterface;

    int openType=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.collection_date_dialog,container,false);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog d= super.onCreateDialog(savedInstanceState);
        d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return d;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            collectionDateInterface = (CollectionDateInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity " + getActivity().toString() + " must implement EnterEmailInterface");
        }


        if (getArguments() != null ) {
            openType=getArguments().getInt("date");
        }else{
            getDialog().dismiss();
        }



        datePicker =view.findViewById(R.id.collection_date);
      select_date =view.findViewById(R.id.select_date);
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day=datePicker.getDayOfMonth();
                int month=datePicker.getMonth();
                int year=datePicker.getYear();

                final String collection_date=dateConverter(day,month,year);
                switch (openType){
                    case 0:

                        //bundle not got. dismiss dialog
                        getDialog().dismiss();
                        break;

                    case 1:
                        //event date
                        collectionDateInterface.collectionDate(collection_date,1);
                        break;
                    case 2:
                        //reminder date

                        collectionDateInterface.collectionDate(collection_date,2);
                        break;

                    default:
                        getDialog().dismiss();
                        break;
                }


                getDialog().dismiss();

            }
        });

    }

    public String dateConverter(int day, int month, int year){
        SimpleDateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat dateFormatter=new SimpleDateFormat("dd-MM-yyyy");
        Date d=new Date(year-1900,month,day);
        return dateFormatter.format(d);

    }

   public interface CollectionDateInterface{
        void collectionDate(String date,int opened_as);
    }
}
