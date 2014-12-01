package com.sunish.barrelrace;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends ActionBarActivity implements SensorEventListener {
	DrawView drawView;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	float x1,y1;
	float maxWidhtAcceleromter;
	Context context;
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        drawView = (DrawView) findViewById(R.id.anim_view);
        drawView.setBackgroundColor(Color.WHITE);
        setSensor();

    }
    
    public void onStart(View v){
    	super.onStart();
    	drawView.start = 0;
    	drawView.EndGame = 0;
    	drawView.hitBarrel = 0;
    }
    
    public void onStop(View v){
    	super.onStop();
    	drawView.start = 0;
    	drawView.EndGame = 1;
    	drawView.hitBarrel = 1;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.HighScore) {
        	HighScore();
            return true;
        }
        if (id == R.id.Instruction) {
        	Instruction();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Instruction() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, Instruction.class);
		startActivity(intent);
	}

	private void HighScore() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, HighScore.class);
		intent.putExtra("Name", "");
		intent.putExtra("Time", 0);
		startActivity(intent);
	}

	public void setSensor(){
    	mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    	mAccelerometer =mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    	mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    	maxWidhtAcceleromter = mAccelerometer.getMaximumRange();
    }
    
    @Override
    protected void onResume() {
      super.onResume();
      mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
      mSensorManager.unregisterListener(this);
      super.onPause();
    }

    @Override
    protected void onStop() {
      mSensorManager.unregisterListener(this);
      super.onStop();
    }


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}


	@Override
	public void onSensorChanged(SensorEvent mAccelerometer) {
		// TODO Auto-generated method stub
		if (mAccelerometer.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			x1 = -mAccelerometer.values[0];
	    	y1 = mAccelerometer.values[1];
	    	drawView.x1 = x1;
	    	drawView.y1 = y1;
	    	drawView.invalidate();
		} 
	}
}
