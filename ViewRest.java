package com.food.foodonroad;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class ViewRest extends AppCompatActivity  implements LocationListener {
    GoogleMap googleMap;
    double latitude=21.7884622 ;
    double longitude =39.1313013;
    int countorder;
    CameraPosition cameraPosition;
    LatLng latLng;
    HashMap<String, Integer> markerMap = new HashMap<String, Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rest);
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        googleMap = fm.getMap();
       // googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        for(int i=0;i<publicdata.restlist.size();i++){
            restclass ut=publicdata.restlist.get(i);
            LatLng LOC2=  new LatLng(Double.parseDouble(ut.Getlantude()),Double.parseDouble(ut.Getlongtude()));

                    if(getcountorderrest(ut.GetIdrest())>2)
                    {
                        Marker a1 =googleMap.addMarker(new MarkerOptions().position(LOC2)
                                .title(ut.Getnamerest())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                                .snippet(ut.Getaddress() + "  Click Here To Continue..."));
                        markerMap.put(a1.getId(),ut.GetIdrest());
                    }
                    else
                    {
                        Marker a1 =googleMap.addMarker(new MarkerOptions().position(LOC2)
                                .title(ut.Getnamerest())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                .snippet(ut.Getaddress() + "  Click Here To Continue..."));
                        markerMap.put(a1.getId(),ut.GetIdrest());
                    }
        }
        latLng = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.addMarker(new MarkerOptions().position(latLng).title("My Location")).showInfoWindow();
        cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.setOnMarkerClickListener(onMarkerClickedListener);
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                publicdata.idrest = markerMap.get(marker.getId());
                Intent intent = new Intent(getApplicationContext(), ViewList.class);
                startActivity(intent);
            }
        });

    }
    // get count order for all rest
    private int getcountorderrest(int p1)
    {
        int rtn=0;
        for(int i=0;i<publicdata.crowdrestlist.size();i++) {
            crowdrest ut1 = publicdata.crowdrestlist.get(i);
            if (ut1.GetIdrest() == p1) {
                rtn=ut1.Getcountorder();
            }
            else
            {
               rtn=0;
            }
        }
        return(rtn);
    }
    //
    private GoogleMap.OnMarkerClickListener onMarkerClickedListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            if (marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
            } else {
                marker.showInfoWindow();
            }
            return true;
        }
    };




    @Override
    public void onLocationChanged(Location location) {
          latitude=location.getLatitude() ;
          longitude =location.getLongitude();
        for(int i=0;i<publicdata.restlist.size();i++){
            restclass ut=publicdata.restlist.get(i);
            LatLng LOC2=  new LatLng(Double.parseDouble(ut.Getlantude()),Double.parseDouble(ut.Getlongtude()));
            Marker a1 =googleMap.addMarker(new MarkerOptions().position(LOC2)
                    .title(ut.Getnamerest())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .snippet(ut.Getaddress() + "  Click Here To Continue..."));
            markerMap.put(a1.getId(), ut.GetIdrest());

        }
        latLng = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.addMarker(new MarkerOptions().position(latLng).title("My Location")).showInfoWindow();
        cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
}
