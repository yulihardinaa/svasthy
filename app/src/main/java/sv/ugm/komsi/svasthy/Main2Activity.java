package sv.ugm.komsi.svasthy;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity implements FetchAddressHome.OnTaskCompleted {

    Context mContext;
    int fragID;
    Button mNotifyButton;
    private NotificationManager mNotifyManager;
    private NotificationReceiver mReciever=new NotificationReceiver();
    private static final String CHANNEL_ID="ch1";
    private static final int NOTIFICATION_ID=0;
    private static final String ACTION_UPDATE_NOTIFICATION="sv.ugm.komsi.svasthy.ACTION_UPDATE_NOTIFICATION";
    private static final String ACTION_CANCEL_NOTIFICATION="sv.ugm.komsi.svasthy.ACTION_CANCEL_NOTIFICATION";
    private FirebaseAuth mAuth;



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
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        mNotifyButton=(Button)findViewById(R.id.button_notify);

        mNotifyManager=(NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_NOTIFICATION);
        intentFilter.addAction(ACTION_CANCEL_NOTIFICATION);
        registerReceiver(mReciever,intentFilter);
//        mNotifyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendNotification();
//            }
//        });

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
        Intent notifIntent=new Intent(this,Main2Activity.class);
        PendingIntent notifPendingIntent=
                PendingIntent.getActivity(this,NOTIFICATION_ID,
                        notifIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent updateIntent=new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent=PendingIntent.getBroadcast(
                this,NOTIFICATION_ID,updateIntent,PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder(this,CHANNEL_ID)//untuk API 26 perlu CHANNEL_ID
                        .setContentTitle("Notification Title")
                        .setContentText("Notification Text")
                        .setSmallIcon(R.drawable.ic_drink)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .addAction(R.drawable.ic_drink,"UPDATE",updatePendingIntent);

        Notification myNotification=notifyBuilder.build();
        mNotifyManager.notify(NOTIFICATION_ID,myNotification);


    }
    @Override
    public void onTaskCompleted(String result) {

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

    }

    private class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}


