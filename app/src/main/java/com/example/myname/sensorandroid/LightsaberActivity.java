package com.example.myname.sensorandroid;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class LightsaberActivity extends Activity implements SensorEventListener2 {

    private SensorManager sensorManager;
    private Sensor liniarAccelerationSensor;


    private float[] liniarAccelerationSensorsValues = new float[3];

    private SoundPool soundPool;
    public int soundHumHigh;
    public int soundHumLow;
    private int idSoundHumLow;
    private int idSoundHumHigh;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightsaber);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        liniarAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        this.createNewSoundPool();
        soundHumHigh = this.soundPool.load(this, R.raw.saberhum_hf, 1);
        soundHumLow = this.soundPool.load(this, R.raw.saberhum_lf, 2);
        this.registerListeners(this.soundPool);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void registerListeners(SoundPool soundPool) {
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

            /**
             * Called when a sound has completed loading.
             *
             * @param soundPool SoundPool object from the load() method
             * @param sampleId  the sample ID of the sound loaded.
             * @param status    the status of the load operation (0 = success)
             */
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                idSoundHumLow = soundPool.play(soundHumLow, 1f, 1f, 1, -1, 1f);
                idSoundHumHigh = soundPool.play(soundHumHigh, 0.2f, 0.2f, 1, -1, 1.2f);
            }
        });

    }

    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        this.soundPool = new SoundPool.Builder().setAudioAttributes(attributes)
                .build();

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == this.liniarAccelerationSensor) {
            this.liniarAccelerationSensorsValues = event.values;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Lightsaber Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.myname.sensorandroid/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.sensorManager.registerListener(this, this.liniarAccelerationSensor, SensorManager.SENSOR_DELAY_GAME);
        this.soundPool.autoResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.sensorManager.unregisterListener(this);
        this.soundPool.autoPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.soundPool.stop(this.idSoundHumHigh);
        this.soundPool.stop(this.idSoundHumLow);
        this.soundPool.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lightsaber, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.new_compass_sensor:
                Intent compassIntent = new Intent(this, MainActivity.class);
                startActivity(compassIntent);
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
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Lightsaber Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.myname.sensorandroid/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
