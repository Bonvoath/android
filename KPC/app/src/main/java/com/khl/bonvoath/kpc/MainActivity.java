package com.khl.bonvoath.kpc;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String TAG = "MainActivity";
    FragmentManager fragmentManager;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initializeComponent();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        String title = "";
        Fragment mFragment = null;
        if(id == R.id.nav_home) {
            mFragment = new HomeFragment();
            title = getResources().getString(R.string.app_name);
        }else if (id == R.id.nav_profile) {
            mFragment = new FragmentProfile();
            title = getResources().getString(R.string.profile);
        } else if (id == R.id.nav_education) {
            mFragment = new EducationFragment();
            title = getResources().getString(R.string.label_kh_education);
        } else if(id == R.id.nav_experience){
            mFragment = new ExperienceFragment();
            title = getResources().getString(R.string.label_kh_experience);
        }else if(id == R.id.nav_gift){
            mFragment = new MotivationFragment();
            title = getResources().getString(R.string.label_motivation);
        }else if(id == R.id.nav_warning){
            mFragment = new PenaltyFragment();
            title = getResources().getString(R.string.label_penalty);
        }else if(id == R.id.nav_couple){
            mFragment = new CoupleFragment();
            title = getResources().getString(R.string.label_couple);
        }else if(id == R.id.nav_child){
            mFragment = new ChildFragment();
            title = getResources().getString(R.string.label_child);
        }else if(id == R.id.nav_parent){
            mFragment = new ParentFragment();
            title = getResources().getString(R.string.label_parent);
        }else if(id == R.id.nav_ref){
            mFragment = new ReferenceFragment();
            title = getResources().getString(R.string.label_reference);
        }

        if(mFragment != null) {
            if(getSupportActionBar() != null)
                getSupportActionBar().setTitle(title);
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, mFragment, TAG).commit();
        }

        return true;
    }

    private void initializeComponent(){
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new HomeFragment(), TAG).commit();
    }
}
