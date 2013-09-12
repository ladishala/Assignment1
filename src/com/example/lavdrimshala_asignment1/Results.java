package com.example.lavdrimshala_asignment1;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Results extends Activity {

	public SQLiteDatabase db;
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		db=openOrCreateDatabase("DBAsingment1.db",MODE_PRIVATE,null);
		getresults();
		settext();
	    try {
            new DownloadImageTask().execute(new URL("http://www.gcsanjauli.com/images/results_icon.jpg"));
	    	} 
	    catch (Exception e) 
	    	{
            e.printStackTrace();
	    	}	
	}
	public void MessageBox(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
		return true;
	}
	public void getresults()
	{
		RatingBar r1 = (RatingBar)findViewById(R.id.RatingBar_Question1Res);
		RatingBar r2 = (RatingBar)findViewById(R.id.RatingBar_Question2Res);
		RatingBar r3 = (RatingBar)findViewById(R.id.RatingBar_Question3Res);
		RatingBar r4 = (RatingBar)findViewById(R.id.RatingBar_Question4Res);
		TextView t1 = (TextView)findViewById(R.id.textView_Question1Results);
		TextView t2 = (TextView)findViewById(R.id.textView_Question2Results);
		TextView t3 = (TextView)findViewById(R.id.textView_Question3Results);
		TextView t4 = (TextView)findViewById(R.id.textView_Question4Results);
		try{
		Cursor cr1 = db.rawQuery("Select AVG(Q1) AS aQ1,AVG(Q2) AS aQ2,AVG(Q3) AS aQ3,AVG(Q4) AS aQ4  from tblResults",null);
		if(cr1!=null)
		{
			cr1.moveToFirst();
			r1.setRating(Float.parseFloat(cr1.getString(cr1.getColumnIndex("aQ1"))));
			r2.setRating(Float.parseFloat(cr1.getString(cr1.getColumnIndex("aQ2"))));
			r3.setRating(Float.parseFloat(cr1.getString(cr1.getColumnIndex("aQ3"))));
			r4.setRating(Float.parseFloat(cr1.getString(cr1.getColumnIndex("aQ4"))));
			t1.setText(Float.toString(r1.getRating())+"/10");
			t2.setText(Float.toString(r2.getRating())+"/10");
			t3.setText(Float.toString(r3.getRating())+"/10");
			t4.setText(Float.toString(r4.getRating())+"/10");
			
		}
		}
		catch(Exception Ex)
		{
			MessageBox("Error: "+Ex.getMessage());
		}
	}
	public String countrecords()
	{
		String toReturn="";
		Cursor cr1 = db.rawQuery("Select Count() AS Count From tblResults", null);
		if(cr1!=null)
		{
			cr1.moveToNext();
			toReturn=cr1.getString(cr1.getColumnIndex("Count"));
		}
		return toReturn;	
	}
	public void settext()
	{
		TextView t = (TextView)findViewById(R.id.textView_ResultsHead);
		t.setText("Feedback form results of Gjovik University College Feedback from "+countrecords()+" feedbacks are shown bellow:");
	}
	 private Bitmap downloadImage(final URL url) {
	        Bitmap bitmap = null;
	        InputStream in = null;        
	        try {
	            in = openHttpConnection(url);
	            bitmap = BitmapFactory.decodeStream(in);
	            in.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return bitmap;                
	    }
	 private InputStream openHttpConnection(final URL url) throws IOException {
	        InputStream in = null;
	        int response = -1;
	        final URLConnection conn = url.openConnection();
	                 
	        if (!(conn instanceof HttpURLConnection)) {                     
	            throw new IOException("Not an HTTP connection");
	        }
	        
	        try {
	            final HttpURLConnection httpConn = (HttpURLConnection) conn;
	            httpConn.setAllowUserInteraction(false);
	            httpConn.setInstanceFollowRedirects(true);
	            httpConn.setRequestMethod("GET");
	            httpConn.connect(); 

	            response = httpConn.getResponseCode();                 
	            if (response == HttpURLConnection.HTTP_OK) {
	                in = httpConn.getInputStream();                                 
	            }                     
	        } catch (Exception ex) {
	                ex.printStackTrace();            
	        }
	        return in;     
	    }
	 private class DownloadImageTask extends AsyncTask<URL, Void, Bitmap> {
	        @Override
	        protected Bitmap doInBackground(URL... urls) {
	                assert urls.length == 1; // sanity check
	            return downloadImage(urls[0]);
	        }
	        @Override
	        protected void onPostExecute(Bitmap bitmap) {
	            //Then display the image to a view
	            final ImageView img = (ImageView) findViewById(R.id.imageView2);
	            img.setImageBitmap(bitmap);
	        }
	    }
}

