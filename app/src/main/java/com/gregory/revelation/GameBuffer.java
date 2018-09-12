package com.gregory.revelation;

import android.support.annotation.Nullable;

import java.util.ArrayList;

public class GameBuffer {

    private ArrayList<String> openQuestions;
    private ArrayList<Pair> closedQuestions;
    public enum State {
        COLLECTING, NEED_ANSWER, FULL
    }
    private State state;
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
        this.state = State.COLLECTING;
        this.sameTurnFlag = samePlayerCanAnswerSameTurn;
    }

    public String removeOpenQuestion(){
        return openQuestions.remove(Util.getRandomIntegerBetweenRange(0, openQuestions.size()));
    }

    public void putOpenQuestion(String question){

        if(sameTurnFlag) {
            openQuestions.add(question);
        } else {
            openQuestions.add(holdQuestion); //from previous turn
            holdQuestion = question; //from this turn
        }

        if(openQuestions.size() >= this.bufferSize && this.state != State.FULL){
            this.state = State.NEED_ANSWER;
        }
    }

    //will return null if in State.NEED_ANSWER
    public @Nullable Pair getQuestionAnswerPair(){
        if(closedQuestions.size() > 0){
            Pair pair = closedQuestions.remove(Util.getRandomIntegerBetweenRange(0, closedQuestions.size()));
            this.state = State.NEED_ANSWER; //we have removed a question from the pool
            return pair;
        } else {
            return null;
        }
    }

    public void assignAnswer(String question, String answer){
        Pair pair = new Pair(question, answer);
        closedQuestions.add(pair);
        if(closedQuestions.size() >= this.bufferSize) {
            this.state = State.FULL;
        }
    }

    public State getState(){
        return this.state;
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
