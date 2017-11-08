package com.shypz.theasoft.shypz;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shypz.theasoft.shypz.utilities.NetworkConnection;

public class SplashActivity extends AppCompatActivity {

    private ConnectivityManager cManager;
    private NetworkConnection ntConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Button signUpBtn = (Button)findViewById(R.id.signUpButton);
        Button signInBtn = (Button)findViewById(R.id.signInButton);





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
            signUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                    startActivity(intent);
                    finish();
                }
            });


        }else{
            Toast.makeText(this,"Network is not available",Toast.LENGTH_LONG).show();
        }
    }


}