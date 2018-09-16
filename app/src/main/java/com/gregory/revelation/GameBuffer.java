package com.gregory.revelation;

import android.support.annotation.Nullable;

import java.util.ArrayList;

public class GameBuffer {

    private ArrayList<String> openQuestions;
    private ArrayList<String> priorityQuestions;

    private int bufferSize;

    GameBuffer(int bufferSize){
        if(bufferSize > 0) {
            this.bufferSize = bufferSize;
        } else {
            this.bufferSize = 3;
        }

        this.openQuestions = new ArrayList<>();
        this.priorityQuestions = new ArrayList<>();
    }

    public String removeOpenThought(){
        if(priorityQuestions.size() > 0){
            return priorityQuestions.remove(Util.getRandomIntegerBetweenRange(0, priorityQuestions.size()-1));
        } else {
            return openQuestions.remove(Util.getRandomIntegerBetweenRange(0, openQuestions.size()-1));
        }
    }

    public void addThought(String question){
        openQuestions.add(question);
    }
    public void addThought(String question, boolean priority){
        if(!priority) {
            openQuestions.add(question); //from previous turn
        } else {
            priorityQuestions.add(question);
        }
    }

    public boolean questionPoolReady(){
        int sum = this.openQuestions.size() + this.priorityQuestions.size();
        return sum >= this.bufferSize;
    }

}
