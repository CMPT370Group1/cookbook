package com.feasymax.cookbook.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.view.tab.ToolsAdapter;

import static com.feasymax.cookbook.R.id.signInErrorText;
import static com.feasymax.cookbook.R.id.textEmail;

public class RegisterActivity extends ActivityMenu {

    private static final String TAG = "RegisterActivity";

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
                if (!Application.userRegister(rsUserName.getText().toString(),
                        rsUserPassword.getText().toString(), email.getText().toString(),
                        firstName.getText().toString(), lastName.getText().toString())) {
                    regErrorText.setVisibility(View.VISIBLE);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
