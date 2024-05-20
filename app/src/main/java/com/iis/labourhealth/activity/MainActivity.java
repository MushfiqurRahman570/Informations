package com.iis.labourhealth.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.iis.labourhealth.R;
import com.iis.labourhealth.adapter.CustomExpandableListAdapter;
import com.iis.labourhealth.database.DOLDataSource;
import com.iis.labourhealth.fragment.AllDOLMapViewFragment;
import com.iis.labourhealth.fragment.SingleWsmsFragment;
import com.iis.labourhealth.model.DOLModel;
import com.iis.labourhealth.util.AppSettings;
import com.iis.labourhealth.util.AppsSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends FragmentActivity {


    public static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 100;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    public Context mContext;
    public Activity mActivity;
    private DrawerLayout mDrawerLayout;
    ImageView home;
    ImageView frargmentHome;
    ImageView frargmentInfo;
    Fragment fragment = null;
    TextView appname = null;
    ExpandableListView expListView = null;
    CustomExpandableListAdapter listAdapter = null;

    DOLModel mDOLModel = null;
    DOLDataSource mDOLDataSource = null;

    ArrayList<DOLModel> mDOLDhakList = null;
    ArrayList<DOLModel> mDOLMymensinghList = null;
    ArrayList<DOLModel> mDOLChittagongList = null;
    ArrayList<DOLModel> mDOLRajshahiList = null;
    ArrayList<DOLModel> mDOLRangpurList = null;
    ArrayList<DOLModel> mDOLKhulnaList = null;
    ArrayList<DOLModel> mDOLBarishalList = null;
    ArrayList<DOLModel> mDOLShyletList = null;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mActivity = this;
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        home = (ImageView) findViewById(R.id.home);
        frargmentInfo = (ImageView) findViewById(R.id.fragmentInfo);
        frargmentHome = (ImageView) findViewById(R.id.fragmenthome);
        home.setImageResource(R.drawable.phome);
        home.setOnClickListener(homeOnclickListener);
        appname = (TextView) findViewById(R.id.appname);

        frargmentInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(expListView)) {
                    mDrawerLayout.closeDrawer(expListView);
                }
                Intent i = new Intent(mActivity, AboutActivity.class);
                startActivity(i);
            }
        });

        frargmentHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(expListView)) {
                    mDrawerLayout.closeDrawer(expListView);
                }
                fragment = new AllDOLMapViewFragment();
                frargmentHome.setVisibility(View.GONE);
                frargmentInfo.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmetnContainer, fragment).commit();


            }
        });

        setUpDrawer();
    }

    private void setUpDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        mDrawerLayout.setDrawerListener(mDrawerListener);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new CustomExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        autoLoadMap();
//        }
    }

    private void autoLoadMap() {
        fragment = new AllDOLMapViewFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmetnContainer, fragment).commit();

        try {
            Thread.sleep(3000);
            if (AppSettings.getInstance(mContext).getFirstTimeBoot()) {
                if (Build.VERSION.SDK_INT >= 23) {
                    chkPermissions();
                    if (!isGPSEnabled) {
                        if (!isGPSEnabled) {
                            showGPSSettingsAlert();
                        }
                    } else {

                    }
                } else {
                    if (!isGPSEnabled) {
                        if (!isGPSEnabled) {
                            showGPSSettingsAlert();
                        }
                    } else {

                    }
                }

            } else {
                alertPermission();
            }
        } catch (Exception e) {

        }


        mDrawerLayout.closeDrawer(expListView);

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup) {
                    expListView.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Integer mListDOLId = null;
                expListView.clearFocus();
                switch (groupPosition) {
                    case 0:
                        mListDOLId = mDOLDhakList.get(childPosition).getmDOLId();
                        callAnotherFragment(mListDOLId);
                        break;

                    case 1:
                        mListDOLId = mDOLMymensinghList.get(childPosition).getmDOLId();
                        callAnotherFragment(mListDOLId);
                        break;
                    case 2:
                        mListDOLId = mDOLChittagongList.get(childPosition).getmDOLId();
                        callAnotherFragment(mListDOLId);
                        break;

                    case 3:
                        mListDOLId = mDOLRajshahiList.get(childPosition).getmDOLId();
                        callAnotherFragment(mListDOLId);
                        break;

                    case 4:
                        mListDOLId = mDOLKhulnaList.get(childPosition).getmDOLId();
                        callAnotherFragment(mListDOLId);
                        break;

                    case 5:
                        mListDOLId = mDOLShyletList.get(childPosition).getmDOLId();
                        callAnotherFragment(mListDOLId);
                        break;

                    case 6:
                        mListDOLId = mDOLBarishalList.get(childPosition).getmDOLId();
                        callAnotherFragment(mListDOLId);
                        break;
                    case 7:
                        mListDOLId = mDOLRangpurList.get(childPosition).getmDOLId();
                        callAnotherFragment(mListDOLId);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmetnContainer, fragment).commit();
                mDrawerLayout.closeDrawer(expListView);
                return false;
            }
        });
    }

    // Intent to another fragment
    public void callAnotherFragment(Integer eListWsmsId) {
        // Parsing Id integer to String
        String myWsmsId = String.valueOf(eListWsmsId);
        //Put the value
        fragment = new SingleWsmsFragment();
        Bundle args = new Bundle();
        args.putString("dolId", myWsmsId);
        fragment.setArguments(args);

        // Home Button Visibility
        frargmentHome.setVisibility(View.VISIBLE);

        // Info Button Visibility
        frargmentInfo.setVisibility(View.GONE);

        //Inflate the fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmetnContainer, fragment).commit();
        mDrawerLayout.closeDrawer(expListView);

    }

    View.OnClickListener homeOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mDrawerLayout.isDrawerOpen(expListView)) {
                mDrawerLayout.closeDrawer(expListView);
            } else {
                mDrawerLayout.openDrawer(expListView);
            }
        }
    };


    private DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {

        @Override
        public void onDrawerStateChanged(int status) {

        }

        @Override
        public void onDrawerSlide(View view, float slideArg) {

        }

        @Override
        public void onDrawerOpened(View view) {
            home.setImageResource(R.drawable.close);
        }

        @Override
        public void onDrawerClosed(View view) {
            home.setImageResource(R.drawable.phome);
        }
    };


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding Header data
        listDataHeader.add("ঢাকা বিভাগ");
        listDataHeader.add("ময়মনসিংহ বিভাগ");
        listDataHeader.add("চট্টগ্রাম বিভাগ");
        listDataHeader.add("রাজশাহী বিভাগ");
        listDataHeader.add("খুলনা বিভাগ");
        listDataHeader.add("সিলেট বিভাগ");
        listDataHeader.add("বরিশাল বিভাগ");
        listDataHeader.add("রংপুর বিভাগ");

        // DataSource
        mDOLDataSource = new DOLDataSource(this);

        mDOLDhakList = mDOLDataSource.getAllDOLListByDivision("Dhaka");
        mDOLMymensinghList = mDOLDataSource.getAllDOLListByDivision("Mymensingh");
        mDOLChittagongList = mDOLDataSource.getAllDOLListByDivision("Chittagong");
        mDOLRajshahiList = mDOLDataSource.getAllDOLListByDivision("Rajshahi");
        mDOLKhulnaList = mDOLDataSource.getAllDOLListByDivision("Khulna");
        mDOLShyletList = mDOLDataSource.getAllDOLListByDivision("Sylhet");
        mDOLRangpurList = mDOLDataSource.getAllDOLListByDivision("Rangpur");
        mDOLBarishalList = mDOLDataSource.getAllDOLListByDivision("Barisal");

        List<String> dhakaChild = new ArrayList<String>();
        for (int i = 0; i < mDOLDhakList.size(); i++) {
            dhakaChild.add(mDOLDhakList.get(i).getmDOLName());

        }

        List<String> mymensinghChild = new ArrayList<String>();
        for (int i = 0; i < mDOLMymensinghList.size(); i++) {
            mymensinghChild.add(mDOLMymensinghList.get(i).getmDOLName());

        }

        List<String> chittagongChild = new ArrayList<String>();
        for (int i = 0; i < mDOLChittagongList.size(); i++) {
            chittagongChild.add(mDOLChittagongList.get(i).getmDOLName());
        }

        List<String> rajshahiChild = new ArrayList<String>();
        for (int i = 0; i < mDOLRajshahiList.size(); i++) {
            //Log.d("shylet child name: ",mDOLShyletList.get(i).getmDOLName());
            rajshahiChild.add(mDOLRajshahiList.get(i).getmDOLName());
        }

        List<String> rangpurChild = new ArrayList<String>();
        for (int i = 0; i < mDOLRangpurList.size(); i++) {
            rangpurChild.add(mDOLRangpurList.get(i).getmDOLName());
        }

        List<String> khulnaChild = new ArrayList<String>();
        for (int i = 0; i < mDOLKhulnaList.size(); i++) {
            khulnaChild.add(mDOLKhulnaList.get(i).getmDOLName());
        }

        List<String> barishalChild = new ArrayList<String>();
        for (int i = 0; i < mDOLBarishalList.size(); i++) {
            barishalChild.add(mDOLBarishalList.get(i).getmDOLName());
        }

        List<String> shyletChild = new ArrayList<String>();
        for (int i = 0; i < mDOLShyletList.size(); i++) {
            shyletChild.add(mDOLShyletList.get(i).getmDOLName());
        }

        listDataChild.put(listDataHeader.get(0), dhakaChild);
        listDataChild.put(listDataHeader.get(1), mymensinghChild);// Header, Child data
        listDataChild.put(listDataHeader.get(2), chittagongChild);
        listDataChild.put(listDataHeader.get(3), rajshahiChild);
        listDataChild.put(listDataHeader.get(4), khulnaChild);
        listDataChild.put(listDataHeader.get(5), shyletChild);
        listDataChild.put(listDataHeader.get(6), barishalChild);
        listDataChild.put(listDataHeader.get(7), rangpurChild);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit ")
                .setMessage("Are you sure to close?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    /**
     * Function to show settings alert dialog
     * On pressing Settings button will launch Settings Options
     */
    public void showGPSSettingsAlert() {
        new AlertDialog.Builder(this)
                .setTitle("লোকেশন সেটিংস")
                .setMessage("অনুগ্রহ পূর্বক সেটিংস অপসন থেকে লোকেশন ম্যানেজার অন করুন")
                .setPositiveButton("সেটিংস", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("নো", null)
                .show();
    }

    private void chkPermissions() {

        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("Location");


        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);


                ActivityCompat.requestPermissions((Activity) mContext, permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);

                return;
            }
            ActivityCompat.requestPermissions((Activity) mContext, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        } else {
        }


    }


    private void alertPermission() {
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle("নির্দেশনা!!");
        alert.setMessage("অনুগ্রহ পূর্বক, মানচিত্র দেখার জন্য আপনার ডিভাইসে ইন্টারনেট আছে কিনা চেক করুন এবং গুগল প্লে পরিষেবা আপ-টু-ডেট আছে কিনা তা নিশ্চিত করুন।");
        alert.setPositiveButton("ঠিক আছে", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppSettings.getInstance(mContext).setFirstTimeBoot(true);
                dialog.dismiss();
                chkPermissions();
                if (!isGPSEnabled) {
                    if (!isGPSEnabled) {
                        showGPSSettingsAlert();
                    }
                }

            }
        });
        alert.create();
        alert.show();
    }


    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ActivityCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, permission))
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);

                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    Toast.makeText(mContext, "Permission Granted", Toast.LENGTH_SHORT)
                            .show();
                    AppsSettings.getAppsSettings(mContext).setAllPermissonAllow(true);
                    try {
                        Thread.sleep(1000);
                        // autoLoadMap();
                    } catch (Exception e) {

                    }

                } else {
                    // Permission Denied
                    Toast.makeText(mContext, "Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

}
