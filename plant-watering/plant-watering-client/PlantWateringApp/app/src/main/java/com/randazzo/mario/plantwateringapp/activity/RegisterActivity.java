package com.randazzo.mario.plantwateringapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.randazzo.mario.plantWatering.dto.security.UserRegistrationDTO;
import com.randazzo.mario.plantwateringapp.R;
import com.randazzo.mario.plantwateringapp.network.NetworkController;
import com.randazzo.mario.plantwateringapp.util.Messages;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseActivity {

    private RegisterActivity ctx;

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText passwordConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ctx = this;

        firstName = findViewById(R.id.register_first_name);
        lastName = findViewById(R.id.register_last_name);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        passwordConfirmation = findViewById(R.id.register_password_confirmation);
    }

    public void confirmRegistration(View view) {
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setFirstName(firstName.getText().toString());
        registrationDTO.setLastName(lastName.getText().toString());
        registrationDTO.setEmail(email.getText().toString());
        registrationDTO.setPassword(password.getText().toString());
        registrationDTO.setPasswordConfirmation(passwordConfirmation.getText().toString());

        NetworkController.getInstance(this).addToRequestQueue(new RegisterRequest(registrationDTO));
    }

    class RegisterRequest extends StringRequest {

        private final UserRegistrationDTO registrationRequest;

        RegisterRequest(UserRegistrationDTO registrationRequest) {
            super(
                    Method.POST,
                    urlServer + "register",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ctx.showOkDialog("Info", Messages.fromMessageResponse(response), true);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            doOnErrorResponse(error, false);
                        }
                    }
            );

            this.registrationRequest = registrationRequest;
        }

        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> params = new HashMap<>();
            params.put("Content-Type", "application/json");
            return params;
        }

        @Override
        public byte[] getBody() {
            return new Gson().toJson(registrationRequest).getBytes();
        }
    }


}
