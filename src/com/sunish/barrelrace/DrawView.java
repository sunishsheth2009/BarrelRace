package com.sunish.barrelrace;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Vibrator;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

class DrawView extends View {
	DrawView drawView;
	float x1,y1;
	float ratiox;
	float ratioy;
	float maxWidhtAcceleromter;
	float last_x1 = 0;
	float last_y1 = 0;
	int x,y;
	Context context;
    ArrayList<Integer> xarray = new ArrayList<Integer>();
    ArrayList<Integer> yarray = new ArrayList<Integer>();
    ArrayList<Integer> firstCircle = new ArrayList<Integer>();
    boolean circle1 = false;
    ArrayList<Integer> secondCircle = new ArrayList<Integer>();
    boolean circle2 = false;
    ArrayList<Integer> thirdCircle = new ArrayList<Integer>();
    boolean circle3 = false;
    int firstQuadrant1 = 0;
    int secondQuadrant1 = 0;
    int thirdQuadrant1 = 0;
    int forthQuadrant1 = 0;
    int firstQuadrant2 = 0;
    int secondQuadrant2 = 0;
    int thirdQuadrant2 = 0;
    int forthQuadrant2 = 0;
    int firstQuadrant3 = 0;
    int secondQuadrant3 = 0;
    int thirdQuadrant3 = 0;
    int forthQuadrant3 = 0;
    int start = 0;
    int InitialSetup = 0;
	long startTime = 0;
	int EndGame = 1;
	String Time;
	long BorderTouchPunishment = 0;
	long preduration = 0;
	Paint p = new Paint();
	int i =0;
	Canvas canvas;
	static final int width = 50;
    static final int height = 50;
    long saveDuration;
    long duration;
    int hitBarrel = 0;
	
	public DrawView(Context context , AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(EndGame == 1){
			x1 = 0;
			y1 = 0;
		}
		Vibrator v = (Vibrator)this.getContext().getSystemService(Context.VIBRATOR_SERVICE);
		p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(20);
        p.setColor(Color.BLACK);
        x=getWidth();
		y=getHeight();
		if(start == 0){
			start();
		}
		
		
		Paint textColor = new Paint();;
		textColor.setColor(Color.BLACK);
		int textSize = (int)((y- ((0.1*y)+x)) - (0.1*y));
		textColor.setTextSize(textSize);
		long endTime = System.currentTimeMillis();
		duration = (endTime - startTime);
		duration = duration / (1000);
		duration = duration + BorderTouchPunishment;
		long minutes = 00,seconds = 00,hours = 00 ;
		long countMinites = 0;
		if(EndGame == 1){
			end();
		}else{
			saveDuration = duration;
		}
		if(duration>60){
			seconds = duration % 60;
			minutes = duration / 60;
			countMinites = minutes;
		}else{
			seconds = duration;
		}
		if(countMinites > 60){
			minutes = countMinites % 60;
			hours = countMinites/60;
		}
		Time = Long.toString(hours) + ":" + Long.toString(minutes) + ":" + Long.toString(seconds);
		String str = Time;
		canvas.drawText("Time:"+str, 0, textSize, textColor);
        canvas.drawRect(0, (float)(y- ((0.1*y)+x)), x, (float) (y-0.1*y) , p);
        p.setColor(Color.WHITE);
        canvas.drawLine((float) ((x/2) - ((0.025 * y)*3)) , (float)(y-0.1*y), (float)((x/2) + (0.025 * y)*3), (float)(y-0.1*y), p);
        p.setStrokeWidth(5);
        if(circle2 == true){
	        p.setColor(Color.BLUE);
        }else{
	        p.setColor(Color.BLACK);
        }
        canvas.drawCircle((float)x/4, (float) ((y- ((0.1*y)+x)) + (x/3)) , (float) 0.025 * y, p);
        if(circle3 == true){
	        p.setColor(Color.BLUE);
        }else{
	        p.setColor(Color.BLACK);
        }
        canvas.drawCircle((float)3*x/4, (float) ((y- ((0.1*y)+x)) + (x/3)) , (float) 0.025 * y, p);
        if(circle1 == true){
	        p.setColor(Color.BLUE);
        }else{
	        p.setColor(Color.BLACK);
        }
        canvas.drawCircle((float)x/2, (float) ((y- ((0.1*y)+x)) + ((2*x)/3)) , (float) 0.025 * y, p);
        
        
        
        p.setColor(Color.RED);
		int newx1 = (int) (((Integer)xarray.get(xarray.size()-1) + x1));
		int newy1 = (int) (((Integer)yarray.get(xarray.size()-1) + y1));
		if(InitialSetup == 0){
			if(circle1 == true && circle2 ==true && circle3 == true){
				start = 0;
				EndGame = 1;
	        }
			if(newx1 - (0.025 * y)  > ((x/2) - ((0.025 * y)*3)) && newx1 + (0.025 * y)< ((x/2) + ((0.025 * y)*3)) && newy1 < ((y-0.1*y) + (0.025 * y)) ){
				xarray.add(newx1);
				yarray.add(newy1);
				InitialSetup = 1;
			}
			if(newy1 + (0.025 * y)  > (getHeight()) && newx1 < 0 + 0.025 * y){
		        canvas.drawCircle((float)0.025 * y, (float) (getHeight()-(0.025*y)) , (float) 0.025 * y, p);
	        }else if(newy1 + (0.025 * y)  > (getHeight()) && (newx1+0.025 * y > (getWidth()))){
	        	canvas.drawCircle((float)(getWidth()-(0.025 * y)) , (float) (getHeight()-(0.025*y)) , (float) 0.025 * y, p);
	        }else if(newy1  < ((y-0.1*y) + (0.025 * y)) && newx1 < 0 + 0.025 * y){
				if(duration > preduration ){
		        	BorderTouchPunishment = BorderTouchPunishment + 5;
					duration = duration + 5;
					preduration = duration;
				}
	        	canvas.drawCircle((float)0.025 * y, (float) ((y-0.1*y) + (0.025 * y)) , (float) 0.025 * y, p);
	        }else if(newy1  < ((y-0.1*y) + (0.025 * y)) && newx1+0.025 * y > (getWidth())){
				if(duration > preduration ){
		        	BorderTouchPunishment = BorderTouchPunishment + 5;
					duration = duration + 5;
					preduration = duration;
				}
	        	canvas.drawCircle((float)(getWidth()-(0.025 * y)), (float) ((y-0.1*y) + (0.025 * y)) , (float) 0.025 * y, p);
	        }else if( newx1 < 0 + 0.025 * y){
	        	canvas.drawCircle( (float)0.025 * y , (float) newy1 , (float) 0.025 * y, p);
	        }else if(newx1+0.025 * y > (getWidth())){
	       		canvas.drawCircle( (float)(getWidth()-(0.025 * y)) , (float) newy1 , (float) 0.025 * y, p);
	        }else if(newy1 + (0.025 * y)  > (getHeight())){
		        canvas.drawCircle((float) newx1, (float) (getHeight()-(0.025*y)) , (float) 0.025 * y, p);
	        }else if(newy1  < ((y-0.1*y) + (0.025 * y))){
				if(duration > preduration ){
		        	BorderTouchPunishment = BorderTouchPunishment + 5;
					duration = duration + 5;
					preduration = duration;
				}
		        canvas.drawCircle((float) newx1, (float) ((y-0.1*y) + (0.025 * y)) , (float) 0.025 * y, p);
	        }else{
		        canvas.drawCircle((float)newx1, (float) newy1 , (float) 0.025 * y, p);
				xarray.add(newx1);
				yarray.add(newy1);
		    }		
		}else{
			if(newx1  > ((x/2) - ((0.025 * y)*3)) && newx1 + (0.025 * y)  < ((x/2) + ((0.025 * y)*3)) && newy1 > ((y-0.1*y) + (0.025 * y)) ){
				InitialSetup = 0;
			}
			if(newy1 + (0.025 * y)  > (getHeight()) && newx1 < 0 + 0.025 * y){
				if(duration > preduration ){
		        	BorderTouchPunishment = BorderTouchPunishment + 5;
					duration = duration + 5;
					preduration = duration;
				}
		        canvas.drawCircle((float)0.025 * y, (float) (getHeight()-(0.025*y)) , (float) 0.025 * y, p);
	        }else if(newy1 + (0.025 * y)  > (getHeight()) && (newx1+0.025 * y > (getWidth()))){
				if(duration > preduration ){
		        	BorderTouchPunishment = BorderTouchPunishment + 5;
					duration = duration + 5;
					preduration = duration;
				}
	        	canvas.drawCircle((float)(getWidth()-(0.025 * y)) , (float) (getHeight()-(0.025*y)) , (float) 0.025 * y, p);
	        }else if(newy1  < ((y- ((0.1*y)+x))+ (0.025 * y)) && newx1 < 0 + 0.025 * y){
				if(duration > preduration ){
		        	BorderTouchPunishment = BorderTouchPunishment + 5;
					duration = duration + 5;
					preduration = duration;
				}
	        	canvas.drawCircle((float)0.025 * y, (float) ((y- ((0.1*y)+x))+(0.025*y)) , (float) 0.025 * y, p);
	        }else if(newy1  < ((y- ((0.1*y)+x)) + (0.025 * y)) && newx1+0.025 * y > (getWidth())){
				if(duration > preduration ){
		        	BorderTouchPunishment = BorderTouchPunishment + 5;
					duration = duration + 5;
					preduration = duration;
				}
	        	canvas.drawCircle((float)(getWidth()-(0.025 * y)), (float) ((y- ((0.1*y)+x))+(0.025*y)) , (float) 0.025 * y, p);
	        }else if( newx1 < 0 + 0.025 * y){
				if(duration > preduration ){
		        	BorderTouchPunishment = BorderTouchPunishment + 5;
					duration = duration + 5;
					preduration = duration;
				}
	        	canvas.drawCircle( (float)0.025 * y , (float) newy1 , (float) 0.025 * y, p);
	        }else if(newx1+0.025 * y > (getWidth())){
				if(duration > preduration ){
		        	BorderTouchPunishment = BorderTouchPunishment + 5;
					duration = duration + 5;
					preduration = duration;
				}
	       		canvas.drawCircle( (float)(getWidth()-(0.025 * y)) , (float) newy1 , (float) 0.025 * y, p);
	        }else if((newx1 - (0.025 * y) < ((x/2) - ((0.025 * y)*3)) || newx1 + (0.025 * y) > ((x/2) + ((0.025 * y)*3))) && newy1 + (0.025 * y)  > ((y-0.1*y))){
				if(duration > preduration ){
		        	BorderTouchPunishment = BorderTouchPunishment + 5;
					duration = duration + 5;
					preduration = duration;
				}
		        canvas.drawCircle((float) newx1, (float) ((y-0.1*y)-(0.025*y)) , (float) 0.025 * y, p);
	        }else if(newy1  < ((y- ((0.1*y)+x)) + (0.025 * y))){
				if(duration > preduration ){
		        	BorderTouchPunishment = BorderTouchPunishment + 5;
					duration = duration + 5;
					preduration = duration;
				}
		        canvas.drawCircle((float) newx1, (float) ((y- ((0.1*y)+x))+(0.025*y)) , (float) 0.025 * y, p);
	        }else{
		        canvas.drawCircle((float)newx1, (float) newy1 , (float) 0.025 * y, p);
				xarray.add(newx1);
				yarray.add(newy1);
		    }	
		}
		
		Path p = new Path();
    	p.moveTo(xarray.get(0),yarray.get(0));
    	for(int i = 0; i < xarray.size();i++){
    		p.lineTo(xarray.get(i), yarray.get(i));
    		p.moveTo(xarray.get(i), yarray.get(i));	
    	}
    	Paint pathPaint = new Paint();
    	pathPaint.setColor(Color.GREEN);
    	pathPaint.setStyle(Paint.Style.STROKE);
    	pathPaint.setStrokeWidth(12);
    	canvas.drawPath(p,pathPaint);
		
		float dist1 = (float) Math.sqrt((newx1 - x/4)*(newx1 - x/4) + (newy1 - ((y- ((0.1*y)+x)) + (x/3)))*(newy1 - ((y- ((0.1*y)+x)) + (x/3))));
		float dist2 = (float) Math.sqrt((newx1 - 3*x/4)*(newx1 - 3*x/4) + (newy1 - ((y- ((0.1*y)+x)) + (x/3)))*(newy1 - ((y- ((0.1*y)+x)) + (x/3))));
		float dist3 = (float) Math.sqrt((newx1 - x/2)*(newx1 - x/2) + (newy1 - ((y- ((0.1*y)+x)) + ((2*x)/3)))*(newy1 - ((y- ((0.1*y)+x)) + ((2*x)/3))));
		
		if(dist1 < (0.025 * y) + (0.025 * y)|| dist2 < (0.025 * y) + (0.025 * y)|| dist3 < (0.025 * y) + (0.025 * y)){
        	v.vibrate(500);
        	start = 0;
        	EndGame = 1;
        	hitBarrel = 1;
        	
		}
		if(InitialSetup != 0){
			if(dist3 < (0.025 * y)*8 && dist3 > (0.025 * y) + (0.025 * y)){
				int flag = 0;
				int flag1 = 0;
				int flag2 = 0;
				double xDiff = x/2 - newx1;
				double yDiff = ((y- ((0.1*y)+x)) + ((2*x)/3)) - newy1;
				int angle = (int)Math.toDegrees(Math.atan2(yDiff,xDiff));
				if(angle<0){
					angle = angle + 360;
				}
				for (int i = 0; i < thirdCircle.size(); i++) {
					if(thirdCircle.get(i) == angle){
						flag = 1;
					}
					if(thirdCircle.get(i) == angle+1){
						flag1 = 1;
					}
					if(thirdCircle.get(i) == angle-1){
						flag2 = 1;
					}
				}
				if(flag == 0){
					if(angle >= 0 && angle <= 90){
						firstQuadrant3++;
					}else if (angle > 90 && angle <= 180){
						secondQuadrant3++; 
					}else if (angle > 180 && angle <=270){
						thirdQuadrant3++;
					}else if(angle > 270 && angle <= 360){
						forthQuadrant3++;
					}
					thirdCircle.add(angle);
				}
				if(flag1 == 0){
					if(angle+1 >= 0 && angle+1 <= 90){
						firstQuadrant3++;
					}else if (angle+1 > 90 && angle+1 <= 180){
						secondQuadrant3++; 
					}else if (angle+1 > 180 && angle+1 <=270){
						thirdQuadrant3++;
					}else if(angle+1 > 270 && angle+1 <= 360){
						forthQuadrant3++;
					}
					thirdCircle.add(angle+1);
				}
				if(flag2 == 0){
					if(angle-1 >= 0 && angle-1 <= 90){
						firstQuadrant3++;
					}else if (angle-1 > 90 && angle-1 <= 180){
						secondQuadrant3++; 
					}else if (angle-1 > 180 && angle-1 <=270){
						thirdQuadrant3++;
					}else if(angle-1 > 270 && angle-1 <= 360){
						forthQuadrant3++;
					}
					thirdCircle.add(angle-1);
				}
				if(thirdCircle.size()-1 > 360 || (firstQuadrant3 >= 85 && secondQuadrant3 >= 85 && thirdQuadrant3 >= 85 && forthQuadrant3 >= 85)){
					circle1 = true;
				}
			}else{
				thirdCircle.clear();
		        firstQuadrant3 = 0;
		        secondQuadrant3 = 0;
		        thirdQuadrant3 = 0;
		        forthQuadrant3 = 0;
			}
			
			if(dist1 < (0.025 * y)*8 && dist1 > (0.025 * y) + (0.025 * y)){
				int flag = 0;
				int flag1 = 0;
				int flag2 = 0;
				double xDiff = x/4 - newx1;
				double yDiff = ((y- ((0.1*y)+x)) + (x/3)) - newy1;
				int angle = (int)Math.toDegrees(Math.atan2(yDiff,xDiff));
				if(angle<0){
					angle = angle + 360;
				}
				for (int i = 0; i < firstCircle.size(); i++) {
					if(firstCircle.get(i) == angle){
						flag = 1;
					}
					if(firstCircle.get(i) == angle+1){
						flag1 = 1;
					}
					if(firstCircle.get(i) == angle-1){
						flag2 = 1;
					}
				}
				if(flag == 0){
					if(angle >= 0 && angle <= 90){
						firstQuadrant1++;
					}else if (angle > 90 && angle <= 180){
						secondQuadrant1++; 
					}else if (angle > 180 && angle <=270){
						thirdQuadrant1++;
					}else if(angle > 270 && angle <= 360){
						forthQuadrant1++;
					}
					firstCircle.add(angle);
					
				}
				if(flag1 == 0){
					if(angle+1 >= 0 && angle+1 <= 90){
						firstQuadrant1++;
					}else if (angle+1 > 90 && angle+1 <= 180){
						secondQuadrant1++; 
					}else if (angle+1 > 180 && angle+1 <=270){
						thirdQuadrant1++;
					}else if(angle+1 > 270 && angle+1 <= 360){
						forthQuadrant1++;
					}
					firstCircle.add(angle+1);
				}
				if(flag2 == 0){
					if(angle-1 >= 0 && angle-1 <= 90){
						firstQuadrant1++;
					}else if (angle-1 > 90 && angle-1 <= 180){
						secondQuadrant1++; 
					}else if (angle-1 > 180 && angle-1 <=270){
						thirdQuadrant1++;
					}else if(angle-1 > 270 && angle-1 <= 360){
						forthQuadrant1++;
					}
					firstCircle.add(angle-1);
				}
				if(firstCircle.size()-1 > 360 || (firstQuadrant1 >= 80 && secondQuadrant1 >= 80 && thirdQuadrant1 >= 80 && forthQuadrant1 >= 80)){
					circle2 = true;
				}
			}else{
				firstCircle.clear();
		        firstQuadrant1 = 0;
		        secondQuadrant1 = 0;
		        thirdQuadrant1 = 0;
		        forthQuadrant1 = 0;
			}
			
			if(dist2 < (0.025 * y)*8 && dist2 > (0.025 * y) + (0.025 * y)){
				int flag = 0;
				int flag1 = 0;
				int flag2 = 0;
				double xDiff = 3*x/4 - newx1;
				double yDiff = ((y- ((0.1*y)+x)) + (x/3)) - newy1;
				int angle = (int)Math.toDegrees(Math.atan2(yDiff,xDiff));
				if(angle<0){
					angle = angle + 360;
				}
				for (int i = 0; i < secondCircle.size(); i++) {
					if(secondCircle.get(i) == angle){
						flag = 1;
					}
					if(secondCircle.get(i) == angle+1){
						flag1 = 1;
					}
					if(secondCircle.get(i) == angle-1){
						flag2 = 1;
					}
				}
				if(flag == 0){
					if(angle >= 0 && angle <= 90){
						firstQuadrant2++;
					}else if (angle > 90 && angle <= 180){
						secondQuadrant2++; 
					}else if (angle > 180 && angle <=270){
						thirdQuadrant2++;
					}else if(angle > 270 && angle <= 360){
						forthQuadrant2++;
					}
					secondCircle.add(angle);
				}
				if(flag1 == 0){
					if(angle+1 >= 0 && angle+1 <= 90){
						firstQuadrant2++;
					}else if (angle+1 > 90 && angle+1 <= 180){
						secondQuadrant2++; 
					}else if (angle+1 > 180 && angle+1 <=270){
						thirdQuadrant2++;
					}else if(angle+1 > 270 && angle+1 <= 360){
						forthQuadrant2++;
					}
					secondCircle.add(angle+1);
				}
				if(flag2 == 0){
					if(angle-1 >= 0 && angle-1 <= 90){
						firstQuadrant2++;
					}else if (angle-1 > 90 && angle-1 <= 180){
						secondQuadrant2++; 
					}else if (angle-1 > 180 && angle-1 <=270){
						thirdQuadrant2++;
					}else if(angle-1 > 270 && angle-1 <= 360){
						forthQuadrant2++;
					}
					secondCircle.add(angle-1);
				}
				if(secondCircle.size()-1 > 360 ||(firstQuadrant2 >= 80 && secondQuadrant2 >= 80 && thirdQuadrant2 >= 80 && forthQuadrant2 >= 80)){
					circle3 = true;
				}
			}else{
		        firstQuadrant2 = 0;
		        secondQuadrant2 = 0;
		        thirdQuadrant2 = 0;
		        forthQuadrant2 = 0;
		        secondCircle.clear();
			}
		
		}
    }
	
	public void start(){
		xarray.clear();
		yarray.clear();
		firstCircle.clear();
		secondCircle.clear();
		thirdCircle.clear();
		circle1 = false;
		circle2 = false;
		circle3 = false;
        xarray.add(x/2);
        yarray.add((int) (0.95 * y));
        start++;
        firstQuadrant1 = 0;
        secondQuadrant1 = 0;
        thirdQuadrant1 = 0;
        forthQuadrant1 = 0;
        firstQuadrant2 = 0;
        secondQuadrant2 = 0;
        thirdQuadrant2 = 0;
        forthQuadrant2 = 0;
        firstQuadrant3 = 0;
        secondQuadrant3 = 0;
        thirdQuadrant3 = 0;
        forthQuadrant3 = 0;
        preduration = 0;
        BorderTouchPunishment = 0;
    	startTime = System.currentTimeMillis();
	}
	
	public void end(){
		
		if(hitBarrel == 1){
			duration = 0;
		}else{
			duration = saveDuration;
			if(duration>0){
		    	final String FinalTime = Long.toString(duration);
				LayoutInflater li = LayoutInflater.from(getContext());
				View promptsView = li.inflate(R.layout.prompts, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);
				TextView text = (TextView) promptsView.findViewById(R.id.textView1);
				String print = "Your Time is: " + FinalTime + " Seconds" + "\n" + "Enter your name:";
				text.setText(print);
		    	String abc = text.getText().toString();
		    	Log.i("Good",abc);
				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);
				// set dialog message
				alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
					    	
						// get user input and set it to result
						// edit text
					    	Editable result = userInput.getText();
							Intent intent = new Intent(getContext(), HighScore.class);
							intent.putExtra("Time", Integer.parseInt(FinalTime));
							intent.putExtra("Name", result.toString());
							getContext().startActivity(intent);
					    }
					  })
					.setNegativeButton("Cancel",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();
		    	start = 0;
		    	EndGame = 1;
		    	hitBarrel = 1;
			}
		}	
	}
}
