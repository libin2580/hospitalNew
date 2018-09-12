package com.zulekhahospitals.zulekhaapp.sidebar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.zulekhahospitals.zulekhaapp.R;

/**
 * Created by libin on 11/8/2016.
 */

public class Tabbar_Activity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabbar_layout);
    }
}