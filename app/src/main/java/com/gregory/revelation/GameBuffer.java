package com.gregory.revelation;

import android.support.annotation.Nullable;

import java.util.ArrayList;

public class GameBuffer {

    private ArrayList<String> openQuestions;
    private ArrayList<Pair> closedQuestions;

    private int bufferSize;
    private boolean sameTurnFlag;
    private String holdQuestion;

    GameBuffer(int bufferSize, boolean samePlayerCanAnswerSameTurn){
        if(bufferSize > 0) {
            this.bufferSize = bufferSize;
        } else {
            this.bufferSize = 3;
        }

        this.openQuestions = new ArrayList<>();
        this.closedQuestions = new ArrayList<>();
        this.sameTurnFlag = samePlayerCanAnswerSameTurn;
    }

    public String removeOpenThought(){
        return openQuestions.remove(Util.getRandomIntegerBetweenRange(0, openQuestions.size()));
    }

    public void addThought(String question){

        if(sameTurnFlag) {
            openQuestions.add(question);
        } else {
            openQuestions.add(holdQuestion); //from previous turn
            holdQuestion = question; //from this turn
        }

    }

    //will return null if in State.NEED_ANSWER
    public @Nullable Pair removePair(){
        if(closedQuestions.size() > 0){
            Pair pair = closedQuestions.remove(Util.getRandomIntegerBetweenRange(0, closedQuestions.size()));
            return pair;
        } else {
            return null;
        }
    }

    public boolean pairPoolReady(){
        return this.closedQuestions.size() >= this.bufferSize;
    }

    public boolean questionPoolReady(){
        return this.openQuestions.size()>- this.bufferSize;
    }

    public void addAnswer(String question, String answer){
        Pair pair = new Pair(question, answer);
        closedQuestions.add(pair);
    }

    public static class Pair {

        private final String left;
        private final String right;

        Pair(String left, String right) {
            this.left = left;
            this.right = right;
        }

        public String getLeft() { return left; }
        public String getRight() { return right; }

        @Override
        public int hashCode() { return left.hashCode() ^ right.hashCode(); }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Pair)) return false;
            Pair pairo = (Pair) o;
            return this.left.equals(pairo.getLeft()) &&
                    this.right.equals(pairo.getRight());
        }

    }
}
