package com.food.foodonroad;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;

public class ViewList extends AppCompatActivity {
    public static List<foodclass> FoodList = new ArrayList<foodclass>();
    Button Button3,Button4,Button1,Button2;
    private ListView listView;
    private FoodAdapter adapter;
    ProgressDialog pg;
    String result1 = "test";
    private RatingBar ratingBar;
    TextView nrest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list2);
        listView = (ListView) findViewById(R.id.listView);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        nrest = (TextView) findViewById(R.id.lblMessage);
        for(restclass oc:publicdata.restlist)
        {
            if(Integer.toString(oc.GetIdrest()).equals(publicdata.idrest)) {
                publicdata.nrest=oc.Getnamerest();
                nrest.setText(publicdata.nrest);
            }

        }

        publicdata.orderlist=new ArrayList<orderclass>();
        WebService wscall = new WebService();
        wscall.execute();
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                final foodclass fo=(foodclass) adapter.getItem(position);
              //  Toast.makeText(getApplicationContext(), "idfood "+ds.GetIdfood(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewList.this);
                alert.setTitle("Update Quantity ");
                alert.setMessage("How Many your need?");
                // Set an EditText view to get user input
                final EditText input = new EditText(ViewList.this);
                input.setInputType(0x00002002);
                alert.setView(input);
                //----------ok
                alert.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int whichButton) {
                                orderclass oc=new orderclass();
                                oc.SetIdfood(fo.GetIdfood());
                                oc.Setnfood(fo.Getnfood());
                                oc.Setprice(fo.Getprice());
                                oc.Setmaxtime(fo.Getmaxtime());
                                oc.Setqyt(Integer.parseInt(input.getText().toString()));
                                publicdata.orderlist.add(oc);
                                Toast.makeText(getApplicationContext(), "item Added ! ", Toast.LENGTH_LONG).show();
                            }
                        });
                //------------Canceled
                alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Canceled.
                                dialog.cancel();
                            }
                        });
                //-----show
                alert.show();
            }
       });

        Button3 = (Button) findViewById(R.id.btncontinue);
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (publicdata.orderlist.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), order.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry,element must choose to continue", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button4 = (Button) findViewById(R.id.button6);
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                onBackPressed();
            }
        });
        Button1 = (Button) findViewById(R.id.btnaddfavorite);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewList.this);
                alert.setTitle("Confirmation ");
                alert.setMessage("Do you want Add to Favorites?");

                //----------ok
                alert.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int whichButton) {
                        WebService3 wscall = new WebService3();
                        wscall.execute();

                    }
                });
                //------------Canceled
                alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                });
                //-----show
                alert.show();

            }
        });
        Button2 = (Button) findViewById(R.id.btnrate);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
               AlertDialog.Builder alert = new AlertDialog.Builder(ViewList.this);
                alert.setTitle("Confirmation ");
                alert.setMessage("Do you want to vote with " + ratingBar.getRating()+" stars?");

                //----------ok
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        WebService2 wscall = new WebService2();
                        wscall.execute();

                    }
                });
                //------------Canceled
                alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                });
                //-----show
                alert.show();

            }
        });


    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        finish();
        super.onBackPressed();
    }
    // =============================================================================
//AysnTask class to handle Country WS call as separate UI Thread
// =============================================================================
    private class WebService extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg = new ProgressDialog(ViewList.this);
            pg.setMessage("Please wait ...");
            pg.setIndeterminate(false);
            pg.setCancelable(true);
            pg.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            getjson1("viewFood",Integer.toString(publicdata.idrest));
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
                    FoodList.clear();

                    for (int i = 0; i < Obj.length(); i++) {
                        JSONObject row = Obj.getJSONObject(i);
                        foodclass fd = new foodclass(
                                Integer.parseInt(row.getString("Idfood")),
                                row.getString("nfood"),
                                Integer.parseInt(row.getString("idrest")),
                                Double.parseDouble(row.getString("price")),
                                row.getString("maxtime"),
                                row.getString("description"),
                                row.getString("image"));



                        FoodList.add(fd);
                    }
                 //   Toast.makeText(getApplicationContext(), FoodList.size(), Toast.LENGTH_LONG).show();
                   adapter = new FoodAdapter(ViewList.this, FoodList);
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
            paramP1.setName("idrest");
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
//==============================================================================
//  =============================================================================
   //AysnTask class to handle Country WS call as separate UI Thread
    private class WebService2 extends AsyncTask<String, Void, Void> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pg = new ProgressDialog(ViewList.this);
        pg.setMessage("Please wait ...");
        pg.setIndeterminate(false);
        pg.setCancelable(true);
        pg.show();
    }
    @Override
    protected Void doInBackground(String... params) {
        getjson1("addrate");
        return null;
    }
    @Override
    protected void onPostExecute(Void result) {
        pg.dismiss();
        Toast.makeText(getApplicationContext(), result1.toString(), Toast.LENGTH_LONG).show();

        if(result1.equals("0") || result1.equals("error")){
            // username / password doesn't match
            Toast.makeText(getApplicationContext(), "can not rate !", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "rate successfully !", Toast.LENGTH_LONG).show();
        }
    }
    public void getjson1( String methName) {
        // Create request
        SoapObject request = new SoapObject(publicdata.NAMESPACE, methName);
        // Property which holds input parameters
        PropertyInfo param1 = new PropertyInfo();
        // Set Name
        param1.setName("username");
        // Set Value
        param1.setValue(publicdata.username);
        // Set dataType
        param1.setType(String.class);
        // Add the property to request object
        request.addProperty(param1);

        PropertyInfo param2 = new PropertyInfo();
        param2.setName("idrest");
        // Set Value
        param2.setValue(publicdata.idrest);
        // Set dataType
        param2.setType(String.class);
        // Add the property to request object
        request.addProperty(param2);

        PropertyInfo param3 = new PropertyInfo();
        param3.setName("ratevalue");
        // Set Value
        param3.setValue(Double.toString(ratingBar.getRating()) );
        // Set dataType
        param3.setType(String.class);
        // Add the property to request object
        request.addProperty(param3);



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
    //  =============================================================================
    //AysnTask class to handle Country WS call as separate UI Thread
    private class WebService3 extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg = new ProgressDialog(ViewList.this);
            pg.setMessage("Please wait ...");
            pg.setIndeterminate(false);
            pg.setCancelable(true);
            pg.show();
        }
        @Override
        protected Void doInBackground(String... params) {
            getjson1("addfavorite");
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            pg.dismiss();
            Toast.makeText(getApplicationContext(), result1.toString(), Toast.LENGTH_LONG).show();

            if(result1.equals("0") || result1.equals("error")){
                // username / password doesn't match
                Toast.makeText(getApplicationContext(), "can not add !", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "added successfully !", Toast.LENGTH_LONG).show();
            }
        }
        public void getjson1( String methName) {
            // Create request
            SoapObject request = new SoapObject(publicdata.NAMESPACE, methName);
            // Property which holds input parameters
            PropertyInfo param1 = new PropertyInfo();
            // Set Name
            param1.setName("username");
            // Set Value
            param1.setValue(publicdata.username);
            // Set dataType
            param1.setType(String.class);
            // Add the property to request object
            request.addProperty(param1);

            PropertyInfo param2 = new PropertyInfo();
            param2.setName("idrest");
            // Set Value
            param2.setValue(publicdata.idrest);
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