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
                    loginevent task = new loginevent();
                    //Execute the task to set Country list in Country Spinner Control
                    task.execute();
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
  //  =============================================================================
    //AysnTask class to handle Country WS call as separate UI Thread
    private class loginevent extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg = new ProgressDialog(LoginActivity.this);
            pg.setMessage("wait ... Login...");
            pg.setIndeterminate(false);
            pg.setCancelable(true);
            pg.show();
        }
        @Override
        protected Void doInBackground(String... params) {
            getjson1(username, password, "login");
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            pg.dismiss();
         //   Toast.makeText(getApplicationContext(), result1.toString(), Toast.LENGTH_LONG).show();
           try {
                if(result1.equals("0") || result1.equals("error")){
                    // username / password doesn't match
                    Toast.makeText(getApplicationContext(), "Username Or Password Is Incorrect", Toast.LENGTH_LONG).show();
                }else{
                    JSONObject json =new JSONObject(result1);
                    JSONArray userObj = json.getJSONArray("Head"); // JSON Array
                    // get first user object from JSON Array
                    JSONObject user= userObj.getJSONObject(0);
                    publicdata.username=user.getString("username");
                    // Starting MainActivity
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }


        }
        public void getjson1(String username1,String password1, String methName) {
            // Create request
            SoapObject request = new SoapObject(publicdata.NAMESPACE, methName);
            // Property which holds input parameters
            PropertyInfo param1 = new PropertyInfo();
            // Set Name
            param1.setName("username");
            // Set Value
            param1.setValue(username1);
            // Set dataType
            param1.setType(String.class);
            // Add the property to request object
            request.addProperty(param1);
            PropertyInfo param2 = new PropertyInfo();
            param2.setName("password");
            // Set Value
            param2.setValue(password1);
            // Set dataType
            param2.setType(String.class);
            // Add the property to request object
            request.addProperty(param2);
            // Create envelope
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            // Set output SOAP object
            envelope.setOutputSoapObject(request);
            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(publicdata.URL1);
            try {
                // Invole web service
                androidHttpTransport.call(publicdata.SOAP_ACTION+methName, envelope);
                // Get the response
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                // Assign it to static variable
                result1 = response.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    //==============================================================================
}
