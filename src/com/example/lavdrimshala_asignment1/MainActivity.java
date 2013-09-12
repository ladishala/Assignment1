package com.example.lavdrimshala_asignment1;

import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
implements RatingBar.OnRatingBarChangeListener{

	public SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setlisteners();
		db=openOrCreateDatabase("DBAsingment1.db",MODE_PRIVATE,null);
		databasehelp();
        fillspinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	 @Override
	 protected void onPause(){
		 savePreferences();
	     super.onPause();

	 }
	 @Override
	 protected void onResume(){
		 getPreferences();
	     super.onResume();

	 }
	 @Override
	 protected void onDestroy(){
		 savePreferences();
	     super.onDestroy();

	 }

	
	public void onSubmit(View v) {

		
		RatingBar r1 = (RatingBar)findViewById(R.id.ratingBar_Question1);
		RatingBar r2 = (RatingBar)findViewById(R.id.RatingBar_Question2);
		RatingBar r3 = (RatingBar)findViewById(R.id.RatingBar_Question3);
		RatingBar r4 = (RatingBar)findViewById(R.id.RatingBar_Question4);
		Spinner s =(Spinner)findViewById(R.id.spinner1);
		try
		{
		db.execSQL("Insert into tblResults VALUES((SELECT max(ID) FROM tblResults)+1,"+
					Float.toString(r1.getRating())+","+
					Float.toString(r2.getRating())+","+
					Float.toString(r3.getRating())+","+
					Float.toString(r4.getRating())+",'"+
					s.getSelectedItem().toString()+"');");
		}
		catch(Exception ex)
		{
			MessageBox("Error: "+ex.getMessage());
		}
		showAlertMessage("Your feedback has been submited. \nThank You for your time! \nThis will help us to improve our services!");
		r1.setRating((float) 5.0);
		r2.setRating((float) 5.0);
		r3.setRating((float) 5.0);
		r4.setRating((float) 5.0);
		s.setSelection(0);
}
	public void MessageBox(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onRatingChanged(RatingBar r, float rating,
			boolean fromUser) {
		
			TextView t;
		    if(r.getId()==R.id.ratingBar_Question1)
		    {
		    	t = (TextView)findViewById(R.id.TextView_Question1result);
		    }
		    else if(r.getId()==R.id.RatingBar_Question2)
		    {
		    	t = (TextView)findViewById(R.id.TextView_Question2result);
		    }
		    else if(r.getId()==R.id.RatingBar_Question3)
		    {
		    	 t = (TextView)findViewById(R.id.TextView_Question3result);
		    }
		    else
		    {
		    	 t = (TextView)findViewById(R.id.TextView_Question4result);
		    }
			t.setText(Float.toString(rating)+"/10");				
	}
	public void setlisteners()
	{
		((RatingBar)findViewById(R.id.ratingBar_Question1)).setOnRatingBarChangeListener(this);
		((RatingBar)findViewById(R.id.RatingBar_Question2)).setOnRatingBarChangeListener(this);
		((RatingBar)findViewById(R.id.RatingBar_Question3)).setOnRatingBarChangeListener(this);
		((RatingBar)findViewById(R.id.RatingBar_Question4)).setOnRatingBarChangeListener(this);
	
	}
	public void fillspinner()
	{
		
		List<String> list= new ArrayList<String>();
		
		try
		{
			Cursor cr = db.rawQuery("Select * from tblRelationship",null);
			if(cr!=null)
			{
				if(cr.moveToFirst())
				{
					do
					{
					list.add(cr.getString(cr.getColumnIndex("Relation")));
					}
					while(cr.moveToNext());
				}
			}
			
			cr.close();
			ArrayAdapter<String> adp1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
			adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			Spinner s = (Spinner)findViewById(R.id.spinner1);
			s.setAdapter(adp1);
		}
		catch(Exception ex)
		{
			//MessageBox("Error: "+ex.getMessage());
		}
	
	}
	public void showAlertMessage(String msg) {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
		dlgAlert.setMessage(msg);
		dlgAlert.setPositiveButton("OK", null);
		dlgAlert.create().show();
	}
	public void openmap(View v)
	{
		Intent myIntent = new Intent(MainActivity.this,CurrentLocationGmap.class);
		MainActivity.this.startActivity(myIntent);
	}
	public void openlogin(View v)
	{
		Intent myIntent = new Intent(MainActivity.this,Login.class);
		MainActivity.this.startActivity(myIntent);
	}
	public void databasehelp()
	{
		db.execSQL("Create Table if not exists tblResults (ID INTEGER PRIMARY KEY AUTOINCREMENT,Q1 FLOAT,Q2 FLOAT,Q3 FLOAT, Q4 FLOAT, Q5 Varchar)");
		db.execSQL("Create Table if not exists tblRelationship (Relation Varchar)");
		Cursor cr = db.rawQuery("Select Relation AS Rel From tblRelationship", null);
		try
		{
			if(cr!=null)
			{
				cr.moveToFirst();
				if(!cr.moveToNext())
				{
					db.execSQL("Insert into tblRelationship Values('Current Student');");
					db.execSQL("Insert into tblRelationship Values('Visitor');");
					db.execSQL("Insert into tblRelationship Values('Faculty or Staff');");
					db.execSQL("Insert into tblRelationship Values('Parent');");
					db.execSQL("Insert into tblRelationship Values('Other');");
				}
			}
		
		
		}
		catch(Exception ex)
		{
			

		}
		
	}
	public void savePreferences()
	{
		RatingBar r1 = (RatingBar)findViewById(R.id.ratingBar_Question1);
		RatingBar r2 = (RatingBar)findViewById(R.id.RatingBar_Question2);
		RatingBar r3 = (RatingBar)findViewById(R.id.RatingBar_Question3);
		RatingBar r4 = (RatingBar)findViewById(R.id.RatingBar_Question4);
		Spinner s =(Spinner)findViewById(R.id.spinner1);
		
		getPreferences(MODE_PRIVATE).edit().putFloat("Question1",r1.getRating()).commit();
		getPreferences(MODE_PRIVATE).edit().putFloat("Question2",r2.getRating()).commit();
		getPreferences(MODE_PRIVATE).edit().putFloat("Question3",r3.getRating()).commit();
		getPreferences(MODE_PRIVATE).edit().putFloat("Question4",r4.getRating()).commit();
		getPreferences(MODE_PRIVATE).edit().putLong("Question5",s.getSelectedItemId()).commit();

	}
	public void getPreferences()
	{
		RatingBar r1 = (RatingBar)findViewById(R.id.ratingBar_Question1);
		RatingBar r2 = (RatingBar)findViewById(R.id.RatingBar_Question2);
		RatingBar r3 = (RatingBar)findViewById(R.id.RatingBar_Question3);
		RatingBar r4 = (RatingBar)findViewById(R.id.RatingBar_Question4);
		Spinner s =(Spinner)findViewById(R.id.spinner1);
		r1.setRating(getPreferences(MODE_PRIVATE).getFloat("Question1",(float) 5.0));
		r2.setRating(getPreferences(MODE_PRIVATE).getFloat("Question2",(float) 5.0));
		r3.setRating(getPreferences(MODE_PRIVATE).getFloat("Question3",(float) 5.0));
		r4.setRating(getPreferences(MODE_PRIVATE).getFloat("Question4",(float) 5.0));
		s.setSelection((int) getPreferences(MODE_PRIVATE).getLong("Question5",(long)0));
	}
	



}


