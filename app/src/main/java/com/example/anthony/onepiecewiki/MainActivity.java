package com.example.anthony.onepiecewiki;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private Fragment[] fragments;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottomnav);
        fragments = new Fragment[] {new FragmentOne(), new FragmentTwo()};
        setFragment(0);
        setBottomNavView((BottomNavigationView)findViewById(R.id.bottom_nav_view));
    }

    private void setFragment(int fragmentVal) {
        currentFragment = fragments[fragmentVal];

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, currentFragment);
        transaction.commit();
    }

    private void setBottomNavView(BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.fragment_1:
                        setFragment(0);
                        return true;
                    case R.id.fragment_2:
                        setFragment(1);
                        return true;
                }
                return true;
            }
        });
    }
}
