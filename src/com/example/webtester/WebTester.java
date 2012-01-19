package com.example.webtester;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.net.Uri;
import android.content.Intent;
import android.widget.*;
import android.view.View;
import android.provider.Browser;

public class WebTester extends Activity {
    /** Called when the activity is first created. */
	
	final WebTester ME = this;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // Find the resources
        final Button button = (Button) findViewById(R.id.button1);
        final EditText text = (EditText) findViewById(R.id.editText1);
        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Disable the button
            	button.setText(R.string.running);
            	button.setClickable(false);
            	
        		Uri uri = Uri.parse(text.getText().toString());
        		
            	// Perform action on click
            	for (int i=1;i<=10;i++) {
            		Browser.clearHistory(getContentResolver());

            		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            		startActivity(intent);
            		
            		// Display which iteration
            		//Toast.makeText(ME, "#", 0).show();
            		
            		SystemClock.sleep(60000);
            	}

            	// Enable the button
            	//button.setText(R.string.run);
            	//button.setClickable(true);
            }
        });
    }
}