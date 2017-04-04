package com.example.dam.mycrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity
{

    private static final String TAG = LoginActivity.class.getSimpleName();
    Button signUp;
    Button login;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.login_button);
        email = (EditText) findViewById(R.id.login_email_id);
        password = (EditText) findViewById(R.id.login_password);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String url =  SignUpActivity.IP_ADDRESS + "/getSalt";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e(TAG, "onResponse: " + response);
                        if (response.equals("No user found"))
                        {
                            Toast.makeText(LoginActivity.this, "No user found", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, SignUpActivity.IP_ADDRESS + "/login", new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response)
                                {
                                    Log.e(TAG, "onResponse: " + response);
                                }
                            }, new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError
                                {
                                    Map<String,String> params = new HashMap<String, String>();
                                    params.put(SignUpActivity.E_MAIL_ID, email.getText().toString());
                                    params.put(SignUpActivity.PASSWORD, password.getText().toString());
                                    return params;
                                }
                            };
                            requestQueue1.add(stringRequest1);
                        }
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put(SignUpActivity.E_MAIL_ID, email.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }
}

