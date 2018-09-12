package com.zulekhahospitals.zulekhaapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivityNew extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    double latd,lngd;
    String k,l;
    String x,y,w,z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_new);
try {


    k = getIntent().getExtras().getString("lat_id");
    l = getIntent().getExtras().getString("lng_id");

    System.out.println("latitude inside mapactivity : " + k);
    System.out.println("longitude inside mapactivity : " + l);

    x = k.replaceAll("N", "");
    y = l.replaceAll("E", "");
    w = x.replaceAll(" ", "");
    z = y.replaceAll(" ", "");
    latd = Double.parseDouble(w);
    lngd = Double.parseDouble(z);

    System.out.println("lat(w) in MapsActivity : " + w);
    System.out.println("lng(z) in MapsActivity : " + z);

    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
}catch (Exception e){
    e.printStackTrace();
}
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        System.out.print("onMapReady lat"+latd);
        System.out.print("onMapReady lng"+lngd);
        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(latd, lngd);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Zulekha Hospital"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
try {

//original cordinates lat= 25.291000  ,   long= 55.384250
    LatLng sydney = new LatLng(latd, lngd);


    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(17).build();
    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    mMap.addMarker(new MarkerOptions().position(sydney).title("Zulekha Hospital"));
   /* Toast.makeText(getApplicationContext(), "latitude : " + latd + "\nlongitude : " + lngd, Toast.LENGTH_LONG).show();*/
}catch (Exception e){
    e.printStackTrace();
}
    }
}
