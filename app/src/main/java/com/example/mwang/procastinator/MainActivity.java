package com.example.mwang.procastinator;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mwang.procastinator.adapters.EventPagerAdapter;
import com.example.mwang.procastinator.fragments.home.CompleteEventsFragment;
import com.example.mwang.procastinator.fragments.home.InCompleteEventsFragment;
import com.example.mwang.procastinator.models.Event;
import com.example.mwang.procastinator.models.access.Authorization;
import com.example.mwang.procastinator.models.access.User;
import com.example.mwang.procastinator.utils.ProcastinatorRoomDatabase;
import com.example.mwang.procastinator.views.EventViewModel;
import com.example.mwang.procastinator.views.MainActivityViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EventViewModel eventViewModel;
    MainActivityViewModel mainActivityViewModel;
    Authorization authorization;
    public static MutableLiveData<Boolean> out;

    @BindView(R.id.view_pager) ViewPager mPager;
    @BindView(R.id.tab_layout) TabLayout tab_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

//        changeFragment(0);
        eventViewModel=ViewModelProviders.of(this).get(EventViewModel.class);
        mainActivityViewModel=ViewModelProviders.of(this).get(MainActivityViewModel.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,EventActivity.class);
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView=navigationView.getHeaderView(0);
        final TextView nav_user_name=(TextView) headerView.findViewById(R.id.username);
        final TextView nav_email=(TextView)headerView.findViewById(R.id.email);


        mainActivityViewModel.getAuthInfo().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {

            if (user!=null){
                nav_email.setText(user.email);
                nav_user_name.setText(user.name);
            }else{
                Log.e("user","null");
            }

            }
        });

        eventViewModel.mAuth.observe(this, new Observer<Authorization>() {
            @Override
            public void onChanged(@Nullable Authorization auth) {
                if (auth!=null){

                    //get auth data
                    mainActivityViewModel.getAuthUserOnline(auth.access_token);
                    authorization=auth;
//                    eventViewModel.getEventsOnline(authorization.access_token);
                }else{
                    Toast.makeText(getApplicationContext(),"Auth error",Toast.LENGTH_SHORT).show();
                }
            }
        });


        eventViewModel.unsyncedAndUnUpdatedEventsList.observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> events) {
                if (authorization!=null ){

//                    eventViewModel.newUpdateEventsOnline(events,authorization.access_token);


                    if (events!=null && events.size()!=0){
                        eventViewModel.newUpdateEventsOnline(events,authorization.access_token);
                    }else{
                        eventViewModel.getEventsOnline(authorization.access_token);
                    }

                    }else{

                    Toast.makeText(getApplicationContext(),"Unable to sync your events with online content",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //initiate the pager adapter
        EventPagerAdapter eventPagerAdapter=new EventPagerAdapter(getSupportFragmentManager());
        eventPagerAdapter.addFragment(new InCompleteEventsFragment(),"InComplete Events");
        eventPagerAdapter.addFragment(new CompleteEventsFragment(),"Completed Events");

        //link adapter with page layout
        tab_layout.setSelectedTabIndicatorColor(Color.parseColor("#FBC02D"));
        mPager.setAdapter(eventPagerAdapter);
        tab_layout.setupWithViewPager(mPager);



        MainActivity.out = new MutableLiveData<>();
        MainActivity.out.postValue(false);
        MainActivity.out.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean!=null && aBoolean){
                    Intent logout=new Intent(getApplicationContext(),AuthActivity.class);
                    logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(logout);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent intent=new Intent(MainActivity.this,EventActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {


            new DeleteDatabaseAsync(getApplication()).execute();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static class DeleteDatabaseAsync extends AsyncTask<Void, Void,Void>{
        private Application application;
        public DeleteDatabaseAsync(Application application1){
            this.application=application1;
        }
        @Override
        protected Void doInBackground(Void... voids) {

            ProcastinatorRoomDatabase db=ProcastinatorRoomDatabase.getDatabase(application);
            try{
                db.authorizationDao().deleteAll();
                db.eventsDao().deleteAll();
                db.usersDao().deleteAll();
                db.clearAllTables();

                MainActivity.out.postValue(true);
            }catch (Exception e){

            }


            return null;
        }
    }




}
