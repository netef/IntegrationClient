package com.Integration.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddNewGenreActivity extends AppCompatActivity {

    private EditText name;
    private ImageView addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_genre);

        name = findViewById(R.id.name);
        addBtn = findViewById(R.id.addbtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewGenre();
            }
        });

    }

    void addNewGenre() {

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

            JSONObject request = new JSONObject();
            try {
                request.put("key", key);
                request.put("elementType", "genre");

                //need to fix name
                request.put("name", name.getText().toString());
                request.put("expired", false);
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