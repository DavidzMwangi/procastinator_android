package com.example.mwang.procastinator;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mwang.procastinator.fragments.Auth.LoginFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


        changeFragment(0);
    }

    public void changeFragment(int page){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);


        switch (page){
            case 0:
                LoginFragment loginFragment=new LoginFragment();
                fragmentTransaction.replace(R.id.auth_frame,loginFragment,"Login Fragment").commit();
                break;


        }
    }
}
