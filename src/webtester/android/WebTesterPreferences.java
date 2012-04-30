package webtester.android;

import com.example.webtester.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class WebTesterPreferences extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {        
        super.onCreate(savedInstanceState);        
        addPreferencesFromResource(R.xml.preferences);        
    }    
}


