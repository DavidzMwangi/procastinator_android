package com.example.mwang.procastinator;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mwang.procastinator.fragments.dialogs.CollectionDateDialog;
import com.example.mwang.procastinator.fragments.dialogs.CollectionTimeDialog;
import com.example.mwang.procastinator.models.Event;
import com.example.mwang.procastinator.utils.CoreUtils;
import com.example.mwang.procastinator.views.EventViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventActivity extends AppCompatActivity  implements CollectionTimeDialog.CollectionTimeInterface,CollectionDateDialog.CollectionDateInterface {

    @BindView(R.id.date_btn) AppCompatButton dateBtn;
    @BindView(R.id.time_btn) AppCompatButton timeBtn;
    @BindView(R.id.reminder_date_btn) AppCompatButton reminderDateBtn;
    @BindView(R.id.reminder_time_btn) AppCompatButton reminderTimeBtn;
    @BindView(R.id.name) AppCompatEditText name;
    @BindView(R.id.description) AppCompatEditText description;
    private String eventTime;
    private String eventDate;
    private String reminderDate;
    private String reminderTime;
    EventViewModel eventViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Toast.makeText(getApplicationContext(),CoreUtils.idGenerator()+"",Toast.LENGTH_SHORT).show();

        eventViewModel=ViewModelProviders.of(this).get(EventViewModel.class);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                collectionTimeDialog.setArguments(bundle);
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
                collectionTimeDialog.setArguments(bundle);
                collectionTimeDialog.show(getSupportFragmentManager(),"Collection Time");
            }
        });
    }

    @Override
    public void collectionDate(String date,int opened_as) {

        if (opened_as==1){
            this.eventDate=date;
            dateBtn.setText(date);
        }else {
            this.reminderDate=date;
            reminderDateBtn.setText(date);
        }

    }

    @Override
    public void collectionTime(String time,int opened_as) {

        if (opened_as==1){
            this.eventTime=time;
            timeBtn.setText(time);
        }else {
            this.reminderTime=time;
            reminderTimeBtn.setText(time);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_event) {

            if (name.getText().toString().isEmpty()){
                name.setError("This field should not be empty");
            }else if (description.getText().toString().isEmpty()){

                description.setError("This field should not be empty");
            }else if (eventTime==null || eventDate==null){
                Toast.makeText(getApplicationContext(),"Ensure the event date and time are not empty",Toast.LENGTH_SHORT).show();
            }else if (reminderTime==null || reminderDate==null){
                Toast.makeText(getApplicationContext(),"Ensure the reminder date and time are not empty",Toast.LENGTH_SHORT).show();

            }else{


                Event newEvent=new Event();
                newEvent.setId(CoreUtils.idGenerator());
                newEvent.event_time=eventTime;
                newEvent.event_date=eventDate;
                newEvent.reminder_time=reminderTime;
                newEvent.reminder_date=reminderDate;
                newEvent.name=name.getText().toString();
                newEvent.description=description.getText().toString();
                newEvent.is_complete=0;
                newEvent.is_synced=0;
                newEvent.has_update=0;

                eventViewModel.newEventOffline(newEvent);


                Intent intent=new Intent(EventActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
