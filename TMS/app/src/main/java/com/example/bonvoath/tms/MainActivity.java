package com.example.bonvoath.tms;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bonvoath.tms.Entities.DataSet;
import com.example.bonvoath.tms.Entities.OrderMaster;
import com.example.bonvoath.tms.utils.DialogOrderGoToMap;
import com.example.bonvoath.tms.utils.OrderMapInfoDetail;
import com.example.bonvoath.tms.utils.OrderPresenter;
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
import com.otaliastudios.autocomplete.Autocomplete;
import com.otaliastudios.autocomplete.AutocompleteCallback;
import com.otaliastudios.autocomplete.AutocompletePolicy;
import com.otaliastudios.autocomplete.AutocompletePresenter;
import com.otaliastudios.autocomplete.CharPolicy;

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
    Toolbar toolbar;
    FusedLocationProviderClient mFusedLocationProviderClient;
    GoogleMap mMap;
    SharedPreferences mSharedPreferences;

    private static final float DEFAULT_ZOOM = 10f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
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
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (android.support.v7.widget.SearchView)menuItem.getActionView();
        EditText editText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        setupUserAutocomplete(editText);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setNavigationIcon(null);
                searchView.setMaxWidth(Integer.MAX_VALUE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mMap.clear();
                for(OrderMaster order: DataSet.OrderMasters){
                    if(order.getRemark() != null && !order.getRemark().equals("")) {
                        if(order.getRemark().toLowerCase().contains(newText.toLowerCase())) {
                            LatLng latLng = new LatLng(order.getLat(), order.getLng());
                            addMarker(order, latLng);
                        }
                    }
                }
                return false;
            }
        });

        searchView.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                toolbar.setNavigationIcon(R.drawable.ic_dehaze_white_24dp);
                mMap.clear();
                for(OrderMaster order: DataSet.OrderMasters){
                    LatLng latLng = new LatLng(order.getLat(), order.getLng());
                    addMarker(order, latLng);
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        dialog.setData((OrderMaster)marker.getTag());
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), getClass().getName());

        return true;
    }

    @Override
    public void OnItemClick(View view, int positionId) {
        OrderMaster order = DataSet.OrderMasters.get(positionId);
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
        DataSet.OrderMasters = new ArrayList<>();
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
                    if(!isError){
                        JSONArray data = response.getJSONArray("Data");
                        for(int i=0;i<data.length(); i++){
                            JSONObject obj = (JSONObject) data.get(i);
                            double lat = obj.getDouble("Lat");
                            double lng = obj.getDouble("Long");
                            String name = obj.getString("Name");
                            String price = obj.getString("Price");
                            LatLng latLng = new LatLng(lat, lng);
                            String num = obj.getString("OrderNum");
                            String date = obj.getString("OrderDate");
                            String address = obj.getString("Address");
                            String remark =(obj.isNull("Remark")?"":obj.getString("Remark"));
                            OrderMaster order = new OrderMaster(num, date, address);
                            order.setLat(lat);
                            order.setName(name);
                            order.setLong(lng);
                            order.setRemark(remark);
                            order.setPrice(price);
                            addMarker(order, latLng);
                            DataSet.OrderMasters.add(order);
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
    }
    private void addMarker(OrderMaster data, LatLng latLng){
        MarkerOptions options = new MarkerOptions();
        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(data.getPrice())));
        options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
        options.position(latLng);
        mMap.addMarker(options).setTag(data);
    }

    private void setupUserAutocomplete(EditText edit) {
        float elevation = 6f;
        AutocompletePolicy policy = new CharPolicy('#');
        Drawable backgroundDrawable = new ColorDrawable(getResources().getColor(R.color.colorGray));
        final AutocompletePresenter<OrderMaster> presenter = new OrderPresenter(this);
        AutocompleteCallback<OrderMaster> callback = new AutocompleteCallback<OrderMaster>() {
            @Override
            public boolean onPopupItemClicked(Editable editable, OrderMaster item) {
                editable.clear();
                editable.append(item.getRemark());
                mMap.clear();
                LatLng latLng = new LatLng(item.getLat(),item.getLng());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                addMarker(item, latLng);
                return true;
            }

            public void onPopupVisibilityChanged(boolean shown) {

            }
        };

        Autocomplete.<OrderMaster>on(edit)
                .with(elevation)
                .with(backgroundDrawable)
                .with(presenter)
                .with(policy)
                .with(callback)
                .build();
    }

    SearchView searchView;
}
