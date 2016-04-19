package com.food.foodonroad;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class order extends AppCompatActivity {
    private ListView listView;
    private OrderAdapter adapter;
    EditText txtspecialrequest;
    Button saveorder,cancel;
    int maxtime=0;
    double total=0.0;
    String result1="",specialrequest;
    ProgressDialog pg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        listView = (ListView) findViewById(R.id.listView2);
        cancel = (Button) findViewById(R.id.btncancel);
        adapter = new OrderAdapter(order.this, publicdata.orderlist);
        listView.setAdapter(adapter);
double subtotal;
        for(orderclass oc:publicdata.orderlist)
        {
          if(maxtime<Integer.parseInt(oc.Getmaxtime())) {
            maxtime=Integer.parseInt(oc.Getmaxtime());
          }
            subtotal=oc.Getqyt()*oc.Getprice();
            total+=subtotal;
        }

        TextView  tcookingtime = (TextView) findViewById(R.id.txtcookingtime);
        TextView  ttotal = (TextView) findViewById(R.id.txttotal);
        tcookingtime.setText(Integer.toString(maxtime)+" Minute");
        ttotal.setText(Double.toString(total)+" SR");
        txtspecialrequest = (EditText) findViewById(R.id.edtspecialorder);
        saveorder = (Button) findViewById(R.id.btnsaveorder);
        saveorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(order.this);
                alert.setTitle("Warning ");
                alert.setMessage("Do you want to make  order?");
                //----------ok
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        specialrequest=txtspecialrequest.getText().toString();
                        WebService1 task = new WebService1();
                        //Execute the task to set Country list in Country Spinner Control
                        task.execute();
                        cancel.setText("Back");

                    }
                });
                //------------Canceled
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                });
                //-----show
                alert.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewRest.class);
                startActivity(intent);
            }
        });

    }
    //  =============================================================================
    //AysnTask class to handle Country WS call as separate UI Thread
    private class WebService1 extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg = new ProgressDialog(order.this);
            pg.setMessage("Please wait ...");
            pg.setIndeterminate(false);
            pg.setCancelable(true);
            pg.show();
        }
        @Override
        protected Void doInBackground(String... params) {
            getjson1("NewOrder");
            for(orderclass oc:publicdata.orderlist)
            {
                getjson2("NewOrderDetail", Integer.toString(oc.GetIdfood()), Integer.toString(oc.Getqyt()));
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            pg.dismiss();
            if(result1.equals("0") || result1.equals("error")){
                // username / password doesn't match
                Toast.makeText(getApplicationContext(), "can not save !", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Saved successfully !", Toast.LENGTH_LONG).show();
                saveorder.setEnabled(false);
            }
        }
        //  public string NewOrder(string idrest, string username, string total, string maxtime)
        public void getjson1( String methName) {
            // Create request
            SoapObject request = new SoapObject(publicdata.NAMESPACE, methName);
            // Property which holds input parameters
            PropertyInfo param1 = new PropertyInfo();
            // Set Name
            param1.setName("idrest");
            // Set Value
            param1.setValue(publicdata.idrest);
            // Set dataType
            param1.setType(String.class);
            // Add the property to request object
            request.addProperty(param1);

            PropertyInfo param2 = new PropertyInfo();
            param2.setName("username");
            // Set Value
            param2.setValue(publicdata.username);
            // Set dataType
            param2.setType(String.class);
            // Add the property to request object
            request.addProperty(param2);

            PropertyInfo param3 = new PropertyInfo();
            param3.setName("total");
            // Set Value
            param3.setValue(Double.toString(total));
            // Set dataType
            param3.setType(String.class);
            // Add the property to request object
            request.addProperty(param3);

            PropertyInfo param4 = new PropertyInfo();
            param4.setName("maxtime");
            // Set Value
            param4.setValue(Integer.toString(maxtime));
            // Set dataType
            param4.setType(String.class);
            // Add the property to request object
            request.addProperty(param4);

            PropertyInfo param5 = new PropertyInfo();
            param5.setName("specialrequest");
            // Set Value
            param5.setValue(specialrequest);
            // Set dataType
            param5.setType(String.class);
            // Add the property to request object
            request.addProperty(param5);

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

        //  public string NewOrder(string idrest, string username, string total, string maxtime)
        public void getjson2( String methName,String idfood1,String qyt1) {
            // Create request
            SoapObject request = new SoapObject(publicdata.NAMESPACE, methName);
            // Property which holds input parameters
            PropertyInfo param1 = new PropertyInfo();
            // Set Name
            param1.setName("idfood");
            // Set Value
            param1.setValue(idfood1);
            // Set dataType
            param1.setType(String.class);
            // Add the property to request object
            request.addProperty(param1);

            PropertyInfo param2 = new PropertyInfo();
            param2.setName("qyt");
            // Set Value
            param2.setValue(qyt1);
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
