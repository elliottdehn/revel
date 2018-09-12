package com.gregory.revelation;

import android.support.annotation.Nullable;

import java.util.ArrayList;

public class GameBuffer {

    private ArrayList<String> openQuestions;
    private ArrayList<Pair> closedQuestions;
    private int bufferSize;

    GameBuffer(int bufferSize){
        if(bufferSize > 0) {
            this.bufferSize = bufferSize;
        } else {
            this.bufferSize = 3;
        }

        this.openQuestions = new ArrayList<>();
        this.closedQuestions = new ArrayList<>();
    }

    public String getOpenQuestion(){
        return openQuestions.get(Util.getRandomIntegerBetweenRange(0, openQuestions.size()));
    }

    public void putOpenQuestion(String question){
        openQuestions.add(question);
    }

    public @Nullable Pair getQuestionAnswerPair(){
        if(closedQuestions.size() > 0){
            return closedQuestions.remove(Util.getRandomIntegerBetweenRange(0, closedQuestions.size()));
        } else {
            return null;
        }
    }

    public boolean questionBufferReady(){
        return this.openQuestions.size() >= this.bufferSize;
    }

    public boolean questionAnswerBufferReady(){
        //in theory there should only ever be 1 in the buffer at any given time...
        return this.openQuestions.size() >= 1;
    }

    public void assignAnswer(String question, String answer){
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
