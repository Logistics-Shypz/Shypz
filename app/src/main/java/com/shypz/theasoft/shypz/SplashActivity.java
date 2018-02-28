package com.shypz.theasoft.shypz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shypz.theasoft.shypz.utilities.NetworkConnection;
import com.shypz.theasoft.shypz.utilities.SessionManager;

public class SplashActivity extends AppCompatActivity {

    private ConnectivityManager cManager;
    private NetworkConnection ntConn;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Button signUpBtn = (Button)findViewById(R.id.signUpButton);
        Button signInBtn = (Button)findViewById(R.id.signInButton);

        session = new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();





        ntConn = new NetworkConnection(this);

        boolean nc_check = ntConn.checkNetworkConnection();

        if(nc_check) {


            /*Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(7000);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            t.start();*/

            if(session.isLoggedIn()){
                Intent intent = new Intent(getApplicationContext(),ShypzHome.class);
                startActivity(intent);
                finish();
            }else {

                signUpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                signInBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }


        }else{
            Toast.makeText(this,"Network is not available",Toast.LENGTH_LONG).show();
        }
    }


}
