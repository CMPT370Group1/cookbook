package com.olya.cookbook.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.olya.cookbook.R;
import com.olya.cookbook.model.Application;
import com.olya.cookbook.view.MainActivity;

import static com.olya.cookbook.R.id.regErrorText;
import static com.olya.cookbook.R.id.signInErrorText;

/**
 * Created by Olya on 2017-09-21.
 */

public class AccountSignRegisterFragment extends Fragment{

    private EditText rsUserName;
    private EditText rsUserPassword;
    private Button btnSignIn;
    private Button btnRegister;
    private TextView signInErrorText;
    private TextView regErrorText;

    public AccountSignRegisterFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_acc_sign_register, container, false);

        btnSignIn = (Button) view.findViewById(R.id.buttonSignin);
        btnRegister = (Button) view.findViewById(R.id.buttonRegister);
        rsUserName = (EditText) view.findViewById(R.id.userName);
        rsUserPassword = (EditText) view.findViewById(R.id.userPassword);
        signInErrorText = view.findViewById(R.id.signInErrorText);
        regErrorText = view.findViewById(R.id.regErrorText);

        // sign in
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                regErrorText.setVisibility(View.GONE);
                if (!Application.userSignIn(rsUserName.getText().toString(),
                        rsUserPassword.getText().toString())) {
                    signInErrorText.setVisibility(View.VISIBLE);
                } else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        // register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                signInErrorText.setVisibility(View.GONE);
                if (!Application.userRegister(rsUserName.getText().toString(),
                        rsUserPassword.getText().toString())) {
                    regErrorText.setVisibility(View.VISIBLE);
                } else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view ;
    }
}
