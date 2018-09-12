package com.zulekhahospitals.zulekhaapp.utility;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zulekhahospitals.zulekhaapp.R;

/**
 * Created by libin on 11/7/2016.
 */
public class Bmi_Fragment extends Activity{
    EditText bmi_h, bmi_w;
    Button bmi_sub;
    TextView bmi_out, bmi_status;
    ImageView back;
    LinearLayout nav_icon;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.bmi_layout);
            back= (ImageView) findViewById(R.id.back_image);
           /* back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   Bmi_Fragment.this.goBack();
                }
            });*/
            bmi_h = (EditText)findViewById(R.id.bmi_height);
            bmi_w = (EditText) findViewById(R.id.bmi_weight);
            bmi_sub = (Button)findViewById(R.id.submit_bmi);
            bmi_out = (TextView)findViewById(R.id.bmi_output);
            bmi_status = (TextView)findViewById(R.id.bmi_status);
            nav_icon=(LinearLayout)findViewById(R.id.nav_icon);

            nav_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            bmi_sub.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    try {
                        InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);

                        String h = bmi_h.getText().toString();
                        String w = bmi_w.getText().toString();

                        Double dh = 0.00, dw = 0.00;
                        if (h != "" && h.length() > 0)
                            dh = Double.parseDouble(h);

                        if (w != "" && w.length() > 0)
                            dw = Double.parseDouble(w);

                        double BMI = dw / (dh * dh) * 10000;
                        double finalValue = Math.round(BMI * 100.0) / 100.0;

                        bmi_out.setText("Your BMI is " + finalValue + "");

                        String status = "Normal";
                        if (finalValue >= 18.5 && finalValue <= 24.9) {
                            status = "Normal";

                        } else if (finalValue >= 25 && finalValue < 30) {
                            status = "Over Weight";

                        } else if (finalValue >= 30) {
                            status = "Obesity";
                        }
                        bmi_status.setText(status);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }

    private void goBack() {
        super.onBackPressed();
    }
}

