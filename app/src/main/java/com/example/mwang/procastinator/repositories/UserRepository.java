package com.example.mwang.procastinator.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.mwang.procastinator.dao.UserDao;
import com.example.mwang.procastinator.models.access.User;
import com.example.mwang.procastinator.utils.NetworkResponse;

import java.util.List;

public class UserRepository {


    UserDao userDao;
    private LiveData<User> authUser;
    public MutableLiveData<NetworkResponse> monitor;
    public UserRepository(Application application){



    }
}
