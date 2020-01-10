package com.example.mylocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TaskLoadedCallBack {

    static String TAG = "MAIN_ACTIVITY";
    GoogleMap map;
    Boolean MapReady = false;

    Button check;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    Context context = this;

    Polyline ploylineRoutes;
    MarkerOptions from;
    MarkerOptions to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(new MapReadyCallBack());

//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        mFusedLocationProviderClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//
////                            this is my current location
////                            location.getLatitude();
////                            location.getLongitude();
////                            Toast.makeText(context,"Lat:" + String.valueOf(location.getLatitude()) + " " + "Lng:" + String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

        final Call<List<Futsal>> futsalApi = ApiManager.getInstance().getApiClient().getFutsal();
        check = (Button)findViewById(R.id.btncheck);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchDataFromApi();
            }
        });
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        Log.d(TAG,"isServicesOK : Checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG,"isServiceOk : Google Play Services is working");
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG,"isServiceOk : an error");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,9001);
            dialog.show();
        }else{
            Toast.makeText(this,"Can't Make Maps Requests", Toast.LENGTH_LONG).show();
        }
        return  false;
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters from the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url from the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
//        if (ploylineRoutes != null){
//            ploylineRoutes.remove();
//        }
        ploylineRoutes = map.addPolyline((PolylineOptions) values[0]);
    }

    private class MapReadyCallBack implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            map.setMyLocationEnabled(true);
//            map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            fetchDataFromApi();
        }
    }

    private void fetchDataFromApi(){
        final Call<List<Futsal>> futsalApi = ApiManager.getInstance().getApiClient().getFutsal();

        futsalApi.clone().enqueue(new Callback<List<Futsal>>() {
            @Override
            public void onResponse(Call<List<Futsal>> call, Response<List<Futsal>> response) {
                final List<Futsal> futsalList = response.body();
                mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
                mFusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if(map!=null){
                                    if(location!=null){
                                        from = new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude()));
                                    }
                                    for(Futsal futsal : futsalList){
                                        LatLng latLng = new LatLng(futsal.getlATITUDE(), futsal.getlONGITUDE());
                                        to = new MarkerOptions().position(latLng);
                                        map.addMarker(new MarkerOptions().position(latLng).title(futsal.getnAME()));
                                        if(location!=null){
                                            new FetchURL(MainActivity.this).execute(getUrl(from.getPosition(), to.getPosition(), "driving"), "driving");
                                        }
                                    }
                                }else {
                                    Toast.makeText(MainActivity.this,"Map is Loading",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onFailure(Call<List<Futsal>> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
