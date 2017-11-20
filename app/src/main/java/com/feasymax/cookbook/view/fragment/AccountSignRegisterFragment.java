package com.feasymax.cookbook.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.view.MainActivity;
import com.feasymax.cookbook.view.RegisterActivity;
import com.feasymax.cookbook.view.fragment.common.OnBackPressFragment;

/**
 * Created by Olya on 2017-09-21.
 */

public class AccountSignRegisterFragment extends OnBackPressFragment{

    private EditText rsUserName;
    private EditText rsUserPassword;
    private Button btnSignIn;
    private Button btnRegister;
    private TextView signInErrorText;

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

        // sign in
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
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
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        return view ;
    }
}
