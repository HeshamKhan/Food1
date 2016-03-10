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

public class LoginActivity extends AppCompatActivity {
    Button btnLogin,btnNewUser;
    EditText txtUsername, txtPassword;
    String username,password,result1="";
    ProgressDialog pg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // get username, Password input text
        txtUsername = (EditText) findViewById(R.id.txtusername);
        txtPassword = (EditText) findViewById(R.id.txtpassword);
        // User Login button
        btnLogin = (Button) findViewById(R.id.btnlogin);
        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                username = txtUsername.getText().toString();
                password = txtPassword.getText().toString();
                // Validate if username, password is filled
                if (username.trim().length() > 0 && password.trim().length() > 0) {
                    //loginevent task = new loginevent();
                    //Execute the task to set Country list in Country Spinner Control
                   // task.execute();
                    //Toast.makeText(getApplicationContext(), responseJSON, Toast.LENGTH_LONG).show();
                } else {
                    // user didn't entered username or password
                    Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btnnewuser = (Button) findViewById(R.id.btnnewuser);
        btnnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);

            }
        });


    }

}
