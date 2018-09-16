package com.gregory.revelation;

import android.support.annotation.Nullable;

import java.util.ArrayList;

public class GameBuffer {

    private ArrayList<String> openQuestions;

    private int bufferSize;

    GameBuffer(int bufferSize){
        if(bufferSize > 0) {
            this.bufferSize = bufferSize;
        } else {
            this.bufferSize = 3;
        }

        this.openQuestions = new ArrayList<>();
    }

    public String removeOpenThought(){
        return openQuestions.remove(Util.getRandomIntegerBetweenRange(0, openQuestions.size()-1));
    }

    public void addThought(String question){
        openQuestions.add(question); //from previous turn
    }

    public boolean questionPoolReady(){
        return this.openQuestions.size() >= this.bufferSize;
    }

}
