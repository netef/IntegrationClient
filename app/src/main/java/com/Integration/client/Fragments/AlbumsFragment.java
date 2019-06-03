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
import com.Integration.client.AddNewGenreActivity;
import com.Integration.client.R;
import com.Integration.client.UpdateActivity;

public class AlbumsFragment extends Fragment {

    private Button addAlbumBtn;
    private Button addGenreBtn;
    private Button updateAlbumBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albums_slide, container, false);
        addAlbumBtn = view.findViewById(R.id.addalbumbtn);
        addGenreBtn = view.findViewById(R.id.addgenrebtn);
        updateAlbumBtn = view.findViewById(R.id.updatedetailsbtn);

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
        updateAlbumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpdateActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}