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

public class RegisterFragment extends Fragment {

    RegisterFragmentInterface registerFragmentInterface;
    TextView signInChanger;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return  inflater.inflate(R.layout.register_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            registerFragmentInterface = (RegisterFragmentInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity " + getActivity().toString() + " must implement EnterEmailInterface");
        }
        signInChanger=(TextView) view.findViewById(R.id.sign_in_changer);
        signInChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFragmentInterface.changePage(0);
            }
        });
    }

    public interface RegisterFragmentInterface{
         void changePage(int page);
    }
}
