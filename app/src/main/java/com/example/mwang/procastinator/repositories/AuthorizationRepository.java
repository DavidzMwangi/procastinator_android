package com.example.mwang.procastinator.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.mwang.procastinator.dao.AuthorizationDao;
import com.example.mwang.procastinator.models.access.Authorization;
import com.example.mwang.procastinator.services.AuthService;
import com.example.mwang.procastinator.utils.CoreUtils;
import com.example.mwang.procastinator.utils.NetworkResponse;
import com.example.mwang.procastinator.utils.ProcastinatorRoomDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class AuthorizationRepository {

    AuthorizationDao authorizationDao;
    private LiveData<Authorization> mAuth;
    public MutableLiveData<NetworkResponse> monitor;
    public AuthorizationRepository(Application application){

        ProcastinatorRoomDatabase procastinatorRoomDatabase=ProcastinatorRoomDatabase.getDatabase(application);
        authorizationDao=procastinatorRoomDatabase.authorizationDao();
        mAuth=authorizationDao.getAuthorization();
        monitor = new MutableLiveData<>();

    }
    public LiveData<Authorization> getAuth() {
        return mAuth;
    }


    public void attemptRegister(String email,String password,String password_confirmation){
        monitor.setValue(new NetworkResponse(true));
        Call<Authorization> call=CoreUtils.getRetrofitClient().create(AuthService.class).tryRegister(email,password,password_confirmation);
        call.enqueue(new Callback<Authorization>() {
            @Override
            public void onResponse(Call<Authorization> call, Response<Authorization> response) {
                if(response.code()==422 || response.code()==500){
                    monitor.postValue(new NetworkResponse(false,"Invalid user credentials",response.code()));
                }else{
                    monitor.postValue(new NetworkResponse(false,"successful",1));
                }
                if (response.body()!=null){
                    authorizationDao.insert(response.body());
                }


            }

            @Override
            public void onFailure(Call<Authorization> call, Throwable t) {

                try{
                    monitor.postValue(new NetworkResponse(false,"Check your internet connection then try again",((HttpException) t).code()));
                }catch (Exception e){
                    monitor.postValue(new NetworkResponse(false,"Check your internet connection then try again",0));
                }
            }
        });
    }


    public void attemptAuth(String username,String password){
        monitor.setValue(new NetworkResponse(true));
        Call<Authorization> call=CoreUtils.getRetrofitClient().create(AuthService.class).tryLogin(username,password);
        call.enqueue(new Callback<Authorization>() {
            @Override
            public void onResponse(Call<Authorization> call, Response<Authorization> response) {
                if(response.code()==401){
                    monitor.postValue(new NetworkResponse(false,"Your credentials did not match our records",response.code()));
                }else if(response.code()==422 || response.code()==500){
                    monitor.postValue(new NetworkResponse(false,"Invalid credentials",response.code()));
                }else{
                    monitor.postValue(new NetworkResponse(false,"",1));
                }
                if (response.body()!=null){

                    authorizationDao.insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<Authorization> call, Throwable t) {
                try{
                    monitor.postValue(new NetworkResponse(false,"Check your internet connection then try again",((HttpException) t).code()));
                }catch (Exception e){
                    monitor.postValue(new NetworkResponse(false,"Check your internet connection then try again",0));
                }
            }
        });
    }

}
