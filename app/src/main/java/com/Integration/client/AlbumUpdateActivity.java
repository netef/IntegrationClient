package com.Integration.client;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AlbumUpdateActivity extends AppCompatActivity {

    //הגדרות יבשות של דברים
    private EditText name;
    private EditText band;
    private Button releaseDate;
    private ImageView updateBtn;

    //שנייה מסביר לך מה זה
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_update);

//:)
        bundle = getIntent().getExtras();
        name = findViewById(R.id.name);
        band = findViewById(R.id.band);
        releaseDate = findViewById(R.id.releasedate);
        updateBtn = findViewById(R.id.updatebtn);

        //כל זה בשביל לוח שנה, זה רק באלבומים אל תתייחס לזה
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(AlbumUpdateActivity.this,
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

        releaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAlbum();
            }
        });

    }

    void updateAlbum() {

        //זה הKEY שיהיה בהתחלה של הJSON
        JSONObject key = new JSONObject();
        try {
            key.put("smartspace", getSharedPreferences(getPackageName(),
                    MODE_PRIVATE).getString("smartspace", ""));
            key.put("email", getSharedPreferences(getPackageName(),
                    MODE_PRIVATE).getString("email", ""));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //זה כל התכונות של האלבום, שם, להקה...
        JSONObject elementProperties = new JSONObject();

        try {
            elementProperties.put("band", band.getText().toString());
            elementProperties.put("releasedate", releaseDate.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String email = getSharedPreferences(getPackageName(), MODE_PRIVATE).getString("email", "");

        //פה כל שאר הדברים כמו הKEY ואיזה סוג של מה זה
        JSONObject request = new JSONObject();
        try {
            request.put("key",key);
            request.put("elementType", "Album");
            request.put("name", name.getText().toString());
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
                        Toast.makeText(AlbumUpdateActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AlbumUpdateActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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