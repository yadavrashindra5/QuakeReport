package com.example.quaakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mQueue= Volley.newRequestQueue(this);

        ArrayList<Earthquake> earthquakes=new ArrayList<>();

        String url="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("features");
                            for(int i=0;i<jsonArray.length();++i)
                            {

                                JSONObject currentEarthquake=jsonArray.getJSONObject(i);
                                JSONObject properties=currentEarthquake.getJSONObject("properties");
                                Double magnitude=properties.getDouble("mag");
                                String place=properties.getString("place");
                                long time=properties.getLong("time");
                                String url1=properties.getString("url");
                                String timee=String.valueOf(time);
                                earthquakes.add(new Earthquake(magnitude,place,timee,url1));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );
        mQueue.add(request);

        ListView lis=findViewById(R.id.list);
        EarthquakeAdapter adapter=new EarthquakeAdapter(this,earthquakes);
        lis.setAdapter(adapter);
        lis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake currentEarthquake=adapter.getItem(position);
                Uri earthquakeUri= Uri.parse(currentEarthquake.getUrl());
                Intent website=new Intent(Intent.ACTION_VIEW,earthquakeUri);
                startActivity(website);
            }
        });







    }




    private static void fun(String SAMPLE_JSON_RESPONSE, ArrayList<Earthquake> earthquakes)
    {
        try {
            JSONObject root=new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray earthquakeArray=root.getJSONArray("features");
            for(int i=0;i<earthquakeArray.length();++i)
            {
                JSONObject currentEarthquake=earthquakeArray.getJSONObject(i);
                JSONObject properties=currentEarthquake.getJSONObject("properties");
                Double magnitude=properties.getDouble("mag");
                String place=properties.getString("place");
                long time=properties.getLong("time");
                String url1=properties.getString("url");
                String timee=String.valueOf(time);
                earthquakes.add(new Earthquake(magnitude,place,timee,url1));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}