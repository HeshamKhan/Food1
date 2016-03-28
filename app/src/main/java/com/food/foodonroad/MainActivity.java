package com.food.foodonroad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner city1,city2;
    Button Button1,Button2,Button3;
    String c1,c2,result1="test";
    ProgressDialog pg;
    EditText txtrest;
    String rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtrest = (EditText) findViewById(R.id.editText);
        publicdata.restlist=new ArrayList<restclass>();
        city1= (Spinner) findViewById(R.id.spinner);
        city2= (Spinner) findViewById(R.id.spinner2);
        //-------------
        Button2 = (Button) findViewById(R.id.button2);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                rest=txtrest.getText().toString();
                if (rest.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "input Restaurant name !", Toast.LENGTH_LONG).show();
                } else {
                    WebService1 ws = new WebService1();
                    ws.execute();
                    if(publicdata.restlist.size()>0) {
                        Intent intent = new Intent(arg0.getContext(), ViewRest.class);
                        startActivity(intent);
                    }
                }
            }
        });
       //-------------------
        Button1 = (Button) findViewById(R.id.button1);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                c1 = city1.getSelectedItem().toString();
                c2 = city2.getSelectedItem().toString();
                if (c1.equals(c2)) {
                    Toast.makeText(getApplicationContext(), "Choose two different cities !", Toast.LENGTH_LONG).show();
                } else {
                    WebService ws = new WebService();
                    ws.execute();
                }
            }
        });
    }


   // =============================================================================
    //AysnTask class to handle Country WS call as separate UI Thread
   // =============================================================================
    private class WebService  extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg = new ProgressDialog(MainActivity.this);
            pg.setMessage("wait ... get data");
            pg.setIndeterminate(false);
            pg.setCancelable(true);
            pg.show();
        }
        @Override
        protected Void doInBackground(String... params) {
            getjson1("viewrest1", c1, c2);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            pg.dismiss();
          //  Toast.makeText(getApplicationContext(), result1, Toast.LENGTH_LONG).show();
           try {
                if(result1.equals("0")){
                    Toast.makeText(getApplicationContext(), "No rest Found !", Toast.LENGTH_LONG).show();
                    publicdata.restlist.clear();
                }else if(result1.equals("error")){
                    publicdata.restlist.clear();
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
                else{
                    JSONObject json =new JSONObject(result1);
                    JSONArray Obj = json.getJSONArray("Head"); // JSON Array
                    // get all object from JSON Array
                    publicdata.restlist.clear();
                    for(int i=0;i<Obj.length();i++)
                    {
                        JSONObject row= Obj.getJSONObject(i);
                        restclass rst = new restclass();
                        rst.SetIdrest(Integer.parseInt(row.getString("Idrest")));
                        rst.Setnamerest(row.getString("namerest"));
                        rst.Setcity1(row.getString("city1"));
                        rst.Setcity2(row.getString("city2"));
                        rst.Setusername(row.getString("username"));
                        rst.Setpassword(row.getString("password"));
                        rst.Setaddress(row.getString("address"));
                        rst.Setlongtude(row.getString("longtude"));
                        rst.Setlantude(row.getString("lantude"));
                        publicdata.restlist.add(rst);
                    }
                    if(publicdata.restlist!=null) {
                        if (publicdata.restlist.size() > 0) {
                            restclass ut=publicdata.restlist.get(0);
                          //  Toast.makeText(getApplicationContext(), ut.Getlongtude()+""+ut.Getlantude(), Toast.LENGTH_LONG).show();
                              Intent intent = new Intent(getApplicationContext(), ViewRest.class);
                              startActivity(intent);

                        }
                    }
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }


        }
        public void getjson1( String p1,String p2,String p3) {
            // Create request
            SoapObject request = new SoapObject(publicdata.NAMESPACE, p1);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            PropertyInfo paramPI1 = new PropertyInfo();
            // Set Name
            paramPI1.setName("c1");
            // Set Value
            paramPI1.setValue(p2);
            // Set dataType
            paramPI1.setType(String.class);
            // Add the property to request object
            request.addProperty(paramPI1);
            PropertyInfo paramPI2 = new PropertyInfo();
            paramPI2.setName("c2");
            // Set Value
            paramPI2.setValue(p3);
            // Set dataType
            paramPI2.setType(String.class);
            // Add the property to request object
            request.addProperty(paramPI2);

            // Set output SOAP object
            envelope.setOutputSoapObject(request);
            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(publicdata.URL1);
            try {
                // Invole web service
                androidHttpTransport.call(publicdata.SOAP_ACTION+p1, envelope);
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

    // =============================================================================
    //AysnTask class to handle Country WS call as separate UI Thread
    // =============================================================================
    private class WebService1  extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg = new ProgressDialog(MainActivity.this);
            pg.setMessage("wait ... get data");
            pg.setIndeterminate(false);
            pg.setCancelable(true);
            pg.show();
        }
        @Override
        protected Void doInBackground(String... params) {
            getjson1("viewrest2",rest);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            pg.dismiss();
            //  Toast.makeText(getApplicationContext(), result1, Toast.LENGTH_LONG).show();
            try {
                if(result1.equals("0")){
                    Toast.makeText(getApplicationContext(), "No rest Found !", Toast.LENGTH_LONG).show();
                    publicdata.restlist.clear();
                }else if(result1.equals("error")){
                    publicdata.restlist.clear();
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
                else{
                    JSONObject json =new JSONObject(result1);
                    JSONArray Obj = json.getJSONArray("Head"); // JSON Array
                    // get all object from JSON Array
                    publicdata.restlist.clear();
                    for(int i=0;i<Obj.length();i++)
                    {
                        JSONObject row= Obj.getJSONObject(i);
                        restclass rst = new restclass();
                        rst.SetIdrest(Integer.parseInt(row.getString("Idrest")));
                        rst.Setnamerest(row.getString("namerest"));
                        rst.Setcity1(row.getString("city1"));
                        rst.Setcity2(row.getString("city2"));
                        rst.Setusername(row.getString("username"));
                        rst.Setpassword(row.getString("password"));
                        rst.Setaddress(row.getString("address"));
                        rst.Setlongtude(row.getString("longtude"));
                        rst.Setlantude(row.getString("lantude"));
                        publicdata.restlist.add(rst);
                    }

                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }


        }
        public void getjson1( String p1,String p2) {
            // Create request
            SoapObject request = new SoapObject(publicdata.NAMESPACE, p1);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            PropertyInfo paramPI1 = new PropertyInfo();
            // Set Name
            paramPI1.setName("nr");
            // Set Value
            paramPI1.setValue(p2);
            // Set dataType
            paramPI1.setType(String.class);
            // Add the property to request object
            request.addProperty(paramPI1);

            // Set output SOAP object
            envelope.setOutputSoapObject(request);
            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(publicdata.URL1);
            try {
                // Invole web service
                androidHttpTransport.call(publicdata.SOAP_ACTION+p1, envelope);
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
