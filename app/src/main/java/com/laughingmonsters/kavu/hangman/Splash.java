package com.laughingmonsters.kavu.hangman;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Kavu on 16-09-2014.
 */
public class Splash extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        new Handler().postDelayed(new Runnable()){
//
//        }
//        AsyncTask task = new AsyncTask() {
//            private Game game;
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                try {
//                    game = new Game();
//                    game.loadWords();
//                }catch(Exception e){
//                    System.out.println(e);
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object object){
//                Intent i = new Intent();
//                System.out.println("Words have been loaded");
//            }
//        };
//        task.execute();
    }
}
