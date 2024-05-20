package com.iis.labourhealth.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.iis.labourhealth.R;
import com.iis.labourhealth.database.DOLDataSource;
import com.iis.labourhealth.model.DOLModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class SplashActivity extends Activity {

    String mDOLDivision = null;
    String mDOLName = null;
    String mDrName = null;
    String mDOLAddress = null;
    String mDOLEmail = null;
    String mDOLPhone = null;
    String mDOLLatitude = null;
    String mDOLLongitude = null;
    public Context mContext;
    DOLModel mDOLModel = null;
    DOLDataSource mDOLDataSource = null;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //window.setStatusBarColor(Color.BLUE);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        startSplash();


    }


    private void startSplash() {

        // Thread that will sleep for 2 seconds
        Thread mSplash = new Thread() {
            public void run() {
                try {
                    sleep(1 * 500);
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        mSplash.start();

        mDOLDataSource = new DOLDataSource(this);
        if (mDOLDataSource.isEmpty()) {
            // Start Parsing
            parseDOLXml();
        }
    }
    /**
     * this method parse the data from xml and insert into database ****
     */
    public void parseDOLXml() {
        XmlPullParser parser = getResources().getXml(R.xml.dol_information);
        try {
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }

                String name = parser.getName();

                if (name.equals("Content")) {
                    while (parser.next() != XmlPullParser.END_TAG) {
                        if (parser.getEventType() != XmlPullParser.START_TAG) {
                            continue;
                        }
                        name = parser.getName();

                        // WSMS Data
                        if (name.equals("DOLDivision")) {
                            mDOLDivision = readText(parser);
                        } else if (name.equals("DOLName")) {
                            mDOLName = readText(parser);
                        } else if (name.equals("DRName")) {
                            mDrName = readText(parser);
                        }else if (name.equals("DOLAddress")) {
                            mDOLAddress = readText(parser);
                        }else if (name.equals("DOLPhone")) {
                            mDOLPhone = readText(parser);
                        }else if (name.equals("DOLEmail")) {
                            mDOLEmail = readText(parser);
                        }else if (name.equals("DOLLatitude")) {
                            mDOLLatitude = readText(parser);
                        }else if (name.equals("DOLLongitude")) {
                            mDOLLongitude = readText(parser);
                        }
                    }

                    // Passing Data to Model Classes
                    mDOLModel = new DOLModel(mDOLName, mDrName, mDOLEmail, mDOLPhone, mDOLAddress, mDOLDivision, mDOLLatitude, mDOLLongitude);
                    mDOLDataSource.addDOL(mDOLModel);
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }





}
