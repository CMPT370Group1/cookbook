package com.feasymax.cookbook.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;

import java.sql.SQLException;

/**
 * Created by Kristine
 * Activity used for registration
 */

public class RegisterActivity extends ActivityMenu {

    private static final String TAG = "RegisterActivity";
    private static final String FIELD_MISSING_ERROR = "Username, password or email is missing";
    private static final String WRONG_CREDENTIALS_ERROR = "This username is already taken";

    private TextView tvRegError;
    private Button btnRegister;
    private EditText rsUserName;
    private EditText rsUserPassword;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = (Button) findViewById(R.id.buttonRegister);
        tvRegError = (TextView) findViewById(R.id.tv_reg_error);
        rsUserName = (EditText) findViewById(R.id.userName);
        rsUserPassword = (EditText) findViewById(R.id.userPassword);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        progressBar = (ProgressBar) findViewById(R.id.pb_register);

        initializeActivity();
    }

    public void initializeActivity() { super.initializeActivity(); }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void setButtons() {
        super.setButtons();

        // register the user
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                String userName = rsUserName.getText().toString();
                String password = rsUserPassword.getText().toString();
                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String mailAddress = email.getText().toString();
                boolean missingFields = false;
                if (TextUtils.isEmpty(userName)) {
                    missingFields = true;
                    rsUserName.setError(getString(R.string.username_required));
                }
                if (TextUtils.isEmpty(password)) {
                    missingFields = true;
                    rsUserPassword.setError(getString(R.string.password_required));
                }
                if (TextUtils.isEmpty(fName)) {
                    missingFields = true;
                    firstName.setError(getString(R.string.firstname_required));
                }
                if (TextUtils.isEmpty(lName)) {
                    missingFields = true;
                    lastName.setError(getString(R.string.lastname_required));
                }
                if (TextUtils.isEmpty(mailAddress)) {
                    missingFields = true;
                    email.setError(getString(R.string.email_required));
                }
                if (!missingFields) {
                    new RegisterActivity.RegisterTask().execute(userName, password, mailAddress, fName, lName);
                }

            }
        });
    }

    private class RegisterTask extends AsyncTask<String, Void, String> {
        static final String USER_EXISTS = "user_exists";
        static final String USER_REGISTERED = "user_registered";
        static final String CONNECTION_FAILED = "connection_failed";

        @Override
        protected void onPreExecute() {
            btnRegister.setVisibility(View.GONE);
            tvRegError.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String uName = params[0];
            String pw = params[1];
            String mail = params[2];
            String fName = params[3];
            String lName = params[4];
            try {
                if (Application.userRegister(uName, pw, mail, fName, lName, getApplicationContext())) {
                    return USER_REGISTERED;
                }
                return USER_EXISTS;
            } catch (SQLException sqlEx) {
                return CONNECTION_FAILED;
            }
        }

        @Override
        protected  void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            btnRegister.setVisibility(View.VISIBLE);
            if (result.equals(CONNECTION_FAILED)) {
                tvRegError.setText(getString(R.string.network_error));
                tvRegError.setVisibility(View.VISIBLE);
            }
            if (result.equals(USER_EXISTS)) {
                tvRegError.setText(getString(R.string.user_already_exists));
                tvRegError.setVisibility(View.VISIBLE);
            }
            if (result.equals(USER_REGISTERED)) {
                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
