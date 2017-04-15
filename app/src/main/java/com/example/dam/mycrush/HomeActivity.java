package com.example.dam.mycrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
{

    private static final String TAG = HomeActivity.class.getSimpleName();
    EditText prefix;
    Button searchInGraph, searchAll, searchPrefix;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        final String id = "2";
        final String interestedIn = "female";
        prefix = (EditText) findViewById(R.id.home_prefix);
        searchAll = (Button) findViewById(R.id.home_search_all);
        searchInGraph = (Button) findViewById(R.id.search_in_graph);
        searchPrefix = (Button) findViewById(R.id.search_prefix);

        searchInGraph.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://139" +
                        ".59.15.102:3000/searchInGraph", new Response.Listener<String>()
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
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", id);
                        hashMap.put("prefix", prefix.getText().toString());
                        hashMap.put("interestedIn", interestedIn);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });

        searchAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://139" +
                        ".59.15.102:3000/searchAll", new Response.Listener<String>()
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
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> hashMap = new HashMap<>();
                        hashMap.put("name", prefix.getText().toString());
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        searchPrefix.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://139" +
                        ".59.15.102:3000/searchInTst", new Response.Listener<String>()
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
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> hashMap = new HashMap<>();
                        hashMap.put("prefix", prefix.getText().toString());
                        hashMap.put("numberOfPerson", "3");
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });

    }
}
