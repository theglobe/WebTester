package webtester.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.net.Uri;
import android.content.Intent;
import android.widget.*;
import android.view.View;
import android.provider.Browser;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.os.AsyncTask;
import java.lang.Void;


public class WebTester extends Activity {
    /** Called when the activity is first created. */
	
	// final WebTester ME = this;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        
        // Find the resources
        final Button button = (Button) findViewById(R.id.button1);
        final TextView text = (TextView) findViewById(R.id.textView1);
        
    	// Load settings
    	SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(WebTester.this);
    	text.setText(sharedPrefs.getString("uri", getResources().getString(R.string.hello)));
    	
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            	// Disable the button
            	button.setText(R.string.running);
            	button.setClickable(false);

            	// Load settings
            	SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(WebTester.this);
            	
            	// Run the iterations in an own Thread in order to avoid blocking the UI
            	new DoIterationsTask().execute(sharedPrefs);
            	
            }
        });
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 0, 0, "Show current settings");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			startActivity(new Intent(this, WebTesterPreferences.class));
			return true;
		}
		return false;
	}
	

	private class DoIterationsTask extends AsyncTask<SharedPreferences, Uri, Void> {
		private int count = 0;
		
		protected Void doInBackground(SharedPreferences... prefs) {
			
			SharedPreferences sharedPrefs = prefs[0];
			
        	Uri uri = Uri.parse(sharedPrefs.getString("uri", "NULL"));
        	int numIterations = Integer.parseInt(sharedPrefs.getString("num_dl", "10"));
        	long sleepTime = Long.parseLong(sharedPrefs.getString("time_between", "60")) * 1000;

        	Log.d("WebTester", "TEST START");
        	
        	for (int i = 1; i <= numIterations; i++) {
        		this.count = i;
        		publishProgress(uri);

        		// Don't have to wait after the last iteration
        		if (i < numIterations) SystemClock.sleep(sleepTime);
        	}
 
        	Log.d("WebTester", "FINAL DOWNLOAD");
        	
        	return null;
		}

		protected void onProgressUpdate(Uri... uri) {
    		Browser.clearHistory(getContentResolver());

    		// Intent clear_cache = new Intent(Intent.AC, uri[0]);
    		Intent intent = new Intent(Intent.ACTION_VIEW, uri[0]);
    		// Display which iteration

    		//Toast.makeText(getApplicationContext(), "Iteration #"+Integer.toString(progress[0]), Toast.LENGTH_LONG).show();
    		Toast.makeText(getApplicationContext(), "Iteration #"+Integer.toString(this.count), Toast.LENGTH_LONG).show();
    		Log.d("WebTester", "START OF DOWNLOAD " + count);
    		
    		startActivity(intent);

		}
		protected void onPostExecute(Void result) {
        	// Enable the button
			Button button = (Button) findViewById(R.id.button1);

        	button.setText(R.string.run);
        	button.setClickable(true);
        	
        	Toast.makeText(getApplicationContext(), "Test complete!", Toast.LENGTH_LONG).show();
		}
	}
}
