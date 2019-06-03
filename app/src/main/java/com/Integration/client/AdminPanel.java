package com.Integration.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminPanel extends AppCompatActivity implements View.OnClickListener {

    private ImageView importUsers;
    private ImageView importElements;
    private ImageView importActions;

    private ImageView exportUsers;
    private ImageView exportElements;
    private ImageView exportActions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        importUsers = findViewById(R.id.importusersbtn);
        importElements = findViewById(R.id.importelementsbtn);
        importActions = findViewById(R.id.importactionsbtn);

        importUsers = findViewById(R.id.exportusersbtn);
        importUsers = findViewById(R.id.exportelementsbtn);
        importUsers = findViewById(R.id.exportactionsbtn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.importusersbtn:
            {
                break;
            }

            case R.id.importelementsbtn:
            {
                break;
            }

            case R.id.importactionsbtn:
            {
                break;
            }


            case R.id.exportusersbtn:
            {
                break;
            }

            case R.id.exportelementsbtn:
            {
                break;
            }

            case R.id.exportactionsbtn:
            {
                break;
            }
        }
    }

    void somethingServer(){

    }
}