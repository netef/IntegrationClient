package com.Integration.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    private EditText userName;
    private EditText avatar;
    private Spinner role;
    private ImageView updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ArrayList<String> roles = new ArrayList<>();
        roles.add("PLAYER");
        roles.add("MANAGER");
        roles.add("ADMIN");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, roles);


        userName = findViewById(R.id.username);
        avatar = findViewById(R.id.avatar);
        updateBtn = findViewById(R.id.updatebtn);

        role = findViewById(R.id.role);
        role.setAdapter(adapter);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }

    void updateUser() {

        String email = getSharedPreferences(getPackageName(), MODE_PRIVATE).getString("email", "");
        JSONObject request = new JSONObject();
        try {
            request.put("email", email);
            request.put("role", role.getSelectedItem().toString());
            request.put("username", userName.getText().toString());
            request.put("avatar", avatar.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String URL = "http://" + getString(R.string.ip) + ":8087/smartspace/users";

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                URL,
                request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(UpdateActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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