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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bonvoath.tms.Entities.DataSet;
import com.example.bonvoath.tms.Entities.OrderMaster;
import com.example.bonvoath.tms.Entities.OrderSearch;
import com.example.bonvoath.tms.utils.MapInfoDialogFragment;
import com.example.bonvoath.tms.utils.OrderPresenter;
import com.example.bonvoath.tms.utils.TMSLib;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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
import com.otaliastudios.autocomplete.AutocompletePresenter;

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
        ActivityCompat.OnRequestPermissionsResultCallback,
        MapInfoDialogFragment.OnMapInfoDialogFragmentListener {
    Toolbar toolbar;
    FusedLocationProviderClient mFusedLocationProviderClient;
    GoogleMap mMap;
    SharedPreferences mSharedPreferences;
    MapInfoDialogFragment dialogFragment;

    private static final float DEFAULT_ZOOM = 10f;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    String key ;
    OrderMaster orderMaster;

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
        View headerView = navigationView.getHeaderView(0);
        ((TextView)headerView.findViewById(R.id.txt_user_login_name))
                .setText(mSharedPreferences.getString("UserName", ""));
        ((TextView)headerView.findViewById(R.id.txt_login_truck_number)).setText(key);
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

                return false;
            }
        });

        searchView.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                toolbar.setNavigationIcon(R.drawable.ic_dehaze_white_24dp);
                mMap.clear();
                orderData();
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
            Intent intent = new Intent(getApplicationContext(), CreateOrderActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_log_out) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        getDeviceLocation();
        enableMyLocation();
    }

    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            orderData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        orderData();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        orderMaster = (OrderMaster)marker.getTag();
        dialogFragment = MapInfoDialogFragment.instance(getClass().getName());
        dialogFragment.setData(orderMaster);
        dialogFragment.setOnMapInfoDialogFragmentListener(this);
        dialogFragment.show(getFragmentManager(), getClass().getName());

        return true;
    }

    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }

    private void initGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if(mapFragment != null)
            mapFragment.getMapAsync(this);
    }

    private void getDeviceLocation()
    {
        try
        {
            FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            Task<Location> request = mFusedLocationProviderClient.getLastLocation();
            request.addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null)
                    {
                        onLocationChanged(location);
                    }else{
                        Toast.makeText(MainActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            request.addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (SecurityException e)
        {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void orderData()
    {
        DataSet.OrderMasters = new ArrayList<>();
        String url = TMSLib.getUrl(this, R.string.order_get_by_truck);
        Map<String, Object> params = new HashMap<>();
        params.put("TruckNum", key);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params), getOrderCallback, getOrderErrorCallback);
        Volley.newRequestQueue(this).add(request);
    }

    private void initializeComponent(){
        key = mSharedPreferences.getString("TruckNumber", "");
    }
    private void addMarker(OrderMaster data, LatLng latLng){
        MarkerOptions options = new MarkerOptions();
        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(data.getPrice())));
        options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
        options.position(latLng);
        mMap.addMarker(options).setTag(data);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }

    private void setupUserAutocomplete(EditText edit) {
        float elevation = 6f;
        //AutocompletePolicy policy = new CharPolicy(this);
        Drawable backgroundDrawable = new ColorDrawable(getResources().getColor(R.color.colorGray));
        final AutocompletePresenter<OrderSearch> presenter = new OrderPresenter(this);
        AutocompleteCallback<OrderSearch> callback = new AutocompleteCallback<OrderSearch>() {
            @Override
            public boolean onPopupItemClicked(Editable editable, OrderSearch item) {
                editable.clear();
                editable.append(item.getTag());

                mMap.clear();
                /*
                DialogOrderGoToMap dialogOrderGoToMap = new DialogOrderGoToMap();
                dialogOrderGoToMap.setData(getApplicationContext(), item.getOrders(), mSharedPreferences.getString("TruckNumber", ""));
                dialogOrderGoToMap.setCancelable(false);
                dialogOrderGoToMap.show(getSupportFragmentManager(), getClass().getName());
                */
                for(OrderMaster order: item.getOrders()){
                    LatLng latLng = new LatLng(order.getLat(),order.getLat());
                    addMarker(order, latLng);
                }

                return true;
            }

            public void onPopupVisibilityChanged(boolean shown) {

            }
        };

        Autocomplete.<OrderSearch>on(edit)
                .with(elevation)
                .with(backgroundDrawable)
                .with(presenter)
                .with(callback)
                .build();
    }

    SearchView searchView;

    @Override
    public void onClick(View sender) {
        int id = sender.getId();
        if(id == R.id.btn_message){
            startActivity(new Intent(this, OrderCommentActivity.class));
        }else if(id == R.id.btn_pay){
            dialogFragment.dismiss();
            startActivity(new Intent(this, OrderPayActivity.class).putExtra("OrderNum", orderMaster.getOrderNumber()));
        }
    }

    Response.Listener<JSONObject> getOrderCallback = new Response.Listener<JSONObject>(){
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
                        LatLng latLng = new LatLng(lat, lng);
                        addMarker(createOrderMaster(obj, latLng), latLng);
                    }
                }
            }catch (JSONException e){
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    };

    Response.ErrorListener getOrderErrorCallback = new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private OrderMaster createOrderMaster(JSONObject obj, LatLng latLng){
        OrderMaster order = null;
        try {
            String name = obj.getString("Name");
            String price = obj.getString("Price");
            String num = obj.getString("OrderNum");
            String date = obj.getString("OrderDate");
            String address = obj.getString("Address");
            String remark = (obj.isNull("Remark") ? "" : obj.getString("Remark"));
            order = new OrderMaster(num, date, address);
            order.setLat(latLng.latitude);
            order.setLong(latLng.longitude);
            order.setName(name);
            order.setRemark(remark);
            order.setPrice(price);
        }catch (JSONException e){}

        return order;
    }
}
