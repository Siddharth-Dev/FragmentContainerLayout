package com.sj.fragmentsample;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;
    FragmentContainerLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (FragmentContainerLayout) findViewById(R.id.container);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.apps_drawerLayout);
        mDrawerLayout.setDrawerListener(new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name));
        mNavigationView = (NavigationView)findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.action_componse) {
                    addFragment(new FragmentOne());
                } else if (menuItem.getItemId() == R.id.action_draft) {
                    addFragment(new FragmentTwo());
                }  else if (menuItem.getItemId() == R.id.action_messages) {
                    addFragment(new FragmentThree());
                }  else if (menuItem.getItemId() == R.id.action_settings) {
                    replaceFragment(new FragmentForth());
                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addFragment(Fragment fragment) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int count = fragmentManager.getBackStackEntryCount();
                if (count>0) {
                    Fragment fragment = fragmentManager.getFragments().get(count - 1);
                    if (fragment.getView() != null) {
                        fragment.getView().setVisibility(View.VISIBLE);
                    }  else if (layout.getChildCount() >0) {
                        layout.getChildAt(0).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        fragmentManager.beginTransaction().add(R.id.container, fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getName()).commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }
}
