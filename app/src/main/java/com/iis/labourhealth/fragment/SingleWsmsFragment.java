package com.iis.labourhealth.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iis.labourhealth.R;
import com.iis.labourhealth.database.DOLDataSource;
import com.iis.labourhealth.model.DOLModel;
import com.iis.labourhealth.util.Functions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SingleWsmsFragment extends Fragment {

    public static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 100;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    public Activity mActivity;
    private TextView mDOLName = null;
    private TextView mDOLPhone = null;
    private TextView mDOLEmail = null;
    private TextView mDOLAddress = null;
    private TextView mDOLPhoneCall = null;
    private TextView mDOLPhoneEmail = null;
    private String stDrName = null;
    private String stDOLEmail = null;
    private String stDOLPhone = null;
    private String stDOLAddress = null;
    private String stDOLLatitude = null;
    private String stDOLLongitude = null;
    private String stDOLName = null;

    DOLModel mDOLModel = null;
    DOLDataSource mDOLDataSource = null;

    MapView mMapView;
    private GoogleMap mGoogleMap = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_single_dol, null);
        mActivity = getActivity();
        mMapView = (MapView) rootView.findViewById(R.id.singleDOLMap);
        mMapView.onCreate(savedInstanceState);
        setUpMapIfNeeded();
        String myWsmsId = getArguments().getString("dolId");

        mDOLName = (TextView) rootView.findViewById(R.id.singleDolName);
        mDOLPhone = (TextView) rootView.findViewById(R.id.singleDOLPhone);
        mDOLEmail = (TextView) rootView.findViewById(R.id.singleEmailEmail);
        mDOLAddress = (TextView) rootView.findViewById(R.id.singleDOLAddress);
        mDOLPhoneCall = (TextView) rootView.findViewById(R.id.singleDolPhoneCall);
        mDOLPhoneEmail = (TextView) rootView.findViewById(R.id.singleDolPhoneEmail);

        mDOLPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();

                int Hr24 = c.get(Calendar.HOUR_OF_DAY);
                int Min = c.get(Calendar.MINUTE);
                if (stDOLPhone.toString() != null && !stDOLPhone.toString().trim().isEmpty()) {
                    if (Hr24 <= 17 && 9 <= Hr24) {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + stDOLPhone)));
                    } else {
                        Toast.makeText(getContext(), " অনুগ্রহপূর্বক সকাল ৯টা থেকে বিকাল ৫ টার মধ্যে কল করুন ।", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), " ফোন নম্বরটি খুঁজে পাওয়া যায় নি ।", Toast.LENGTH_SHORT).show();

                }
            }
        });

        mDOLPhoneEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stDOLEmail.toString() != null && !stDOLEmail.toString().trim().isEmpty()) {
                    startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + stDOLEmail)));
                } else {
                    Toast.makeText(getContext(), " ইমেইল এড্রেস টি খুঁজে পাওয়া যায়নি ।", Toast.LENGTH_SHORT).show();

                }
            }
        });

        mDOLDataSource = new DOLDataSource(getActivity());
        mDOLModel = mDOLDataSource.getSingleDOLById(myWsmsId);

        stDOLName = mDOLModel.getmDOLName();
        stDrName = mDOLModel.getmDrName();
        stDOLEmail = mDOLModel.getmDOLEmail();
        stDOLPhone = mDOLModel.getmDOLPhone();
        stDOLAddress = mDOLModel.getmDOLAddress();
        stDOLLatitude = mDOLModel.getmDOLLatitude();
        stDOLLongitude = mDOLModel.getmDOLLongitude();

        mDOLName.setText(stDrName);
        mDOLPhone.setText(stDOLPhone);
        mDOLEmail.setText(stDOLEmail);
        mDOLAddress.setText(stDOLAddress);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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


                ActivityCompat.requestPermissions((Activity) mActivity, permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);

                return;
            }
            ActivityCompat.requestPermissions((Activity) mActivity, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        } else {
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ActivityCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) mActivity, permission))
                return false;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == MY_PERMISSIONS_ACCESS_COARSE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permisssion granted! ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mGoogleMap == null) {

            mMapView.onResume();// needed to get the map to display immediately

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mGoogleMap = googleMap;
                    setDOLMarker();
                    if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mGoogleMap.setMyLocationEnabled(true);

                }
            });

            if (Build.VERSION.SDK_INT >= 23) {
                chkPermissions();
            } else {
                // Pre-Marshmallow
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                // Check if we were successful in obtaining the map.
                if (mGoogleMap == null) {
                    Functions.showToast(getActivity(), "Unable to load Map");
                }
            }
        }
    }

    public void setDOLMarker() {
        // latitude and longitude
        double latitude = Double.parseDouble(stDOLLatitude);
        double longitude = Double.parseDouble(stDOLLongitude);

        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(stDOLName);

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marker
        mGoogleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(15)
                .tilt(30) // Sets the tilt of the camera to 30 degrees
                .build(); // Creates a CameraPosition from the builder
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}
