package com.laughingmonsters.kavu.hangman;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by Kavu on 14-09-2014.
 */
public class Logic {
    ArrayList<String> possibleWords = new ArrayList<String>();
    ArrayList<String> used;
    String word;
    public boolean complete;

    public Logic (){
        used = new ArrayList<String>();
    }

    public Logic(String word, String usedLetters){
        this.word = word;
        used = new ArrayList<String>();
        for(int i = 0; i < usedLetters.length(); i++){
            char s = usedLetters.charAt(i);
            used.add("" + s);
        }
    }

    public void restart(){
        word = possibleWords.get(new Random().nextInt(possibleWords.size()));
        used.clear();
        complete = false;
        updateWord();
    }

    public boolean guessLetter(String guess){
        String letter = guess.toLowerCase();
        if(word.contains(letter)){
            used.add(letter);
            return true;
        }else{
            used.add(letter);
            return false;
        }
    }

    public boolean guessWord(String guess){
        if(guess.equals(word)){
            complete = true;
            return true;
        }else{
            return false;
        }
    }

    public String updateWord(){
        String visibleWord = "";
        if(complete){
            return word;
        }
        for (int n = 0; n < word.length(); n++) {
            String aLetter = word.substring(n, n + 1);
            if (used.contains(aLetter)) {
                visibleWord = visibleWord + aLetter;
            } else {
                visibleWord = visibleWord + "*";
            }
        }
        if(!visibleWord.contains("*")){
            complete = true;
        }
        return visibleWord;
    }

    public void getWords(String wordURL, final Context context) throws Exception {
        final String url = wordURL;
        System.out.println("url is: " + url);
        AsyncTask task = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();
                    while (line != null) {
                        if(line.length() > 4){
                        sb.append(line + "\n");
                        }
                        line = br.readLine();
                    }
                    String data = sb.toString();
                    data = data.replaceAll("<.+?>", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");

                    possibleWords.clear();
                    possibleWords.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));
                }catch(Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object object){
                String words = "";
                for(String s : possibleWords){
                    words += s + "-";
                }
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString("possibleWords", words).commit();
                System.out.println("Words have been loaded");
            }
        };
        task.execute();
    }

    public String getWord(){ return word; }

    public void setWord(String word){ this.word = word; used.clear(); complete = false;}

    public ArrayList<String> getUsed(){ return used; }

    public void setUsed(ArrayList<String> used){ this.used = used; }

    public ArrayList<String> getPossibleWords(){ return possibleWords; }

    public void setPossibleWords(ArrayList<String> possibleWords) { this.possibleWords = possibleWords; }
}
