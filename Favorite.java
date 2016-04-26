package com.food.foodonroad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

public class Favorite extends AppCompatActivity {
    private ListView listView;
    private favoriteAdapter adapter;
    String result1="";
    ProgressDialog pg;
    public  ArrayList<favoriteclass> favlist = new ArrayList<favoriteclass>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        listView = (ListView) findViewById(R.id.listView3);
        WebService ws = new WebService();
        ws.execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final favoriteclass fo = (favoriteclass) adapter.getItem(position);
                publicdata.idrest=fo.GetIdrest();
                Intent intent = new Intent(getApplicationContext(), ViewList.class);
                startActivity(intent);
            }
        });

    }
    //AysnTask class to handle Country WS call as separate UI Thread
// =============================================================================
    private class WebService extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg = new ProgressDialog(Favorite.this);
            pg.setMessage("Please wait ...");
            pg.setIndeterminate(false);
            pg.setCancelable(true);
            pg.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            getjson1("viewfavorite",publicdata.username);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            pg.dismiss();
            //  Toast.makeText(getApplicationContext(), result1, Toast.LENGTH_LONG).show();
            try {
                if (result1.equals("0")) {
                    Toast.makeText(getApplicationContext(), "No rest Found !", Toast.LENGTH_LONG).show();

                } else if (result1.equals("error")) {

                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                } else {
                    JSONObject json = new JSONObject(result1);
                    JSONArray Obj = json.getJSONArray("Head"); // JSON Array
                    // get all object from JSON Array


                    for (int i = 0; i < Obj.length(); i++) {
                        JSONObject row = Obj.getJSONObject(i);
                        favoriteclass item = new favoriteclass();
                        item.SetIdrest(Integer.parseInt(row.getString("idrest")));
                        item.Setnamerest(row.getString("namerest"));
                        item.Setaddress( row.getString("address"));
                        favlist.add(item);
                    }
                    adapter = new favoriteAdapter(Favorite.this, favlist);
                    listView.setAdapter(adapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        public void getjson1(String p1, String p2) {
            // Create request
            SoapObject request = new SoapObject(publicdata.NAMESPACE, p1);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            PropertyInfo paramP1 = new PropertyInfo();
            // Set Name
            paramP1.setName("username");
            // Set Value
            paramP1.setValue(p2);
            // Set dataType
            paramP1.setType(String.class);
            // Add the property to request object
            request.addProperty(paramP1);
            // Set output SOAP object
            envelope.setOutputSoapObject(request);
            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(publicdata.URL1);
            try {
                // Invole web service
                androidHttpTransport.call(publicdata.SOAP_ACTION + p1, envelope);
                // Get the response
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                // Assign it to static variable
                result1 = response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
