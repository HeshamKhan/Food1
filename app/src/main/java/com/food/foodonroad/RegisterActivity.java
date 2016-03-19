package com.food.foodonroad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class RegisterActivity extends AppCompatActivity {
    Button btnsave,btnback;
    EditText txtUsername,txtFullName, txtPassword,txtPhone,txtEmail;
    String username,fullname, password,phone,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // get username, Password input text
        txtUsername = (EditText) findViewById(R.id.etusername);
        txtFullName = (EditText) findViewById(R.id.etfullname);
        txtPassword = (EditText) findViewById(R.id.etpassword);
        txtPhone = (EditText) findViewById(R.id.etphone);
        txtEmail = (EditText) findViewById(R.id.etemail);

        // User Login button
        btnsave = (Button) findViewById(R.id.btnsavenew);
        // Login button click event
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Get input data
                username = txtUsername.getText().toString();
                fullname = txtFullName.getText().toString();
                password = txtPassword.getText().toString();
                phone = txtPhone.getText().toString();
                email = txtEmail.getText().toString();


                // Validate if username, password is filled
                if (username.trim().length() > 0 && fullname.trim().length() > 0
                        && password.trim().length() > 0 && phone.trim().length() > 0
                        && email.trim().length() > 0) {
                  // loginevent task = new loginevent();
                    //Execute the task to set Country list in Country Spinner Control
                  //  task.execute();
                    //Toast.makeText(getApplicationContext(), responseJSON, Toast.LENGTH_LONG).show();
                } else {
                    // user didn't entered username or password
                    Toast.makeText(getApplicationContext(), "Please enter data", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnback = (Button) findViewById(R.id.btnbacknew);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        finish();
        super.onBackPressed();
    }

}
