package com.example.mwang.procastinator.views;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.mwang.procastinator.models.access.Authorization;
import com.example.mwang.procastinator.repositories.AuthorizationRepository;
import com.example.mwang.procastinator.utils.NetworkResponse;

public class AuthorizationViewModel extends AndroidViewModel {

    AuthorizationRepository authorizationRepository;
    private LiveData<Authorization> mAuth;
    public MutableLiveData<NetworkResponse> monitor;
    public AuthorizationViewModel(@NonNull Application application) {
        super(application);

        authorizationRepository=new AuthorizationRepository(application);
        mAuth=authorizationRepository.getAuth();
        monitor=authorizationRepository.monitor;
    }

    public LiveData<Authorization> getmAuth (){
        return  mAuth;
    }

    public void attemptLogin(String username,String password){
        authorizationRepository.attemptAuth(username,password);
    }

    public void attemptRegister(String name,String email, String password, String password_confirmation){

        authorizationRepository.attemptRegister(name,email,password,password_confirmation);
    }
}
