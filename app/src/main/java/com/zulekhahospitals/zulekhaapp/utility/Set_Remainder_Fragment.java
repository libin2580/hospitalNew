package com.zulekhahospitals.zulekhaapp.utility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zulekhahospitals.zulekhaapp.R;

/**
 * Created by libin on 11/7/2016.
 */

public class Set_Remainder_Fragment extends Activity {
    Button date, time, submit;
    EditText message;
    String date_ = "", time_ = "", message_ = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_reminder);

        date = (Button) findViewById(R.id.date_pick);
        message = (EditText)findViewById(R.id.message);
        time = (Button)findViewById(R.id.time_pick);
        submit = (Button)findViewById(R.id.set_but);

        submit.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View arg0) {

                String data = message.getText().toString();
                // TODO Auto-generated method stub
                Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
                openNewAlarm.putExtra(AlarmClock.EXTRA_MESSAGE, "ZH Alarm:"
                        + data);

                startActivity(openNewAlarm);
            }
        });
    }
}

