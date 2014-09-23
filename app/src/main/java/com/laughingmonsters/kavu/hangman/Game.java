package com.laughingmonsters.kavu.hangman;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Game extends Activity implements View.OnClickListener{
    ArrayList<Button> letters = new ArrayList<Button>();
    HighScore score = new HighScore();
    Logic logic = new Logic();
    String guessText;
    ImageView image;
    TextView text;
    Button guess;
    int error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        letters.add((Button) findViewById(R.id.a));
        letters.add((Button) findViewById(R.id.b));
        letters.add((Button) findViewById(R.id.c));
        letters.add((Button) findViewById(R.id.d));
        letters.add((Button) findViewById(R.id.e));
        letters.add((Button) findViewById(R.id.f));
        letters.add((Button) findViewById(R.id.g));
        letters.add((Button) findViewById(R.id.h));
        letters.add((Button) findViewById(R.id.i));
        letters.add((Button) findViewById(R.id.j));
        letters.add((Button) findViewById(R.id.k));
        letters.add((Button) findViewById(R.id.l));
        letters.add((Button) findViewById(R.id.m));
        letters.add((Button) findViewById(R.id.n));
        letters.add((Button) findViewById(R.id.o));
        letters.add((Button) findViewById(R.id.p));
        letters.add((Button) findViewById(R.id.q));
        letters.add((Button) findViewById(R.id.r));
        letters.add((Button) findViewById(R.id.s));
        letters.add((Button) findViewById(R.id.t));
        letters.add((Button) findViewById(R.id.u));
        letters.add((Button) findViewById(R.id.v));
        letters.add((Button) findViewById(R.id.w));
        letters.add((Button) findViewById(R.id.x));
        letters.add((Button) findViewById(R.id.y));
        letters.add((Button) findViewById(R.id.z));
        for(Button b: letters){
            b.setOnClickListener(this);
        }
        guess = (Button) findViewById(R.id.guess);
        image = (ImageView) findViewById(R.id.image);
        text = (TextView) findViewById(R.id.text);
        guess.setOnClickListener(this);
        image.setOnClickListener(this);
        loadWords();
        load();
    }

    @Override
    public void onPause(){
        save();
    }

    @Override
    public void onDestroy(){
        save();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, Options.class);
            startActivity(i);
            return true;
        }else if(id == R.id.action_score){
            Intent i = new Intent(this, HighScore.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(image)){
            if(error == 6 || logic.complete){
                restart();
            }
        }else if(view.equals(guess) && error < 6){
            guessText = "";
            guessDialog();
        }else{
        for(Button b: letters){
            if(view.equals(b) && error < 6){
                guessLetter(b.getText().toString());
                view.setEnabled(false);
                }
        }
        }
    }

    public void guessLetter(String guess){
        if(logic.guessLetter(guess)){
            correct();
        }else{
            error();
        }
    }

    public void guessWord(){
        if(guessText.length() > 0){
        if(logic.guessWord(guessText)){
            correct();
        }else{
            error();
        }
        }
    }

    public void guessDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Make a Guess");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Guess", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                guessText = input.getText().toString();
                guessWord();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void correct(){
        text.setText(logic.updateWord());
        if(logic.complete){
            Context context = getApplicationContext();
            CharSequence text = "You win! \n Press the image to play again";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void error(){
        error++;
        if(error == 1){
            image.setImageResource(R.drawable.forkert1);
        }else if(error == 2){
            image.setImageResource(R.drawable.forkert2);
        }else if(error == 3){
            image.setImageResource(R.drawable.forkert3);
        }else if(error == 4){
            image.setImageResource(R.drawable.forkert4);
        }else if(error == 5){
            image.setImageResource(R.drawable.forkert5);
        }else if(error == 6){
            image.setImageResource(R.drawable.forkert6);
        }
        if(error == 6){
            Context context = getApplicationContext();
            CharSequence text = "You lose! \n Press the image to play again";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void restart(){
        logic.restart();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("word", logic.getWord());
        text.setText(logic.updateWord());
        error = 0;
        image.setImageResource(R.drawable.galge);
        for(Button b: letters){
            b.setEnabled(true);
        }
    }

    public void save(){
        String used = "";
        for(String s : logic.getUsed()){
            used += s;
        }
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("used", used).commit();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("word", logic.getWord()).commit();
    }

    public void load(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        logic.setWord(sharedPrefs.getString("word", ""));
        String[] usedLetters = sharedPrefs.getString("used", "").split("-");
        ArrayList<String> used = new ArrayList();
        for(String s : usedLetters){
            used.add(s);
        }
        logic.setUsed(used);
    }

    public void loadWords(){
        String url = PreferenceManager.getDefaultSharedPreferences(this).getString("url", "");
        logic.url = "http://cnn.com";
        try {
            logic.getWords();
            System.out.println("words loaded: " + logic.getPossibleWords());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
