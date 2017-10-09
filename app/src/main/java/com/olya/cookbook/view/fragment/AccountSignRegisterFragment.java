package com.olya.cookbook.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.olya.cookbook.R;
import com.olya.cookbook.model.Application;
import com.olya.cookbook.view.MainActivity;
import com.olya.cookbook.view.RecipesActivity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Olya on 2017-09-21.
 */

public class AccountSignRegisterFragment extends Fragment{

    private EditText rsUserName;
    private EditText rsUserPassword;
    private Button btnSignIn;
    private Button btnRegister;

    public AccountSignRegisterFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_acc_sign_register, container, false);

        btnSignIn = (Button) view.findViewById(R.id.buttonSignin);
        btnRegister = (Button) view.findViewById(R.id.buttonRegister);
        rsUserName = (EditText) view.findViewById(R.id.userName);
        rsUserPassword = (EditText) view.findViewById(R.id.userPassword);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Application.userSignIn(rsUserName.getText().toString(),
                        rsUserPassword.getText().toString());

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Application.userRegister(rsUserName.getText().toString(),
                        rsUserPassword.getText().toString());

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view ;
    }
}
