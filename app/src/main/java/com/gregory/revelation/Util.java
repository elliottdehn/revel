package com.gregory.revelation;

import android.graphics.Color;

import java.util.Random;

public class Util {
    public static int getRandomIntegerBetweenRange(int min, int max){
        return (int)(Math.random()*((max-min)+1))+min;
    }

    public static int getRandomColor(){
        Random rnd = new Random();
        int generatedColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return generatedColor;
    }
}
