package sv.ugm.komsi.svasthy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Main2Activity extends AppCompatActivity {

    Context mContext;
    int fragID;

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
                    fragment = new FragmentHome();
                    break;
                case R.id.navigation_news:
                    fragment = new FragmentHome();
                    break;
                case R.id.navigation_profile:
                    fragment = new FragmentHome();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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


}
