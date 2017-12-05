package com.feasymax.cookbook.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

/**
 * Created by Olya on 2017-09-21.
 */

public class ToolsAccountFragment extends Fragment {

    public static final String FRAGMENT_ID = "ToolsAccountFragment";

    boolean isAccountEdited = false;

    // variables for sign/register fragment
    EditText rsUserName;
    EditText rsUserPassword;
    Button btnSignIn;
    Button btnRegister;
    TextView signInErrorText;

    // variables for account view/edit fragment
    Button btnEditAccount;
    Button btnSignOut;
    EditText avUserName;
    EditText avUserEmail;
    TextView changePassword;
    EditText oldPassword;
    EditText newPassword;
    TextView oldPassErrorText;
    TextView userTakenErrorText;

    /**
     * Required empty public constructor
     */
    public ToolsAccountFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // the layout for this fragment
        View view;

        // if user is signed in, show account's info with possibility to edit it
        if (Application.isUserSignedIn()) {
            view = inflater.inflate(R.layout.fragment_acc_view, container, false);

            btnEditAccount = view.findViewById(R.id.buttonEditAccount);
            btnSignOut = view.findViewById(R.id.buttonSignOut);
            avUserName = view.findViewById(R.id.userName);
            avUserEmail = view.findViewById(R.id.userEmail);
            changePassword = view.findViewById(R.id.textChangePassword);
            oldPassword = view.findViewById(R.id.userOldPassword);
            newPassword = view.findViewById(R.id.userNewPassword);
            oldPassErrorText = view.findViewById(R.id.oldPassErrorText);
            userTakenErrorText = view.findViewById(R.id.userTakenErrorText);

            // show user's username and email, but make it not modifiable
            avUserName.setText(Application.getUser().getUsername());
            avUserEmail.setText(Application.getUser().getEmail());
            avUserName.setEnabled(false);
            avUserEmail.setEnabled(false);

            // sign out
            btnSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Application.userSignOut(getContext());

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });

            // edit account
            btnEditAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // change layout depending if the user is viewing or modifying the account info
                    // user is modifying
                    if (!isAccountEdited) {

                        oldPassErrorText.setVisibility(View.GONE);
                        userTakenErrorText.setVisibility(View.GONE);
                        btnEditAccount.setText("Done");
                        avUserName.setEnabled(true);
                        avUserEmail.setEnabled(true);
                        changePassword.setVisibility(View.VISIBLE);
                        oldPassword.setVisibility(View.VISIBLE);
                        newPassword.setVisibility(View.VISIBLE);
                        btnSignOut.setVisibility(View.GONE);
                        isAccountEdited = true;

                    }
                    // user is viewing account info
                    else {
                        // save changes the user has made
                        String res = Application.userEditAccount(avUserName.getText().toString(),
                                avUserEmail.getText().toString(), oldPassword.getText().toString(),
                                newPassword.getText().toString());
                        Log.d("newPassword", newPassword.getText().toString());
                        Log.d("oldPassword", oldPassword.getText().toString());
                        if (!newPassword.getText().toString().isEmpty() && !res.contains("PASS")) {
                            // old password is incorrect
                            oldPassErrorText.setVisibility(View.VISIBLE);
                        }
                        if (!res.contains("USERNAME")) {
                            // username is already taken
                            userTakenErrorText.setVisibility(View.VISIBLE);
                        }

                        btnEditAccount.setText("Edit Account");
                        avUserName.setEnabled(false);
                        avUserEmail.setEnabled(false);
                        changePassword.setVisibility(View.GONE);
                        oldPassword.setVisibility(View.GONE);
                        newPassword.setVisibility(View.GONE);
                        oldPassword.setText("");
                        newPassword.setText("");
                        btnSignOut.setVisibility(View.VISIBLE);
                        isAccountEdited = false;
                    }
                }
            });
        }
        // if user is not signed in, show sign/register form
        else {
            view = inflater.inflate(R.layout.fragment_acc_sign_register, container, false);
            btnSignIn = (Button) view.findViewById(R.id.buttonSignin);
            btnRegister = (Button) view.findViewById(R.id.buttonRegister);
            rsUserName = (EditText) view.findViewById(R.id.userName);
            rsUserPassword = (EditText) view.findViewById(R.id.userPassword);
            signInErrorText = view.findViewById(R.id.signInErrorText);

            // sign in
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view3) {
                    if (!Application.userSignIn(rsUserName.getText().toString(),
                            rsUserPassword.getText().toString(), getContext())) {
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
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }


        return view ;
    }
}
