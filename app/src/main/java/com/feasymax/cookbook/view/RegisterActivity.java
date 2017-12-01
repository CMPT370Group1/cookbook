package com.feasymax.cookbook.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;

public class RegisterActivity extends ActivityMenu {

    private static final String TAG = "RegisterActivity";
    private static final String FIELD_MISSING_ERROR = "Username, password or email is missing";
    private static final String WRONG_CREDENTIALS_ERROR = "This username is already taken";

    private TextView regErrorText;
    private Button btnRegister;
    private EditText rsUserName;
    private EditText rsUserPassword;
    private EditText firstName;
    private EditText lastName;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = (Button) findViewById(R.id.buttonRegister);
        regErrorText = (TextView) findViewById(R.id.regErrorText);
        rsUserName = (EditText) findViewById(R.id.userName);
        rsUserPassword = (EditText) findViewById(R.id.userPassword);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);

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

        // register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                if (rsUserName.getText().length() == 0 || rsUserPassword.getText().length() == 0 ||
                        email.getText().length() == 0) {
                    regErrorText.setText(FIELD_MISSING_ERROR);
                    regErrorText.setVisibility(View.VISIBLE);
                }
                else {
                    if (!Application.userRegister(rsUserName.getText().toString(),
                            rsUserPassword.getText().toString(), email.getText().toString(),
                            firstName.getText().toString(), lastName.getText().toString())) {
                        regErrorText.setText(WRONG_CREDENTIALS_ERROR);
                        regErrorText.setVisibility(View.VISIBLE);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
    }
}
