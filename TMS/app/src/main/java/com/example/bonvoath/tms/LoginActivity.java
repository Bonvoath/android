package com.example.bonvoath.tms;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bonvoath.tms.utils.TMSLib;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements OnClickListener{
    private EditText txtTruckNumber;
    private View mProgressView;
    private View mLoginFormView;
    SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    CallbackManager callbackManager;

    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    // local variable
    String truck_number;
    String loginProvider;
    String user_id;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginManager.getInstance().logOut();
        clearLoginSession();

        txtTruckNumber = findViewById(R.id.txtTruckNum);
        txtTruckNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        LoginButton loginButton = findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, facebookCallback);

        initializeComponent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
                googleSignIn();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void attemptLogin() {
        txtTruckNumber.setError(null);
        truck_number = txtTruckNumber.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(truck_number)) {
            txtTruckNumber.setError(getString(R.string.message_required_truck_number));
            focusView = txtTruckNumber;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            runQuery();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void runQuery()
    {
        String url = TMSLib.getUrl(this, R.string.truck_login);
        Map<String, String> params = new HashMap<>();
        params.put("TruckNum", truck_number);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params), loginCallback, loginErrorCallback);
        Volley.newRequestQueue(this).add(request);
    }

    private void initializeComponent(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        ((TextView)signInButton.getChildAt(0)).setText(R.string.google_sign_in);
        signInButton.setOnClickListener(this);
    }

    private void clearLoginSession(){
        mSharedPreferences = getApplicationContext().getSharedPreferences("Auth", MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            loginProvider = "Google";
            register_account(account.getEmail(), account.getDisplayName(), account.getId());
        }
    }

    private void get_facebook_email(){
        showProgress(true);
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), fbCallback);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void register_account(String email, String name, String value){
        String url = TMSLib.getUrl(this, R.string.register_new_account);
        Map<String, String> params = new HashMap<>();
        params.put("Email", email);
        params.put("UserName", name);
        params.put("Provider", loginProvider);
        params.put("ProviderValue", value);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params), registerCallback, registerErrorCallback);
        Volley.newRequestQueue(this).add(request);
    }

    private FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>(){
        @Override
        public void onSuccess(LoginResult loginResult) {
            loginProvider = "Facebook";
            get_facebook_email();
        }
        @Override
        public void onCancel() {
            Toast.makeText(getApplication(), "Cancel", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onError(FacebookException error) {
            Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_LONG).show();
        }
    };

    // facebook request information.
    private GraphRequest.GraphJSONObjectCallback fbCallback = new GraphRequest.GraphJSONObjectCallback(){
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            try{
                String email = (object.isNull("email")?"": object.getString("email"));
                String name = object.getString("name");
                String id = object.getString("id");
                register_account(email, name, id);
            }catch (JSONException e){
                Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show();
                signOut();
            }
        }
    };

    // register response callback.
    private Response.Listener<JSONObject> registerCallback = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response) {
            try {
                boolean is_error = response.getBoolean("IsError");
                if(!is_error){
                    JSONObject data = response.getJSONObject("Data");
                    user_name = data.getString("Name");
                    user_id = data.getString("UserId");
                    truck_number = data.getString("TruckNumber");
                    startMainActivity();
                }else{
                    signOut();
                    Toast.makeText(getApplication(), response.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                showProgress(false);
                Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show();
                signOut();
            }
        }
    };

    // register response callback.
    private Response.Listener<JSONObject> loginCallback = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response) {
            try {
                boolean is_error = response.getBoolean("IsError");
                boolean data = response.isNull("Data");
                showProgress(false);
                if(is_error) {
                    txtTruckNumber.setError("Login fail");
                } else {
                    if(data) {
                        txtTruckNumber.setError("Login fail");
                    } else {
                        startMainActivity();
                    }
                }
            } catch (JSONException e) {
                showProgress(false);
                txtTruckNumber.setError(e.getMessage());
            }
        }
    };

    // register error callback
    private Response.ErrorListener registerErrorCallback = new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error) {
            showProgress(false);
            signOut();
        }
    };

    // register error callback
    private Response.ErrorListener loginErrorCallback = new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error) {
            showProgress(false);
            txtTruckNumber.setError("Login fail");
        }
    };

    // start main activity.
    private void startMainActivity(){
        editor.putString("TruckNumber", truck_number);
        editor.putString("UserId", user_id);
        editor.putString("UserName", user_name);
        editor.putString("Provider", loginProvider);
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    // sign out facebook or google
    private void signOut(){
        LoginManager.getInstance().logOut();
        mGoogleSignInClient.getSignInIntent();
        clearLoginSession();
    }
}

