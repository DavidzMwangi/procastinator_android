package com.example.mwang.procastinator.fragments.Auth;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mwang.procastinator.R;
import com.example.mwang.procastinator.utils.NetworkResponse;
import com.example.mwang.procastinator.views.AuthorizationViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterFragment extends Fragment {

    RegisterFragmentInterface registerFragmentInterface;
    AuthorizationViewModel authorizationViewModel;
    @BindView(R.id.sign_in_changer)  TextView signInChanger;
    @BindView(R.id.user_name_edit_text) EditText username;
    @BindView(R.id.password_edit_text) EditText password;
    @BindView(R.id.name_edit_text) EditText name_edit_text;
    @BindView(R.id.password_confirm_edit_text) EditText passwordConfirm;
    @BindView(R.id.sign_btn) Button signBtn;
    @BindView(R.id.register_btn) ProgressBar registerPr;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup auth_view = (ViewGroup) inflater.inflate(
                R.layout.register_fragment, container, false
        );
        ButterKnife.bind(this, auth_view);
        return auth_view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authorizationViewModel=ViewModelProviders.of(this).get(AuthorizationViewModel.class);


        try {
            registerFragmentInterface = (RegisterFragmentInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity " + getActivity().toString() + " must implement EnterEmailInterface");
        }
        signInChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFragmentInterface.changePage(0);
            }
        });

        authorizationViewModel.monitor.observe(this, new Observer<NetworkResponse>() {
            @Override
            public void onChanged(@Nullable NetworkResponse networkResponse) {
                if(networkResponse==null)
                    return;
                if(networkResponse.is_loading){
                    registerPr.setVisibility(View.VISIBLE);
                }else{
                    registerPr.setVisibility(View.GONE);
                }

                if(networkResponse.message!=null && !networkResponse.message.equals(""))
                    Snackbar.make(registerPr, networkResponse.message , Snackbar.LENGTH_LONG).show();
            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")){
                    username.setError("This field cannot be empty");

                }else
                if (password.getText().toString().equals("")){
                    password.setError("This field cannot be empty");

                }else if(passwordConfirm.getText().toString().equals("")){
                    passwordConfirm.setError("This field cannot be empty");
                }else if( !password.getText().toString().equals(passwordConfirm.getText().toString())){
                    Snackbar.make(password,"Password confirmation does not match" , Snackbar.LENGTH_LONG).show();
                }
                else{
                    //attempt login
                    authorizationViewModel.attemptRegister(name_edit_text.getText().toString(),username.getText().toString(),password.getText().toString(),passwordConfirm.getText().toString());

                }
            }
        });
    }

    public interface RegisterFragmentInterface{
         void changePage(int page);
    }
}
