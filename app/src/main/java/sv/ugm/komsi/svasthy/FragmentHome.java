package sv.ugm.komsi.svasthy;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class FragmentHome extends Fragment {
    View view;

    private TextView mLocationTextView;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private boolean mTrackingLocation;
    Button mNotifyButton;
    private NotificationManager mNotifyManager;
    private static final String CHANNEL_ID="ch1";
    private static final int NOTIFICATION_ID=0;
    private static final String ACTION_UPDATE_NOTIFICATION="sv.ugm.komsi.svasthy.ACTION_UPDATE_NOTIFICATION";
    private static final String ACTION_CANCEL_NOTIFICATION="sv.ugm.komsi.svasthy.ACTION_CANCEL_NOTIFICATION";

    private NotificationReceiver mReciever=new NotificationReceiver();

    public FragmentHome() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLocationTextView = view.findViewById(R.id.address_home);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    TRACKING_LOCATION_KEY);
        }
        Log.d("mantap", getActivity().toString());
        final Context context = getActivity();
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                new FetchAddressHome(context, new FetchAddressHome.OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(String result) {
                        if (mTrackingLocation) {
                            Log.d("mantap jiwa", result);
                            try {
                                mLocationTextView.setText(getString(R.string.address_text, result, System.currentTimeMillis()));
                            } catch (IllegalStateException e){
                                e.printStackTrace();
                            }
                        }
                    }
                })
                        .execute(locationResult.getLastLocation());
            }
        };

        getLocation();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    public void onRequestPermissionResult(int requestCode,
                                          @NonNull String[] permission,
                                          @NonNull int[] grantResult) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(getContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mNotifyButton=view.findViewById(R.id.button_notify);

        mNotifyManager=(NotificationManager)
                getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_NOTIFICATION);
        intentFilter.addAction(ACTION_CANCEL_NOTIFICATION);
        getContext().registerReceiver(mReciever,intentFilter);
        mNotifyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });
        return view;
    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID,"Notification",
                            NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Descriptions....");
            channel.enableVibration(true);
            channel.enableLights(true);
            mNotifyManager.createNotificationChannel(channel);
        }


    }

    private void sendNotification(){
        Intent notifIntent=new Intent(getContext(),FragmentHome.class);
        PendingIntent notifPendingIntent=
                PendingIntent.getActivity(getContext(),NOTIFICATION_ID,
                        notifIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent updateIntent=new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent=PendingIntent.getBroadcast(
                getContext(),NOTIFICATION_ID,updateIntent,PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder(getContext(),CHANNEL_ID)//untuk API 26 perlu CHANNEL_ID
                        .setContentTitle("Hi :)")
                        .setContentText("Time To Drink Water")
                        .setSmallIcon(R.drawable.ic_drink)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        Notification myNotification=notifyBuilder.build();
        mNotifyManager.notify(NOTIFICATION_ID,myNotification);


    }
    private class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

}