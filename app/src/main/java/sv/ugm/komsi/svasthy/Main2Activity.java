package sv.ugm.komsi.svasthy;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity implements FetchAddressHome.OnTaskCompleted {

    Context mContext;
    int fragID;

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


}


