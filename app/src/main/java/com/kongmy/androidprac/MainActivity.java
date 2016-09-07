package com.kongmy.androidprac;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult>,
        LocationListener {

    private static final int REQUEST_CHECK_SETTINGS = 9901;
    private GoogleApiClient mGoogleApiClient;
    private Button mButtonRefresh;
    private TextView mTextViewLongtitude;
    private TextView mTextViewLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mButtonRefresh = (Button) findViewById(R.id.button_refresh);
        mTextViewLongtitude = (TextView) findViewById(R.id.longtitude);
        mTextViewLatitude = (TextView) findViewById(R.id.latitude);

        mButtonRefresh.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mGoogleApiClient.connect();
        }
    }

    private void startReceiveUpdate() {
        LocationRequest request = createLocationRequest();
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private LocationRequest createLocationRequest() {
        return LocationRequest.create()
                .setInterval(5000)
                .setMaxWaitTime(4000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(1000);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        updateLocation();
    }

    private void updateLocation() {
        try {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (lastLocation != null)
                updateLocation(lastLocation);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mButtonRefresh.setEnabled(true);

        LocationRequest request = createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(request);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(this);

        updateLocation();
        startReceiveUpdate();
    }

    private void updateLocation(Location lastLocation) {
        mTextViewLongtitude.setText(String.valueOf(lastLocation.getLongitude()));
        mTextViewLatitude.setText(String.valueOf(lastLocation.getLatitude()));
    }

    @Override
    public void onConnectionSuspended(int i) {
        mButtonRefresh.setEnabled(false);
        mTextViewLongtitude.setText(R.string.msg_not_available);
        mTextViewLatitude.setText(R.string.msg_not_available);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mButtonRefresh.setEnabled(false);
        mTextViewLongtitude.setText(R.string.msg_not_available);
        mTextViewLatitude.setText(R.string.msg_not_available);
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        Status status = locationSettingsResult.getStatus();
        if (status.isSuccess()) {
        } else {
            try {
                status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        updateLocation(location);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == RESULT_OK) {
            startReceiveUpdate();
        }
    }
}
