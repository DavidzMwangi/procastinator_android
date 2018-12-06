package com.example.mwang.procastinator;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mwang.procastinator.fragments.Auth.LoginFragment;
import com.example.mwang.procastinator.fragments.Auth.RegisterFragment;
import com.example.mwang.procastinator.models.access.Authorization;
import com.example.mwang.procastinator.views.AuthorizationViewModel;

public class AuthActivity extends AppCompatActivity implements LoginFragment.LoginFragmentInterface,RegisterFragment.RegisterFragmentInterface {

    AuthorizationViewModel authorizationViewModel;
    Authorization authorization;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


        authorizationViewModel=ViewModelProviders.of(this).get(AuthorizationViewModel.class);
        authorizationViewModel.getmAuth().observe(this, new Observer<Authorization>() {
            @Override
            public void onChanged(@Nullable Authorization auth) {
                if (auth!=null){
                    authorization=auth;
                    //change activity to go main activity

                    Intent intent=new Intent(AuthActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{
                    changeFragment(0);
                }
            }
        });

    }

    public void changeFragment(int page){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);


        switch (page){
            case 0:
                LoginFragment loginFragment=new LoginFragment();
                fragmentTransaction.replace(R.id.auth_frame,loginFragment,"Login Fragment").commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.auth_frame,new RegisterFragment(),"Register Fragment").commit();
                break;


        }
    }

    @Override
    public void changePage(int page) {
        changeFragment(page);
    }
}
