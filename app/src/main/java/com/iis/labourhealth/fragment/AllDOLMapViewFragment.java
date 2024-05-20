package com.iis.labourhealth.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import java.util.ArrayList;

public class AllDOLMapViewFragment extends Fragment {
    public static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 100;
    public Activity mActivity;
    private MapView mMapView = null;
    private GoogleMap mGoogleMap = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_dol_map_view, container, false);
        mActivity = getActivity();
        mMapView = (MapView) rootView.findViewById(R.id.alldolMapView);
        mMapView.onCreate(savedInstanceState);
        setUpMapIfNeeded();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_ACCESS_COARSE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission granted!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
     *  Find places and show as a marker into map.
     */

    private class GetPlaces extends AsyncTask<Void, Void, ArrayList<DOLModel>> {

        private ProgressDialog dialog;

        public GetPlaces() {
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onPostExecute(ArrayList<DOLModel> result) {
            super.onPostExecute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Log.i("resultSize", String.valueOf(result.size()));
            mGoogleMap.clear();

            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    mGoogleMap.addMarker(new MarkerOptions().title(result.get(i).getmDOLName())
                            .position(new LatLng(Double.parseDouble(result.get(i).getmDOLLatitude()), Double.parseDouble(result.get(i).getmDOLLongitude())))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                }
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(Double.parseDouble(result.get(0).getmDOLLatitude()), Double.parseDouble(result.get(0).getmDOLLongitude())))
                        .zoom((float) 6.5) // Sets the zoom
                        .tilt(30) // Sets the tilt of the camera to 30 degrees
                        .build(); // Creates a CameraPosition from the builder
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
            dialog.setMessage("Loading..");
            dialog.isIndeterminate();
            dialog.show();
        }

        @Override
        protected ArrayList<DOLModel> doInBackground(Void... arg0) {
            DOLDataSource mDOLDataSource = new DOLDataSource(getActivity());
            ArrayList<DOLModel> mAllDOLList = mDOLDataSource.getAllDOLList();

            return mAllDOLList;
        }
    }

    private void setUpMapIfNeeded() {
        if (mGoogleMap == null) {
            mMapView.onResume();
            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mGoogleMap = googleMap;
                    GetPlaces myPlaces = new GetPlaces();
                    myPlaces.execute();
                    if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mGoogleMap.setMyLocationEnabled(true);


                }
            });

        }
    }
}
