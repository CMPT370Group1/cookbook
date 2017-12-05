package com.feasymax.cookbook.view.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.view.MainActivity;
import com.feasymax.cookbook.view.RegisterActivity;

import java.sql.SQLException;

/**
 * Created by Olya on 2017-09-21.
 * Fragment for sign in /registration (accessed in main app screen)
 */

public class AccountSignRegisterFragment extends Fragment {

    public static final String FRAGMENT_ID = "AccountSignRegisterFragment";

    private EditText rsUserName;
    private EditText rsUserPassword;
    private Button btnSignIn;
    private Button btnRegister;
    private TextView tvSigninError;
    private ProgressBar progressBar;

    /**
     * Required empty public constructor
     */
    public AccountSignRegisterFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_acc_sign_register, container, false);

        btnSignIn = (Button) view.findViewById(R.id.buttonSignin);
        btnRegister = (Button) view.findViewById(R.id.buttonRegister);
        rsUserName = (EditText) view.findViewById(R.id.userName);
        rsUserPassword = (EditText) view.findViewById(R.id.userPassword);
        tvSigninError = view.findViewById(R.id.tv_signin_error);
        progressBar = view.findViewById(R.id.pb_signin);

        // sign in
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                String userName = rsUserName.getText().toString();
                String password = rsUserPassword.getText().toString();
                boolean missingFields = false;
                if (TextUtils.isEmpty(userName)) {
                    missingFields = true;
                    rsUserName.setError(getString(R.string.username_required));
                }
                if (TextUtils.isEmpty(password)) {
                    missingFields = true;
                    rsUserPassword.setError(getString(R.string.password_required));
                }
                if (!missingFields) {
                    new SiginTask().execute(userName, password);
                }
//                if (!Application.userSignIn(rsUserName.getText().toString(),
//                        rsUserPassword.getText().toString(), getContext())) {
//                    tvSigninError.setVisibility(View.VISIBLE);
//                } else {
//                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                    startActivity(intent);
//                }
            }
        });

        // register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view ;
    }

    // AsyncTask for background process of Login
    private class SiginTask extends AsyncTask<String, Void, String> {
        static final String SIGNIN_SUCCESS = "Signin_successfull";
        static final String SIGNIN_INCORRECT = "Signin_incorrect";
        static final String SIGNIN_NETWORK_ERROR = "Signin_network_error";
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            btnSignIn.setVisibility(View.GONE);
            tvSigninError.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params) {
            String userName = params[0];
            String password = params[1];
            try {
                if (Application.userSignIn(userName, password, getContext())) {
                    return SIGNIN_SUCCESS;
                }
                return SIGNIN_INCORRECT;
            } catch (SQLException e) {
                e.printStackTrace();
                return SIGNIN_NETWORK_ERROR;
            }
        }

        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            btnSignIn.setVisibility(View.VISIBLE);
            if (result.equals(SIGNIN_SUCCESS)) {
                // Start main activity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else if (result.equals(SIGNIN_INCORRECT)) {
                // Show error text with error message
                tvSigninError.setText(getString(R.string.invalid_credentials));
                tvSigninError.setVisibility(View.VISIBLE);
            } else {
                tvSigninError.setText(getString(R.string.network_error));
                tvSigninError.setVisibility(View.VISIBLE);
            }
        }
    }
}