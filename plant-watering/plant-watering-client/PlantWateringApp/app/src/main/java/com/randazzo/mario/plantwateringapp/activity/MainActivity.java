package com.randazzo.mario.plantwateringapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.randazzo.mario.plantwateringapp.R;
import com.randazzo.mario.plantwateringapp.network.NetworkController;
import com.randazzo.mario.plantwateringapp.util.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    public static final int LOGOUT_REQUEST_CODE = 1;
    public static final int LOGOUT_RESULT_OK_CODE = 1;
    public static final int LOGOUT_RESULT_FAIL_CODE = 2;

    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button registerButton;
    private ImageView settingsButton;
    private ProgressBar loginLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.ctx = this;

        this.username = findViewById(R.id.main_username);
        this.password = findViewById(R.id.main_password);

        this.loginButton = findViewById(R.id.main_login_button);
        this.registerButton = findViewById(R.id.main_register_button);
        this.settingsButton = findViewById(R.id.main_settings_button);

        this.loginLoading = findViewById(R.id.main_login_loading);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == LOGOUT_REQUEST_CODE) {
            if (resultCode == LOGOUT_RESULT_OK_CODE)
                Toast.makeText(this, "Logout successfully!", Toast.LENGTH_LONG).show();
            else if (resultCode == LOGOUT_RESULT_FAIL_CODE)
                Toast.makeText(this, "Error on performing logout!", Toast.LENGTH_LONG).show();

            setEnableUI(true);
            loginLoading.setVisibility(View.GONE);
        }
    }

    public void login(View view) {
        NetworkController.getInstance(this).addToRequestQueue(
                new LoginRequest(username.getText().toString(), password.getText().toString())
        );
        setEnableUI(false);
        loginLoading.setVisibility(View.VISIBLE);
    }

    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void settings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void setEnableUI(Boolean enabled) {
        username.setEnabled(enabled);
        password.setEnabled(enabled);
        loginButton.setEnabled(enabled);
        registerButton.setEnabled(enabled);
        settingsButton.setEnabled(enabled);
    }


    class LoginRequest extends JsonObjectRequest {

        private String username;
        private String password;

        LoginRequest(String username, String password) {
            super(
                    Method.GET,
                    urlServer + "private/account",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Session.getInstance().setSessionToken(response.getString("authctoken"));
                                startActivityForResult(new Intent(ctx, PlantActivity.class), LOGOUT_REQUEST_CODE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            doOnErrorResponse(error, false);
                            setEnableUI(true);
                            loginLoading.setVisibility(View.GONE);
                        }
                    }
            );

            this.username = username.trim();
            this.password = password.trim();
        }

        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> params = new HashMap<>();
            params.put("Authorization", "Basic " + new String(Base64.encode((username + ":" + password).getBytes(), android.util.Base64.DEFAULT)));
            return params;
        }
    }

}
