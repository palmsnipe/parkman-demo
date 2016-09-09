package com.palmsnipe.parkmandemo;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.palmsnipe.parkmandemo.fragments.CustomMapFragment;
import com.palmsnipe.parkmandemo.helpers.DataHelper;
import com.palmsnipe.parkmandemo.helpers.JsonHelper;
import com.palmsnipe.parkmandemo.helpers.MapHelper;
import com.palmsnipe.parkmandemo.models.Bounds;
import com.palmsnipe.parkmandemo.models.LocationData;
import com.palmsnipe.parkmandemo.models.PolygonZone;
import com.palmsnipe.parkmandemo.models.Zone;
import com.palmsnipe.parkmandemo.views.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, MapWrapperLayout.OnDragListener, View.OnClickListener {

    private LatLng mCurrentLocation;
    private LocationData mData;
    private List<PolygonZone> mZones;
    private PolygonZone mSelected;

    private GoogleMap mMap;
    private PinView mPin;
    private BottomSheetBehavior mBottomSheetBehavior;
    private TextView mTvZone;
    private TextView mTvMaxDuration;
    private TextView mTvEmail;
    private TextView mTvProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // map
        CustomMapFragment mapFragment = (CustomMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.setOnDragListener(this);
        mapFragment.getMapAsync(this);

        mPin = (PinView) findViewById(R.id.pin);

        // bottom sheet
        View bottomSheet = findViewById( R.id.bottom_sheet);

        mTvZone = (TextView) bottomSheet.findViewById(R.id.zone);
        mTvMaxDuration = (TextView) bottomSheet.findViewById(R.id.max_duration);
        mTvEmail = (TextView) bottomSheet.findViewById(R.id.email);
        mTvProvider = (TextView) bottomSheet.findViewById(R.id.provider);
        Button btnPark = (Button) bottomSheet.findViewById(R.id.park);

        btnPark.setOnClickListener(this);

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    // Show again the pin if the user closed the bottom sheet with a slide
                    mPin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        mBottomSheetBehavior.setHideable(true);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        new LoadDataTask().execute();
    }

    private void configureMap() {
        // set bounds
        Bounds bounds = mData.getBounds();

        if (bounds != null) {
            LatLng northEast = new LatLng(bounds.getNorth(), bounds.getEast());
            LatLng southWest = new LatLng(bounds.getSouth(), bounds.getWest());
            LatLngBounds mapBounds = new LatLngBounds(southWest, northEast);
            mMap.setLatLngBoundsForCameraTarget(mapBounds);

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(mapBounds, 0);
            mMap.animateCamera(cu);
        }

        // show user location
        if (mCurrentLocation != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 18));
        }

    }

    private void drawZones() {
        mZones = new ArrayList<>();
        for (Zone zone : mData.getZones()) {

            List<LatLng> points = JsonHelper.getPolygon(zone.getPolygon());

            PolygonOptions polygonOptions = new PolygonOptions().addAll(points);
            int color = zone.getZoneColor();
            polygonOptions.fillColor(color)
                    .strokeColor(Color.BLACK)
                    .strokeWidth(2)
                    .zIndex(zone.getDepth());

            Polygon polygon = mMap.addPolygon(polygonOptions);

            mZones.add(new PolygonZone(polygon, zone));
        }
    }

    @Override
    public void onDrag(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            // Set the default color of the zone
            if (mSelected != null)
                mSelected.getPolygon().setFillColor(mSelected.getZone().getZoneColor());
            mSelected = null;

            mPin.setVisibility(View.GONE);
        }
        else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            mPin.setText("");
            mPin.setVisibility(View.VISIBLE);

            // check if the pin is on a zone
            LatLng center = mMap.getCameraPosition().target;
            boolean isInside = false;
            for (PolygonZone item : mZones) {
                isInside = MapHelper.isPointInPolygon(center, item.getPolygon().getPoints());

                if (isInside) {
                    zoneSelected(item);
                    break;
                }
            }
            if (!isInside) {
                // Hide the bottom sheet
                if (!mBottomSheetBehavior.isHideable())
                    mBottomSheetBehavior.setHideable(true);
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        }
    }

    private void zoneSelected(PolygonZone item) {
        Zone zone = item.getZone();
        mSelected = item;

        mSelected.getPolygon().setFillColor(Color.parseColor("#86BCFF"));

        // Show the price on the pin
        mPin.setAmount(zone.getServicePrice(), zone.getCurrency());

        // Fill the informations
        mTvZone.setText(zone.getName());
        mTvProvider.setText(zone.getProviderName());
        mTvEmail.setText(zone.getContactEmail());
        mTvMaxDuration.setText(String.format(Locale.getDefault(), getString(R.string.max_duration), zone.getMaxDuration()));

        // Show the details
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.park) {
            if (mSelected != null) {
            Zone zone = mSelected.getZone();
                String text = String.format(Locale.getDefault(), "%d - %s\n%s",
                        zone.getId(),
                        zone.getName(),
                        zone.getPaymentIsAllowed() == 1 ? getString(R.string.payment_allowed) : getString(R.string.payment_not_allowed));
                Toast.makeText(this, text, Toast.LENGTH_LONG).show();
            }
        }
    }

    class LoadDataTask extends AsyncTask<Void, Void, LocationData> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected LocationData doInBackground(Void... params) {
            JSONObject json = DataHelper.loadJsonObjectFromAsset(getApplicationContext(), "json.json");

            if (json != null) {
                if (json.has("current_location")) {
                    String[] currentLocation = JsonHelper.getString(json, "current_location").split(", ");
                    mCurrentLocation = new LatLng(Double.parseDouble(currentLocation[0]), Double.parseDouble(currentLocation[1]));
                }
                if (json.has("location_data")) {
                    try {
                        return LocationData.fromJson(json.getJSONObject("location_data"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(LocationData result) {
            if (result != null) {
                mData = result;

                configureMap();
                drawZones();
            }
            else {
                Log.e(ParkManDemo.TAG, "Unable to get the data");
            }
        }

    }
}
