package com.example.mwang.procastinator.fragments.Auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mwang.procastinator.R;

public class LoginFragment extends Fragment {

    LoginFragmentInterface loginFragmentInterface;
    TextView signUpChanger;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            loginFragmentInterface = (LoginFragmentInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity " + getActivity().toString() + " must implement EnterEmailInterface");
        }
        signUpChanger=(TextView)view.findViewById(R.id.sign_up_changer);
        signUpChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFragmentInterface.changePage(1);
            }
        });

    }

    public interface LoginFragmentInterface{
         void changePage(int page);
    }
}
