package com.Integration.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateShowActivity extends AppCompatActivity {

    private EditText numberOfTickets;
    private EditText relatedToAlbum;
    private ImageView updateBtn;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_update);


        bundle = getIntent().getExtras();
        numberOfTickets = findViewById(R.id.numoftickets);
        relatedToAlbum = findViewById(R.id.relatedtoalbum);
        updateBtn = findViewById(R.id.updatebtn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAlbum();
            }
        });

        Log.e("id", bundle.getString("id"));
    }

    void updateAlbum() {

        JSONObject key = new JSONObject();
        try {
            key.put("smartspace", getSharedPreferences(getPackageName(),
                    MODE_PRIVATE).getString("smartspace", ""));
            key.put("email", getSharedPreferences(getPackageName(),
                    MODE_PRIVATE).getString("email", ""));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject elementProperties = new JSONObject();

        try {
            elementProperties.put("numberoftickets", numberOfTickets.getText().toString());
            elementProperties.put("relatedtoalbum", relatedToAlbum.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String email = getSharedPreferences(getPackageName(), MODE_PRIVATE).getString("email", "");

        JSONObject request = new JSONObject();
        try {
            request.put("key",key);
            request.put("elementType", "Show");
            request.put("elementProperties", elementProperties);
        } catch (Exception e) {
            e.printStackTrace();
        }


        String URL = "http://" + getString(R.string.ip) + ":8087/smartspace/elements/"
                + getSharedPreferences(getPackageName(),
                MODE_PRIVATE).getString("smartspace", "") + "/" +
                email +
                getSharedPreferences(getPackageName(),
                        MODE_PRIVATE).getString("smartspace", "") +
                bundle.getString("id");

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                URL,
                request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(UpdateShowActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateShowActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}