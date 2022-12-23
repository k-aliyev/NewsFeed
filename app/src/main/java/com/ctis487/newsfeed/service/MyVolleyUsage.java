package com.ctis487.newsfeed.service;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ctis487.newsfeed.entity.Article;
import com.ctis487.newsfeed.entity.Source;
import com.ctis487.newsfeed.util.Common;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyVolleyUsage {
    RequestQueue queue;
    Context context;

    public MyVolleyUsage(Context context){
        this.context =context;
        queue = Volley.newRequestQueue(context);
    }

    public void requestArticles(String urlString) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, urlString, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray articles = response.getJSONArray("articles");
                            Common.articles.addAll(new ArrayList<Article>(Arrays.asList(new Gson().fromJson(
                                    articles.toString(), Article[].class
                            ))));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("Result: ", Common.articles.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Result",
                        "ERROR JSONObject request" + error.toString());
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0");
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }

    public void requestSources(String urlString) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, urlString, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray sources = response.getJSONArray("sources");
                            Common.setSources(new ArrayList<Source>(Arrays.asList(new Gson().fromJson(
                                    sources.toString(), Source[].class
                            ))));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("Result: ", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Result",
                                "ERROR JSONObject request" + error.toString());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0");
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }

    public void sendGETRequest(String urlString) {
        // To get
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                urlString, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Request", response.toString());
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError arg0) {
                Toast.makeText(context, "ERROR",
                        Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("num1", "10");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}