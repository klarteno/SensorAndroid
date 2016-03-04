package com.example.myname.sensorandroid;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity implements SensorEventListener2 {

    ImageView compassDrawing;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Sensor magneticSensor;
    Toast myToast;

    private float[] accelerometerSensorsValues = new float[3];
    private float[] magneticSensorsValues = new float[3];
    private float[] rotation = new float[9];
    private float[] orientation = new float[3];
    private float currentCompassAngle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compassDrawing = (ImageView) findViewById(R.id.compasspic);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            myToast = Toast.makeText(this, "There is a sensor accelerometer detected", Toast.LENGTH_LONG);
            myToast.show();

            this.accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else {
            myToast = Toast.makeText(this, "There is no sensor  accelerometer detected", Toast.LENGTH_LONG);
            myToast.show();
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            myToast = Toast.makeText(this, "There is a sensor magnetic detected", Toast.LENGTH_LONG);
            myToast.show();

            this.accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        } else {
            myToast = Toast.makeText(this, "There is no sensor magnetic  detected", Toast.LENGTH_LONG);
            myToast.show();
        }

    }

    private void initializeSensor(int sensorName) {
        if (sensorManager.getDefaultSensor(sensorName) != null) {
            myToast = Toast.makeText(this, "There is a sensor" + sensorName + " detected", Toast.LENGTH_LONG);
            myToast.show();

            this.accelerometerSensor = sensorManager.getDefaultSensor(sensorName);
        } else {
            myToast = Toast.makeText(this, "There is no sensor " + sensorName + " detected", Toast.LENGTH_LONG);
            myToast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.new_saber_sensor:
                Intent lightSaberIntent = new Intent(this, LightsaberActivity.class);
                startActivity(lightSaberIntent);
                return true;
            case R.id.help:
                Toast.makeText(this, "help for sensors", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFlushCompleted(Sensor sensor) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor == this.accelerometerSensor) {
            accelerometerSensorsValues = event.values;
        }
        if (event.sensor == this.magneticSensor) {
            magneticSensorsValues = event.values;
        }

        SensorManager.getRotationMatrix(rotation, null, accelerometerSensorsValues, magneticSensorsValues);
        SensorManager.getOrientation(rotation, orientation);

        float azimuthRadians = orientation[0];
        float azimuthDegrees = -(float) (Math.toDegrees(azimuthRadians) + 360) % 360;

        doAnimation(currentCompassAngle, azimuthDegrees, this.compassDrawing);
        currentCompassAngle = azimuthDegrees;
    }

    private void doAnimation(float fromAngle, float toAngle, View rotationImage) {

        RotateAnimation rotation = new RotateAnimation(
                fromAngle, toAngle, Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotation.setDuration(200);
        rotation.setFillAfter(true);

        rotationImage.startAnimation(rotation);
    }

    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

 
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        this.sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    
    @Override
    protected void onPause() {
        super.onPause();

        this.sensorManager.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
