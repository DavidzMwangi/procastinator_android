package com.example.mwang.procastinator.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.mwang.procastinator.dao.UserDao;
import com.example.mwang.procastinator.models.access.User;
import com.example.mwang.procastinator.services.AuthService;
import com.example.mwang.procastinator.utils.CoreUtils;
import com.example.mwang.procastinator.utils.NetworkResponse;
import com.example.mwang.procastinator.utils.ProcastinatorRoomDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {


    UserDao userDao;
    private LiveData<User> authUser;
    public MutableLiveData<NetworkResponse> monitor;
    public UserRepository(Application application){

        ProcastinatorRoomDatabase procastinatorRoomDatabase=ProcastinatorRoomDatabase.getDatabase(application);
        userDao=procastinatorRoomDatabase.usersDao();
        authUser=userDao.getAuthUser();
        monitor=new MutableLiveData<>();

    }


    public LiveData<User> getAuthUser(){
        return authUser;
    }

    public void getAuthUserOnline(String token){
        Call<User> call=CoreUtils.getAuthRetrofitClient(token).create(AuthService.class).getAuthUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body()!=null){
                    userDao.insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
