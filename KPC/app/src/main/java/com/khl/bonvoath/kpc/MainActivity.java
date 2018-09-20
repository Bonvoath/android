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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnHomeFragmentListener {
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
        if(id == R.id.nav_home) {
            loadHomeFragment();
        }else if (id == R.id.nav_profile) {
            loadFragment(new FragmentProfile(), R.string.profile);
        } else if (id == R.id.nav_education) {
            loadFragment(new EducationFragment(), R.string.label_kh_education);
        } else if(id == R.id.nav_experience){
            loadFragment(new ExperienceFragment(), R.string.label_kh_experience);
        }else if(id == R.id.nav_gift){
            loadFragment(new MotivationFragment(), R.string.label_motivation);
        }else if(id == R.id.nav_warning){
            loadFragment(new PenaltyFragment(), R.string.label_penalty);
        }else if(id == R.id.nav_couple){
            loadFragment(new FamilyFragment(), R.string.label_family);
        }else if(id == R.id.nav_parent){
            loadFragment(new ParentFragment(), R.string.label_parent);
        }else if(id == R.id.nav_ref){
            loadFragment(new ReferenceFragment(), R.string.label_reference);
        }

        return true;
    }

    private void initializeComponent(){
        fragmentManager = getSupportFragmentManager();
        loadHomeFragment();
    }

    @Override
    public void onClickGridViewItem(int position) {
        if(position == 0)
            loadFragment(new FragmentProfile(), R.string.profile);
        else if (position == 1) {
            loadFragment(new EducationFragment(), R.string.label_kh_education);
        } else if(position == 2){
            loadFragment(new ExperienceFragment(), R.string.label_kh_experience);
        }else if(position == 3){
            loadFragment(new MotivationFragment(), R.string.label_motivation);
        }else if(position == 4){
            loadFragment(new PenaltyFragment(), R.string.label_penalty);
        }else if(position == 5){
            loadFragment(new FamilyFragment(), R.string.label_family);
        }else if(position == 6){
            loadFragment(new ParentFragment(), R.string.label_parent);
        }else if(position == 7){
            loadFragment(new ReferenceFragment(), R.string.label_reference);
        }
    }

    private void loadHomeFragment(){
        HomeFragment home =new HomeFragment();
        home.setmOnHomeFragmentListener(this);
        loadFragment(home, R.string.label_kh_home);
    }

    private void loadFragment(Fragment fragment, int title){
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment, TAG).commit();
    }
}
