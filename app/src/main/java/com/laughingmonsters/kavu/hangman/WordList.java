package com.laughingmonsters.kavu.hangman;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Kavu on 23-09-2014.
 */
public class WordList extends Activity{
    String[] pWords;
    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //list = (ListView) findViewById(R.id.listView);
        loadList();
        list = new ListView(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });
        list.setAdapter(new ArrayAdapter(this, R.layout.layout_list, R.id.word, pWords));
    }

    public void loadList(){
        String words = PreferenceManager.getDefaultSharedPreferences(this).getString("possibleWords", "");
        pWords = words.split(" ");
    }
}
