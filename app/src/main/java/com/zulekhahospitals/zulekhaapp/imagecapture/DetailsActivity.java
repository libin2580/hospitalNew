package com.zulekhahospitals.zulekhaapp.imagecapture;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.zulekhahospitals.zulekhaapp.R;
import com.zulekhahospitals.zulekhaapp.edu_leaflet.Educational_leafle_pdf_Fragment;

import static com.zulekhahospitals.zulekhaapp.utility.ZHconstansts.analytics;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        analytics = FirebaseAnalytics.getInstance(DetailsActivity.this);
        analytics.setCurrentScreen(DetailsActivity.this,DetailsActivity.this.getLocalClassName(), null /* class override */);

        String title = getIntent().getStringExtra("title");
        Bitmap bitmap = getIntent().getParcelableExtra("image");

        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(title);

        ImageView imageView = (ImageView) findViewById(R.id.imgView3);

        imageView.setImageBitmap(bitmap);
    }
}
