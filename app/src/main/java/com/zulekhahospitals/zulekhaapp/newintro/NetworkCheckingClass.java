package com.zulekhahospitals.zulekhaapp.newintro;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by libin on 11/3/2016.
 */
public class NetworkCheckingClass {
    Context context;
    public NetworkCheckingClass(Context con) {
        this.context=con;

    }

    public boolean ckeckinternet() {

        ConnectivityManager conMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;

    }
}
