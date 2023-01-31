package com.example.signdemo;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.signdemo.databinding.ActivityMapsBinding;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    class Position{
        double lat,lng;
    }
    class Marker{
        Position position;
        String location,description,reporter;
    }
    class Response{
        boolean success;
        String message;
        Marker[] markers;
    }

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityMapsBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        setContentView(R.layout.activity_maps);

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
        Executors.newSingleThreadExecutor().execute(()->{
            try {
                Log.e("info","here");
                HttpURLConnection conn= (HttpURLConnection) new URL("http://172.20.10.8/Banjir/markers.php").openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();
                InputStream is;
                if(conn.getResponseCode()!=200){
                    is=conn.getErrorStream();
                }
                else
                    is=conn.getInputStream();
                StringBuilder sb=new StringBuilder();
                BufferedReader br= new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line=br.readLine())!=null){
                    sb.append(line);
                }
                Log.e("MAP",sb.toString());
                Response response=new Gson().fromJson(sb.toString(),Response.class);
                if(response.success){
                    for(Marker marker:response.markers){
                        runOnUiThread(()->{
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(marker.position.lat,marker.position.lng))
                                    .title(marker.location)
                                    .snippet(marker.description + "\n" + marker.reporter));

                        });
                    }
                }else{
                    runOnUiThread(()->{
                        Toast.makeText(this,response.message,Toast.LENGTH_SHORT).show();
                    });
                }
            }catch (Exception e){
                Log.e("error",e.getMessage(),e);
            }

        });

    }
}
