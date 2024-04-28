package com.example.practicaltestfazal.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.practicaltestfazal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker[] markers = new Marker[4];
    private Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng[] locations = {
                new LatLng(23.0204741, 72.4149272), // Ahmedabad
                new LatLng(22.6958878, 72.8175262), // Nadiad
                new LatLng(22.5489636, 72.7970762),  // Anand
                new LatLng(22.322337, 73.0082707)    // Vadodara
        };

        for (int i = 0; i < locations.length; i++) {
            markers[i] = mMap.addMarker(new MarkerOptions()
                    .position(locations[i])
                    .title("Marker " + (i + 1)));
        }

        drawLinesBetweenMarkers(locations);

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (LatLng latLng : locations) {
                    builder.include(latLng);
                }
                LatLngBounds bounds = builder.build();
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
            }
        });
    }


    private void drawLinesBetweenMarkers(LatLng[] locations) {
        PolylineOptions polylineOptions = new PolylineOptions();
        //polylineOptions.color(Color.WHITE);
        for (LatLng latLng : locations) {
            polylineOptions.add(latLng);
        }
        polyline = mMap.addPolyline(polylineOptions);
    }
}