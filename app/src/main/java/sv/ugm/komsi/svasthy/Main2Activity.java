package sv.ugm.komsi.svasthy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Main2Activity extends AppCompatActivity implements FetchAddressHome.OnTaskCompleted {

    Context mContext;
    int fragID;
    private TextView mLocationTextView;
    private static  final int REQUEST_LOCATION_PERMISSION=1;
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private  boolean mTrackingLocation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new FragmentHome();
                    break;
                case R.id.navigation_moodtrack:
                    fragment = new FragmentMoodTrack();
                    break;
                case R.id.navigation_news:
                    fragment = new FragmentNews();
                    break;
                case R.id.navigation_profile:
                    fragment = new FragmentProfile();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mLocationTextView=findViewById(R.id.address_home);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation();
        int fragmentId = getIntent().getIntExtra("FRAGMENT_ID", 0);

        if (fragmentId == 0) {
            loadFragment(new FragmentHome());
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.setSelectedItemId(R.id.navigation_home);
        } else if (fragmentId == 1) {
            loadFragment(new FragmentHome());
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.setSelectedItemId(R.id.navigation_moodtrack);
        } else if (fragmentId == 3) {
            loadFragment(new FragmentHome());
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.setSelectedItemId(R.id.navigation_news);
        } else if (fragmentId == 4) {
            loadFragment(new FragmentHome());
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.setSelectedItemId(R.id.navigation_profile);
        }

        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    TRACKING_LOCATION_KEY);
        }

        mLocationCallback = new LocationCallback(){
            @Override
            public  void onLocationResult(LocationResult locationResult){
                new FetchAddressHome(Main2Activity.this,Main2Activity.this)
                        .execute(locationResult.getLastLocation());
            }
        };
    }


    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
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

    public  void onRequestPermissionResult(int requestCode,
                                           @NonNull String[] permission,
                                           @NonNull int[] grantResult){
        switch (requestCode){
            case REQUEST_LOCATION_PERMISSION:
                if(grantResult.length>0&&grantResult[0]==PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }else{
                    Toast.makeText(this, "Permission Denied!",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
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
