package sv.ugm.komsi.svasthy;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class FragmentHome extends Fragment implements FetchAddressHome.OnTaskCompleted{
    View view;
    private TextView mLocationTextView;
    private static  final int REQUEST_LOCATION_PERMISSION=1;
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private  boolean mTrackingLocation;


    public FragmentHome() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLocationTextView= view.findViewById(R.id.address_home);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());


        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    TRACKING_LOCATION_KEY);
        }

        mLocationCallback = new LocationCallback(){
            @Override
            public  void onLocationResult(LocationResult locationResult){
                new FetchAddressHome(getContext(),FragmentHome.this)
                        .execute(locationResult.getLastLocation());
            }
        };
        getLocation();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
//
            mTrackingLocation = true;
            mFusedLocationClient.requestLocationUpdates
                    (getLocationRequest(),
                            mLocationCallback,
                            null /* Looper */);
            mLocationTextView.setText(getString(R.string.address_text,
                    getString(R.string.loading),
                    System.currentTimeMillis()));

        }
    }
    private LocationRequest getLocationRequest(){
        LocationRequest locationRequest =new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }



    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putBoolean(TRACKING_LOCATION_KEY,mTrackingLocation);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onTaskCompleted(String result) {
        if(mTrackingLocation){
            mLocationTextView.setText(getString
                    (R.string.address_text,result,System.currentTimeMillis()));
        }
    }
}

