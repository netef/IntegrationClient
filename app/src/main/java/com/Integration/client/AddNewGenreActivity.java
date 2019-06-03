package com.Integration.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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


        Map<String, Object> creator = new HashMap<>();
        Map<String, Object> latlng = new HashMap<>();
        Map<String, Object> elementProperties = new HashMap<>();

        creator.put("email", getSharedPreferences(getPackageName(),
                MODE_PRIVATE).getString("email", ""));
        creator.put("smartspace", getSharedPreferences(getPackageName(),
                MODE_PRIVATE).getString("smartspace", ""));

        //just for testing
        latlng.put("lat", 1);
        latlng.put("lng", 1);

        elementProperties.put("genrename", name.getText().toString());


        JSONObject request = new JSONObject();
        try {
            request.put("elementType", "genre");

            //need to fix name
            request.put("name", "Show");

            request.put("creator", creator);
            request.put("latlng", latlng);
            request.put("elementProperties", elementProperties);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String URL = "http://" + getString(R.string.ip) + ":8087/smartspace/elements";

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