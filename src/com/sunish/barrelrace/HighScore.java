package com.sunish.barrelrace;

/**
 * Group members : Sunish S Sheth Net ID. : sss140830 and Kanav Kaul Net ID. : kxk140730 Start date: September 29th, 2014
 * Purpose : Class Assignment - CS 6301.022 Summary: The assignment is on
 * Contact Manager. This program basically adds a new contact, deletes an
 * existing contact and lastly updates the contact.
 * This is the Main Activity of the program. This activity if the first one to be executed when the program starts.
 * This activity is used to display the List of the contacts using a List View. All the contacts are arranged in sorted order on the First name.
 * On clicking any of the contacts, one can view the contact. On clicking, a new activity is called which is the DisplayActivity.java for further operations.
 * Action Bar has an Add to contact menu item which is used to add a new contact. On clicking, a new activity is called which is the AddUpdateContactActivity.java for further operations.
 */


// This activity is created by Sunish Sheth (sss140830)

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

/**
 * This is the MainActivity where all the processing is done. The list creation and the action bar configuration is done here.
 * A file read is used extract the contacts from the file. Each line in the file is then processed and useful informations are divided into diffirent arrays and then displayed on the screen using the List view.
 * The action bar is used to display add to contact menu item.
 */
public class HighScore extends ActionBarActivity{
	ArrayList<Score> scoreList;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// onCreate is the method which will be initially called when the activity is started
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
		ListView listView ; // creating a List View Object.
		listView = (ListView) findViewById(R.id.list); // In the layout file called main.xml, a list view is created having ID list which is used to reference the layout onto the screen.	 
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);        
		listView.setTextFilterEnabled(true);
		TextIO text = new TextIO();
        Intent intent = getIntent();
		String name = intent.getStringExtra("Name");
		int time = intent.getIntExtra("Time", 0);
		String outputString = name + "\t" + time + "\n";	

		if(name.trim().equalsIgnoreCase("") || time == 0){
    		scoreList = new ArrayList<Score>();
    		scoreList = text.Read(getBaseContext());
	        String write = "";
            if(scoreList.size() > 0){
        		Collections.sort(scoreList, new CustomComparator());
        		int i = 1;
            	for(Score s : scoreList){
            		i++;
       	       	 	write = write + s.name + "\t";
    		        if(i>10){
    		        	break;
    		        }
            	}
            	String ScreenDisplay[] = write.split("\t") ;
	       	    MyListAdapter my = new MyListAdapter(this,R.layout.custom_row_view, ScreenDisplay, scoreList); // Creating a custom view of the list.
	       	    listView.setAdapter(my); 
    	     }else{
             	Log.i("Map null" , "Map null");
             } 
    	}else{
    		text.Write(getBaseContext(), outputString);
    		scoreList = new ArrayList<Score>();
    		scoreList = text.Read(getBaseContext());
	        String write = "";
            if(scoreList != null){
        		Collections.sort(scoreList, new CustomComparator());
        		int i = 1;
            	for(Score s : scoreList){
            		i++;
       	       	 	write = write + s.name + "\t";
    		        if(i>10){
    		        	break;
    		        }
            	}
            	String ScreenDisplay[] = write.split("\t") ;
	       	    MyListAdapter my = new MyListAdapter(this,R.layout.custom_row_view, ScreenDisplay, scoreList); // Creating a custom view of the list.
	       	    listView.setAdapter(my); 
    	     }else{
             	Log.i("Map null" , "Map null");
             } 
    	}
    }

/**
 * Creating the menu for the action bar.
 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.high_score, menu); // Taking the id of the menu created in menu.xml
        super.onCreateOptionsMenu(menu);
        return true;
    }
}
/**
 * This class is created for creating a custom view for the list view for displaying the details of each contact.
 */
class MyListAdapter extends ArrayAdapter<String>{
	int listItemView;
	String[] ScreenDisplay;
	ArrayList<Score> scoreList;
	Context context;
	// This constructor takes in id of the ListView, array of names and hash map which will help map each phone number to its name.
	public MyListAdapter(Context context, int listItemView, String [] ScreenDisplay, ArrayList<Score> scoreList) {
		    super(context, listItemView, ScreenDisplay);
		    this.context = context;
		    this.listItemView = listItemView;	    
		    this.ScreenDisplay = ScreenDisplay;
		    this.scoreList = scoreList;
		}
		public View getView(int position, View convertView, ViewGroup parent){
		    View v = convertView;
		    if (v == null) {
		        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        v = inflater.inflate(listItemView, null);
		    }
		    Score s = scoreList.get(position);
		    String Name = s.name;
		    int time = s.time;       
		    if (Name.length() > 0) {
		        TextView cName = (TextView) v.findViewById(R.id.name); // Taking the id of the name in the list view described in custom_row_view and displaying the name of that contact.
		        TextView cNumber = (TextView) v.findViewById(R.id.phone); // Taking the id of the phone in the list view described in custom_row_view and displaying phone number of that contact.
		        cName.setText(Name);
		        String print = Integer.toString(time) + "" + " Seconds";
		        cNumber.setText(print);
		    }
		    return v;
		}
}

class TextIO{
	public ArrayList<Score> Read(Context context){
		ArrayList<Score> scoreList = null;
		String [] input; // Every index in this string array will store the entire contact details of each person.
		String filename = "myfile.txt"; //This the file where all the contact details are stored.
	    // Code to read the file and each single line is stored in input String which is further processed.
	    try {
	        FileInputStream inputStream = context.openFileInput(filename);
	        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
	        StringBuilder total = new StringBuilder();
	        String eachinputline ="";
	        String line;
	        line = r.readLine();
	        if (line != null) {
	            while (line != null) {
	                total.append(line);
	                total.append("\n");
	                eachinputline = total.toString();
	                line = r.readLine();
	            }
	            //Log.i("reead", eachinputline);
	            input =  eachinputline.split("\n"); // Used to obtain every line and to be stored seperately.
	            String [] names = new String[input.length] ; // Array to store every details of each person.
	            String [] Phones = new String[input.length] ; // Array to store the phone number.
	    		scoreList = new ArrayList<Score>();
	            String ScreenDisplay[] = new String[input.length]; // Array to store the First name and Last name
	            for(int i =0;i<input.length;i++){
	           	names=input[i].split("\t");
	           	ScreenDisplay[i] = names[0]; // Appending the First name and Last name into a ScreenDisplay array
	           	Phones[i] = names[1]; // Putting the phone number in an seperate array. 
	    		scoreList.add(new Score(ScreenDisplay[i],Integer.parseInt(Phones[i])));
	            } 
	            r.close();
	            inputStream.close();	            
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return scoreList;
	}
	
	public void Write(Context context, String outputString){
    	String [] input;
    	String filename = "myfile.txt"; //This the file where all the contact details are stored.
        // Code to read the file and each single line is stored in input String which is further processed.
        try {
            FileInputStream inputStream = context.openFileInput(filename);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String eachinputline ="";
            String line;
            line = r.readLine();
            if (line != null) {
	             while (line != null) {
	                 total.append(line);
	                 total.append("\n");
	                 eachinputline = total.toString();
	                 line = r.readLine();
	             }
	             input =  eachinputline.split("\n"); // Used to obtain every line and to be stored seperately.
	             String [] names = new String[input.length] ; // Array to store every details of each person.
	             String [] Score = new String[input.length] ; // Array to store the phone number.
	             HashMap<String,String> map = new HashMap<String, String>(); // Hash map to every name to its phone number.
	             String ScreenDisplay[] = new String[input.length]; // Array to store the First name and Last name
	             for(int i =0;i<input.length;i++){
	            	 names=input[i].split("\t");
	            	 ScreenDisplay[i] = names[0]; // Appending the First name and Last name into a ScreenDisplay array
	            	 Score[i] = names[1]; // Putting the phone number in an seperate array. 
	            	 map.put(ScreenDisplay[i],Score[i]); // Mapping the phone number to each individual person.
	             }
	             String[] keys = (String[]) map.keySet().toArray(new String[1]);
	             Arrays.sort(keys);
	             for(String key : keys){
	            	 String write = key + "\t" + map.get(key);
	            	 //Toast.makeText(context,"Read " +  write, Toast.LENGTH_LONG).show();
	             }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    	try {
    		filename = "myfile.txt";
             FileOutputStream outputStream = context.openFileOutput(filename, context.MODE_APPEND);
             outputStream.write(outputString.getBytes());
             outputStream.close();
             File fileDir = context.getFilesDir();
             //Toast.makeText(context, "Write "+outputString , Toast.LENGTH_LONG).show();
             System.out.print(fileDir.toString());
             } catch (Exception e) {
                 e.printStackTrace();
             }
    	}	
	}

final class Score{
String name;
int time;
    public Score(String name,int time){
            this.name = name;
            this.time = time;
    }
}
final class CustomComparator implements Comparator<Score> {
        @Override
    public int compare(Score o1, Score o2) {
        return new Integer(o1.time).compareTo(o2.time);
    }
}