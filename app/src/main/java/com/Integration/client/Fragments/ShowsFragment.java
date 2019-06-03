package com.Integration.client.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.Integration.client.AddNewAlbumActivity;
import com.Integration.client.AddNewShowActivity;
import com.Integration.client.R;
import com.Integration.client.SearchFragment;
import com.Integration.client.UpdateActivity;

public class ShowsFragment extends Fragment {

    private Button addShowBtn;
    private Button updateShowBtn;
    private Button search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shows_slide, container, false);
        addShowBtn = view.findViewById(R.id.addshowbtn);
        updateShowBtn = view.findViewById(R.id.updateshowbtn);
        search = view.findViewById(R.id.searchbtn);

        addShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddNewShowActivity.class);
                startActivity(intent);
            }
        });

        updateShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpdateActivity.class);
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
}