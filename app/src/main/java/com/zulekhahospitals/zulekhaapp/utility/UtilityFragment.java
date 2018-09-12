package com.zulekhahospitals.zulekhaapp.utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.ImageView;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.imagecapture.ImageActivity;
import com.zulekhahospitals.zulekhaapp.sidebar.ComplaintFragment;

/**
 * Created by libin on 11/7/2016.
 */

public class UtilityFragment extends Activity implements View.OnClickListener {

    ImageView s_alm,bmi,b_card;
    ImageView back;
    @Override
       protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
      setContentView(R.layout.utility_layout);
        back= (ImageView) findViewById(R.id.back_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              UtilityFragment.this.goBack();
            }
        });
        s_alm= (ImageView) findViewById(R.id.set_alrm);
        bmi= (ImageView) findViewById(R.id.bm);
        b_card= (ImageView) findViewById(R.id.buis_card);
        s_alm.setOnClickListener(this);
        bmi.setOnClickListener(this);
        b_card.setOnClickListener(this);

    }

    private void goBack() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bm:
                Intent i = new Intent(getApplicationContext(),Bmi_Fragment.class);
             //   i.putExtra("current_but_id",0);
                startActivity(i);

                break;
            case R.id.set_alrm:
             /*   Intent i1 = new Intent(getApplicationContext(),Set_Remainder_Fragment.class);
               // i1.putExtra("current_but_id",1);
                startActivity(i1);*/
                try {
                    Intent openNewAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
                    openNewAlarm.putExtra(AlarmClock.EXTRA_MESSAGE, "ZH Alarm:");

                    startActivity(openNewAlarm);
                } catch (Exception e) {

                   /* Toast.makeText(this, "Install any Reminder/Alarm App.",
                            Toast.LENGTH_LONG).show();

*/
                    Snackbar.with(UtilityFragment.this,null)
                            .type(Type.ERROR)
                            .message("Install any Reminder/Alarm App")
                            .duration(Duration.LONG)

                            .show();
                }
                break;
            case R.id.buis_card:
                Intent ib = new Intent(getApplicationContext(),ImageActivity.class);
                //   i.putExtra("current_but_id",0);
                startActivity(ib);

                break;

        }

    }
}