package com.example.bonvoath.tms;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bonvoath.tms.Entities.OrderMaster;
import com.example.bonvoath.tms.utils.DialogOrderGoToMap;
import com.example.bonvoath.tms.utils.OrderMapInfoDetail;
import com.example.bonvoath.tms.utils.TMSLib;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        DialogOrderGoToMap.OnDialogItemClick{

    FusedLocationProviderClient mFusedLocationProviderClient;
    GoogleMap mMap;
    SharedPreferences mSharedPreferences;

    private static final float DEFAULT_ZOOM = 16f;
    private ArrayList<OrderMaster> mOrderNums = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSharedPreferences = getApplicationContext().getSharedPreferences("Auth", MODE_PRIVATE);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        initializeComponent();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initGoogleMap();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_location) {
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_orders) {
            Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        getDeviceLocation();
        mMap.setMyLocationEnabled(true);
        orderData();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        OrderMapInfoDetail dialog = new OrderMapInfoDetail();
        dialog.setData((JSONObject) marker.getTag());
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), getClass().getName());

        return true;
    }

    @Override
    public void OnItemClick(View view, int positionId) {
        OrderMaster order = mOrderNums.get(positionId);
        LatLng latLng = new LatLng(order.getLat(),order.getLng());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }

    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }

    private void initGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getDeviceLocation() {
        try {
            Task<Location> request = mFusedLocationProviderClient.getLastLocation();
            request.addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null)
                    {
                        onLocationChanged(location);
                    }
                }
            });

            request.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Fail:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (SecurityException e) {
            Log.e("Map Context", e.getMessage());
        }
    }

    private void orderData()
    {
        String url = TMSLib.getUrl(this, R.string.order_get_by_truck);
        String key = mSharedPreferences.getString("TruckNumber", "");
        Map<String, Object> params = new HashMap<>();
        params.put("TruckNum", key);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    boolean isError = response.getBoolean("IsError");
                    if(isError == false){
                        JSONArray data = response.getJSONArray("Data");
                        for(int i=0;i<data.length(); i++){
                            JSONObject obj = (JSONObject) data.get(i);
                            double lat = obj.getDouble("Lat");
                            double lng = obj.getDouble("Long");
                            String price = obj.getString("Price");
                            LatLng latLng = new LatLng(lat, lng);
                            addMarker(price,obj, latLng);
                            String num = obj.getString("OrderNum");
                            String date = obj.getString("OrderDate");
                            String address = obj.getString("Address");
                            OrderMaster order = new OrderMaster(num, date, address);
                            order.setLat(lat);
                            order.setLong(lng);
                            mOrderNums.add(order);
                        }
                    }
                }catch (JSONException e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(request);
    }

    private void initializeComponent(){
        btnSearch = findViewById(R.id.btn_search);
        btnRefresh = findViewById(R.id.btn_refresh);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogOrderGoToMap dialogOrderGoToMap = new DialogOrderGoToMap();
                dialogOrderGoToMap.setOrderDataList(mOrderNums);
                dialogOrderGoToMap.setCancelable(false);
                dialogOrderGoToMap.setOnDialogItemClick(MainActivity.this);
                dialogOrderGoToMap.show(getSupportFragmentManager(), getClass().getName());
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Refresh order", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void addMarker(String price, JSONObject data, LatLng latLng){
        MarkerOptions options = new MarkerOptions();
        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(price)));
        options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
        options.position(latLng);
        mMap.addMarker(options).setTag(data);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }
    ImageButton btnSearch;
    ImageButton btnRefresh;
}
