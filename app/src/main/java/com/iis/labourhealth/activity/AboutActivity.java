package com.iis.labourhealth.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iis.labourhealth.R;

public class AboutActivity extends Activity {
    public Context mContext;
    public ImageView backToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about_activity);
        mContext = this;
        backToHome = (ImageView) findViewById(R.id.backToHome);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView companydetails=(TextView)findViewById(R.id.link);
        companydetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://inovexidea.com")));
                finish();
            }
        });

    }


    /**
     * Get the current package version.
     *
     * @return The current version.
     */
    private String getVersion() {
        String result = "";
        try {

            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);

            result = String.format("%s (%s)", info.versionName, info.versionCode);

        } catch (PackageManager.NameNotFoundException e) {
            result = "Unable to get application version.";
        }

        return result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();


    }

}

