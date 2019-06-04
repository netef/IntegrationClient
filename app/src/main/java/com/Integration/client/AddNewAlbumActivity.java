package com.Integration.client;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Integration.client.Boundaries.Creator;
import com.Integration.client.Boundaries.ElementKey;
import com.Integration.client.Boundaries.Latlng;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNewAlbumActivity extends AppCompatActivity {

    private EditText name;
    private EditText band;
    private Button releaseDate;
    private EditText preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_album);


        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewAlbumActivity.this,
                android.R.style.Theme_Material_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        releaseDate.setText(String.format("%d/%d/%d", dayOfMonth, month + 1, year));
                    }
                },
                year,
                month,
                day);

        name = findViewById(R.id.name);
        band = findViewById(R.id.band);
        releaseDate = findViewById(R.id.releasedate);
        preview = findViewById(R.id.preview);
        ImageView addBtn = findViewById(R.id.addbtn);

        releaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewAlbum();
            }
        });
    }

    void addNewAlbum() {

        ElementKey elementKey=new ElementKey("inbala1");
        Creator creatortemp=new Creator(getSharedPreferences(getPackageName(),
                MODE_PRIVATE).getString("email", ""),
                getSharedPreferences(getPackageName(),
                        MODE_PRIVATE).getString("smartspace", ""));

        JSONObject key = new JSONObject();

        try {
            key.put("id", elementKey.getId());
            key.put("smartspace", elementKey.getSmartspace());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject creator = new JSONObject();

        try {
            key.put("email", creatortemp.getEmail());
            key.put("smartspace", creatortemp.getSmartspace());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Latlng latlangtemp  = new Latlng(1.0,1.0);
        Gson gson = new Gson();
        gson.toJson(latlangtemp);

        JSONObject latlng = new JSONObject();

        try {
            key.put("lat",latlangtemp.getLat());
            key.put("lng", latlangtemp.getLng());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject elementProperties = new JSONObject();

        try {
            elementProperties.put("band", band.getText().toString());
            elementProperties.put("releasedate", releaseDate.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject request = new JSONObject();
        try {
            request.put("key", key);
            request.put("name", name.getText().toString());
            request.put("elementType", "Album");
            request.put("creator", creator);
            request.put("latlng", latlng);
            request.put("elementProperties", elementProperties);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String URL = "http://" + getString(R.string.ip) + ":8087/smartspace/elements/"
                + getSharedPreferences(getPackageName(),
                MODE_PRIVATE).getString("smartspace", "") + "/" + getSharedPreferences(getPackageName(),
                MODE_PRIVATE).getString("email", "");

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}