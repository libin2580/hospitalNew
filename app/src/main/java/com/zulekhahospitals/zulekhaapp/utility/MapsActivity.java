package com.zulekhahospitals.zulekhaapp.utility;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zulekhahospitals.zulekhaapp.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double latd,lngd;
    String k,l;
    String x,y,w,z;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        k=intent.getStringExtra("lat_id");
        l=intent.getStringExtra("lng_id");


        System.out.println("lat in MapsActivity : "+k);
        System.out.println("lng in MapsActivity : "+l);

        x=k.replaceAll("N","");
        y=l.replaceAll("E","");
        w=x.replaceAll(" ","");
        z=y.replaceAll(" ","");
       latd = Double.parseDouble(w);
       lngd= Double.parseDouble(z);

        System.out.println("lat(w) in MapsActivity : "+w);
        System.out.println("lng(z) in MapsActivity : "+z);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        System.out.print(""+latd);
        System.out.print(""+lngd);
        LatLng sydney = new LatLng(latd,lngd);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Zulekha Hospital"));
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(sydney , 14.0f) );
      // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
