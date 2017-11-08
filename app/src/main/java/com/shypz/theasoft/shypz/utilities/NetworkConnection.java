package com.shypz.theasoft.shypz.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by H216104 on 11/5/2017.
 */

public class NetworkConnection {

        private ConnectivityManager cManager;
        public Context context;


        public NetworkConnection(Context applicationContext){
            this.context = applicationContext;
        }


        public boolean checkNetworkConnection(){
            cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cManager.getActiveNetworkInfo();

            if(nInfo != null && nInfo.isConnected()){
                return true;

            }else{

                return false;
            }

        }
}
