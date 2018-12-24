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

public class LoginFragment extends Fragment {


    AuthorizationViewModel authorizationViewModel;
    LoginFragmentInterface loginFragmentInterface;

    @BindView(R.id.sign_up_changer) TextView signUpChanger;
    @BindView(R.id.user_name_edit_text) EditText userNameEditText;
    @BindView(R.id.password_edit_text) EditText passwordEditText;
    @BindView(R.id.sign_btn) Button signBtn;
    @BindView(R.id.login_pr) ProgressBar loginPr;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ViewGroup auth_view = (ViewGroup) inflater.inflate(
                R.layout.login_fragment, container, false
        );
        ButterKnife.bind(this, auth_view);
        return auth_view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authorizationViewModel=ViewModelProviders.of(this).get(AuthorizationViewModel.class);
        try {
            loginFragmentInterface = (LoginFragmentInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity " + getActivity().toString() + " must implement EnterEmailInterface");
        }
        signUpChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFragmentInterface.changePage(1);
            }
        });

        authorizationViewModel.monitor.observe(this, new Observer<NetworkResponse>() {
            @Override
            public void onChanged(@Nullable NetworkResponse networkResponse) {
                if(networkResponse==null)
                    return;
                if(networkResponse.is_loading){
                    loginPr.setVisibility(View.VISIBLE);
                }else{
                    loginPr.setVisibility(View.GONE);
                }

                if(networkResponse.message!=null && !networkResponse.message.equals(""))
                    Snackbar.make(loginPr, networkResponse.message , Snackbar.LENGTH_LONG).show();
            }
        });
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userNameEditText.getText().toString().equals("")){
                    userNameEditText.setError("This field cannot be empty");

                }else
                if (passwordEditText.getText().toString().equals("")){
                    passwordEditText.setError("This field cannot be empty");

                }else{
                    //attempt login
                    authorizationViewModel.attemptLogin(userNameEditText.getText().toString(),passwordEditText.getText().toString());

                }


            }
        });
    }

    public interface LoginFragmentInterface{
         void changePage(int page);
    }
}
