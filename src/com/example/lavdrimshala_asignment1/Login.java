package com.example.lavdrimshala_asignment1;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends Activity {

	private String password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		password= "ebcdc7babc0de9a1d6c7d1c18bfcb8183fa492";
	    try {
            new DownloadImageTask().execute(new URL("http://icons.iconarchive.com/icons/aha-soft/security/256/key-icon.png"));
	    	} 
	    catch (Exception e) 
	    	{
            e.printStackTrace();
	    	}		
	 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	public String toSHA1(String toConvert)
	{
		MessageDigest md;
		String result="";
		
		try
		{
			md=MessageDigest.getInstance("SHA1");
			byte[] Hash = md.digest(toConvert.getBytes("UTF8"));
				
			for(int i=0;i<=md.getDigestLength();i++)
			result += Integer.toHexString(0xFF & Hash[i]);
			
		}
		catch(Exception ex)
		{
			
		}
		
		return result;
	}
	public void MessageBox(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	public void login(View v)
	{
		EditText pass = (EditText)findViewById(R.id.editText_Login);
		String pass_string=pass.getText().toString();
		
		if(toSHA1(pass_string).equals(password))
		{
			pass.setText(null);
			Intent myIntent = new Intent(Login.this,Results.class);
			Login.this.startActivity(myIntent);
		
		}
		else
		{
			
			showAlertMessage("Wrong Pin! \nTry Again!");
			pass.setText(null);
		}
	
	}
	public void showAlertMessage(String msg) {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
		dlgAlert.setMessage(msg);
		dlgAlert.setPositiveButton("OK", null);
		dlgAlert.create().show();
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
