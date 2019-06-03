package com.Integration.client;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class SearchFragment extends AppCompatActivity {

    private EditText x;
    private EditText y;
    private Spinner type;
    private EditText name;
    private ImageView searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_fragment);

        x = findViewById(R.id.x);
        y = findViewById(R.id.y);
        type = findViewById(R.id.typespinner);
        name = findViewById(R.id.name);
        searchBtn = findViewById(R.id.searchbtn);


    }
}
