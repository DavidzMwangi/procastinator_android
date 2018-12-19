package com.example.mwang.procastinator.views;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.mwang.procastinator.models.access.User;
import com.example.mwang.procastinator.repositories.UserRepository;
import com.example.mwang.procastinator.utils.NetworkResponse;

public class MainActivityViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    public MutableLiveData<NetworkResponse> monitor;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        userRepository=new UserRepository(application);
        monitor=userRepository.monitor;
    }

    public void getAuthUserOnline(String token){
        userRepository.getAuthUserOnline(token);
    }

    public LiveData<User> getAuthInfo(){
        return   userRepository.getAuthUser();
    }
}
