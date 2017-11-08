package com.shypz.theasoft.shypz.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.shypz.theasoft.shypz.utilities.NetworkConnection;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        boolean test = new NetworkConnection(context).checkNetworkConnection();

        if(test){
            Toast.makeText(context,"Network is available",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"Network is not available",Toast.LENGTH_LONG).show();
        }
    }
}
