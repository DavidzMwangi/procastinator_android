package com.example.mwang.procastinator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mwang.procastinator.fragments.dialogs.CollectionDateDialog;
import com.example.mwang.procastinator.fragments.dialogs.CollectionTimeDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventActivity extends AppCompatActivity  implements CollectionTimeDialog.CollectionTimeInterface,CollectionDateDialog.CollectionDateInterface {

    @BindView(R.id.date_btn) AppCompatButton dateBtn;
    @BindView(R.id.time_btn) AppCompatButton timeBtn;
    @BindView(R.id.reminder_date_btn) AppCompatButton reminderDateBtn;
    @BindView(R.id.reminder_time_btn) AppCompatButton reminderTimeBtn;
    private String time;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                CollectionDateDialog collectionDateDialog=new CollectionDateDialog();
                collectionDateDialog.show(getChildFragmentManager(),"Collection Date");
                 */

                CollectionDateDialog collectionDateDialog=new CollectionDateDialog();
                Bundle bundle=new Bundle();
                bundle.putInt("date",1);
                collectionDateDialog.setArguments(bundle);
                collectionDateDialog.show(getSupportFragmentManager(),"Collection Date");
            }
        });


        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CollectionTimeDialog collectionTimeDialog=new CollectionTimeDialog();
                Bundle bundle=new Bundle();
                bundle.putInt("time",1);
                collectionTimeDialog.show(getSupportFragmentManager(),"Collection Time");
            }
        });


        reminderDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CollectionDateDialog collectionDateDialog=new CollectionDateDialog();
                Bundle bundle=new Bundle();
                bundle.putInt("date",2);
                collectionDateDialog.setArguments(bundle);
                collectionDateDialog.show(getSupportFragmentManager(),"Collection Date");
            }
        });


        reminderTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CollectionTimeDialog collectionTimeDialog=new CollectionTimeDialog();
                Bundle bundle=new Bundle();
                bundle.putInt("time",2);
                collectionTimeDialog.show(getSupportFragmentManager(),"Collection Time");
            }
        });
    }

    @Override
    public void collectionDate(String date,int opened_as) {
        this.date=date;
        if (opened_as==1){
            dateBtn.setText(date);
        }else {
            reminderDateBtn.setText(date);
        }

    }

    @Override
    public void collectionTime(String time,int opened_as) {
        this.time=time;
        if (opened_as==1){
            timeBtn.setText(time);
        }else {
            reminderTimeBtn.setText(time);
        }

    }
}
