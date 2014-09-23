package com.laughingmonsters.kavu.hangman;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Kavu on 15-09-2014.
 */
public class Options extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.options);
    }
}
