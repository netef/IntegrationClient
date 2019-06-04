package com.Integration.client.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Integration.client.AddNewShowActivity;
import com.Integration.client.R;
import com.Integration.client.SearchFragment;
import com.Integration.client.UpdateActivity;
import com.Integration.client.UpdateShowActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ShowsFragment extends Fragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shows_slide, container, false);

        listView = view.findViewById(R.id.listview);
        Button addShowBtn = view.findViewById(R.id.addshowbtn);
        Button search = view.findViewById(R.id.searchbtn);
        Button updateUser = view.findViewById(R.id.updatedetailsbtn);

        loadShowsFromServer();

        addShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddNewShowActivity.class);
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

        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadShowsFromServer() {

        String URL = "http://" + getString(R.string.ip) + ":8087/smartspace/elements/"
                + this.getActivity().getSharedPreferences(this.getActivity().getPackageName(),
                MODE_PRIVATE).getString("smartspace", "") + "/"
                + this.getActivity().getSharedPreferences(this.getActivity().getPackageName(),
                MODE_PRIVATE).getString("email", "") +
                "?search=Show&value=Show$page=0&size=10";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        final ArrayList<JSONObject> shows = new ArrayList<>();
                        final ArrayList<String> strings = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++){
                            try {
                                shows.add(response.getJSONObject(i));
                                strings.add(shows.get(i).getString("name") + "\n" +
                                        shows.get(i).getJSONObject("elementProperties").getString("numberoftickets") + "\n"
                                + shows.get(i).getJSONObject("elementProperties").getString("relatedtoalbum"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, strings){

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view =super.getView(position, convertView, parent);

                                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                                textView.setTextColor(Color.WHITE);
                                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                textView.setTextSize(30);

                                return view;
                            }
                        };

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                                new AlertDialog.Builder(getContext())
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle(" ")
                                        .setPositiveButton("Update Show", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(getContext(), UpdateShowActivity.class);
                                                try {
                                                    intent.putExtra("id", shows.get(position).getJSONObject("key").getString("id"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                startActivity(intent);
                                            }
                                        })
                                        .setNeutralButton("Check in/out", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(view.getTag() == null)
                                                {
                                                    view.setBackgroundColor(Color.BLUE);
                                                    view.setTag("0");
                                                }

                                                else if(view.getTag().equals("0"))
                                                {
                                                    view.setBackgroundColor(Color.TRANSPARENT);
                                                    view.setTag("1");
                                                }
                                                else if(view.getTag().equals("1")){
                                                    view.setBackgroundColor(Color.BLUE);
                                                    view.setTag("0");
                                                }
                                            }
                                        })
                                        .setNegativeButton("Delete Show", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .create()
                                        .show();

                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
        requestQueue.add(jsonArrayRequest);
    }
}