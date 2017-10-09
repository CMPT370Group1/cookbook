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

import java.util.List;

/**
 * Created by Olya on 2017-09-21.
 */

public class ToolsAccountFragment extends Fragment {

    boolean isAccountEdited = false;

    EditText rsUserName;
    EditText rsUserPassword;
    Button btnSignIn;
    Button btnRegister;

    Button btnEditAccount;
    Button btnSignOut;
    EditText avUserName;
    EditText avUserEmail;
    TextView changePassword;
    EditText oldPassword;
    EditText newPassword;

    public ToolsAccountFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        if (Application.isUserSignedIn()) {
            view = inflater.inflate(R.layout.fragment_acc_view, container, false);

            btnEditAccount = view.findViewById(R.id.buttonEditAccount);
            btnSignOut = view.findViewById(R.id.buttonSignOut);
            avUserName = view.findViewById(R.id.userName);
            avUserEmail = view.findViewById(R.id.userEmail);
            changePassword = view.findViewById(R.id.textChangePassword);
            oldPassword = view.findViewById(R.id.userOldPassword);
            newPassword = view.findViewById(R.id.userNewPassword);

            avUserName.setEnabled(false);
            avUserEmail.setEnabled(false);

            btnSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Application.userSignOut();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });

            btnEditAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isAccountEdited) {
                        btnEditAccount.setText("Done");
                        avUserName.setEnabled(true);
                        avUserEmail.setEnabled(true);
                        changePassword.setVisibility(View.VISIBLE);
                        oldPassword.setVisibility(View.VISIBLE);
                        newPassword.setVisibility(View.VISIBLE);
                        isAccountEdited = true;


                        Application.userEditAccount(avUserName.getText().toString(),
                                oldPassword.getText().toString(), avUserEmail.getText().toString(),
                                newPassword.getText().toString());
                    }
                    else {
                        btnEditAccount.setText("Edit Account");
                        avUserName.setEnabled(false);
                        avUserEmail.setEnabled(false);
                        changePassword.setVisibility(View.GONE);
                        oldPassword.setVisibility(View.GONE);
                        newPassword.setVisibility(View.GONE);
                        isAccountEdited = false;
                    }
                }
            });
        }
        else {
            view = inflater.inflate(R.layout.fragment_acc_sign_register, container, false);

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
        }


        return view ;
    }
}
