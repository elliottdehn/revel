package com.gregory.revelation;

import java.util.ArrayList;

public class GameBuffer {

    private static ArrayList<String> openQuestions = new ArrayList<>();
    private static ArrayList<Pair> closedQuestions = new ArrayList<Pair>();

    public static Pair getQuestionAnswerPair(){
        return new Pair("Hello","goodbye");
    }

    public static String getOpenQuestion(){
        //TODO: refactor this so that I have a util class instead of this abomination
        return openQuestions.get(FlavorGenerator.getRandomIntegerBetweenRange(0, openQuestions.size()));
    }

    public static void putOpenQuestion(String question){
        openQuestions.add(question);
    }

    public static void assignAnswer(String question, String answer){
        Pair pair = new Pair(question, answer);
        closedQuestions.add(pair);
    }

    //yoinked from stackoverflo
    public static class Pair {

        private final String left;
        private final String right;

        public Pair(String left, String right) {
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
