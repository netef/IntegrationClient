package com.Integration.client;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.Integration.client.Fragments.AlbumsFragment;
import com.Integration.client.Boundaries.PageAdapter;
import com.Integration.client.Fragments.ShowsFragment;

public class TabsActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        Log.d(TAG, "onCreate: Starting.");

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AlbumsFragment(), "Albums");
        adapter.addFragment(new ShowsFragment(), "Shows");
        viewPager.setAdapter(adapter);
    }
}