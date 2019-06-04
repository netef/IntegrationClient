package com.Integration.client.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.Integration.client.AddNewAlbumActivity;
import com.Integration.client.AddNewGenreActivity;
import com.Integration.client.AlbumUpdateActivity;
import com.Integration.client.R;
import com.Integration.client.SearchFragment;
import com.Integration.client.TabsActivity;
import com.Integration.client.UpdateActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class AlbumsFragment extends Fragment {

    private Button addAlbumBtn;
    private Button addGenreBtn;
    private Button updateUserBtn;
    private Button updateAlbumBtn;
    private Button search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albums_slide, container, false);
        addAlbumBtn = view.findViewById(R.id.addalbumbtn);
        addGenreBtn = view.findViewById(R.id.addgenrebtn);
        updateUserBtn = view.findViewById(R.id.updatedetailsbtn);
        updateAlbumBtn = view.findViewById(R.id.updatealbumbtn);
        search = view.findViewById(R.id.searchbtn);

        loadAlbumsFromServers();

        addAlbumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddNewAlbumActivity.class);
                startActivity(intent);
            }
        });
        addGenreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddNewGenreActivity.class);
                startActivity(intent);
            }
        });
        updateUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpdateActivity.class);
                startActivity(intent);
            }
        });
        updateAlbumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AlbumUpdateActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchFragment.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadAlbumsFromServers() {

        String URL = "http://" + getString(R.string.ip) + ":8087/smartspace/elements/"
                + this.getActivity().getSharedPreferences(this.getActivity().getPackageName(),
                MODE_PRIVATE).getString("smartspace", "") + "/"
                + this.getActivity().getSharedPreferences(this.getActivity().getPackageName(),
                MODE_PRIVATE).getString("email", "") +
                "?search=Album&value=Album$page=0&size=0";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList<JSONObject> albums = new ArrayList<>();
                            albums.add(response);
                            for(JSONObject album : response)
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){

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