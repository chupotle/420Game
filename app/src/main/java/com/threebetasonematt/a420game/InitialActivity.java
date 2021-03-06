package com.threebetasonematt.a420game;

/*
Handles logging in and starting a game.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InitialActivity extends AppCompatActivity {

    public static final String TAG = "InitialActivity";

    Button mPlayButton;
    EditText mEnterUsername;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("com.threebetasonematt.a420game", MODE_PRIVATE);


        //get reference for username box
        mEnterUsername = (EditText)findViewById(R.id.edittext_enter_username);

        //handle play button
        mPlayButton = (Button)findViewById(R.id.button_splash_play);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEnterUsername.getText().toString();
                if(username.equals("") || username.equals(null)){
                    //display error if the username is bad
                    Toast.makeText(InitialActivity.this, R.string.blank_username_error, Toast.LENGTH_SHORT).show();
                }
                else{
                    //open next activity
                    Intent intent = new Intent(InitialActivity.this, StartGameActivity.class);
                    intent.putExtra(constants.KEY_USERNAME, username);
                    SocketHandler.username = username;
                    startActivity(intent);
                }
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();

        if(prefs.getBoolean("firstrun", true)){
            //check for barometer, kick out of app if none
            if(!barometerCheck()){
                Toast.makeText(InitialActivity.this, "Your phone doesn't have a barometer and so this game won't work properly. Sorry.", Toast.LENGTH_LONG).show();
            }

            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }

    //checks whether the decive has a barometer on it, this game requires one
    public boolean barometerCheck(){
        //do check for barometer
        PackageManager manager = getPackageManager();
        boolean hasBarometer = manager.hasSystemFeature(PackageManager.FEATURE_SENSOR_BAROMETER);
        return hasBarometer;
    }
}
