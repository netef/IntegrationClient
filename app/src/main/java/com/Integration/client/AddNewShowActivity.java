package com.Integration.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Integration.client.Boundaries.Creator;
import com.Integration.client.Boundaries.Latlng;
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

public class AddNewShowActivity extends AppCompatActivity {

    private EditText numberOfTickets;
    private EditText location;
    private EditText relatedToAlbum;
    private EditText preview;
    private ImageView addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_show);

        numberOfTickets = findViewById(R.id.numoftickets);
        location = findViewById(R.id.location);
        relatedToAlbum = findViewById(R.id.relatedtoalbum);
        preview = findViewById(R.id.preview);
        addBtn = findViewById(R.id.addbtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewShow();
            }
        });
    }

    void addNewShow() {

        Creator creator = new Creator(getSharedPreferences(getPackageName(),
                MODE_PRIVATE).getString("email", ""),
                getSharedPreferences(getPackageName(),
                        MODE_PRIVATE).getString("smartspace", ""));

        Latlng latlng = new Latlng(1, 1);


        Map<String, Object> elementProperties = new HashMap<>();
        elementProperties.put("numberOfTickets", numberOfTickets.getText().toString());
        elementProperties.put("location", location.getText().toString());
        elementProperties.put("relatedToAlbum", relatedToAlbum.getText().toString());

        JSONObject request = new JSONObject();
        try {
            request.put("elementType", "Show");

            //need to fix name
            request.put("name", "Show");
            request.put("expired", false);
            request.put("creator", creator);
            request.put("latlng", latlng);
            request.put("elementProperties", elementProperties);
            request.put("preview", preview.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String URL = "http://" + getString(R.string.ip) + ":8087/smartspace/elements/"
                + creator.getSmartspace() + "/" + creator.getEmail();

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