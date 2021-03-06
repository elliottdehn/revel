package com.gregory.revelation;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gregory.revelation.fragments.GameSettingsFragment;
import com.gregory.revelation.fragments.IntroFragment;
import com.gregory.revelation.fragments.NextPlayerFragment;
import com.gregory.revelation.fragments.PostResponseFragment;
import com.gregory.revelation.fragments.PostThoughtFragment;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private GameBuffer mGameBuffer;

    /*
        I make this in my free time, and as such, the code here is not representative of my professional work
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.hide();
        }

        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) { //this is done so that rotations don't reset the fragment
            setupFragmentIntro();
        }
    }

    public void onFragmentInteraction(int id, List<Object> args) {
        switch(id){
            case R.id.button_intro:
                handleIntro(args);
                break;
            case R.id.button_startGame:
                handleStartGame(args);
                break;
            case R.id.button_postThought:
                handlePostThought(args);
                break;
            case R.id.button_doneResponding:
                handleDoneResponding(args);
                break;
            case R.id.button_nextPlayerIsReady:
                handleNextPlayerIsReady(args);
                break;

        }
    }

    private void handleIntro(List<Object> args){
        setupFragmentGameSettings();
    }

    private void handleStartGame(List<Object> args){
        Object firstArg = args.get(0);

        //should always be allowed, this is on me
        int playerCount = (int) firstArg;

        mGameBuffer = new GameBuffer(playerCount);
        for(int i = 0; i < playerCount; i++){
            mGameBuffer.addThought(FlavorGenerator.grabQuestion(), true);
            //use our supplied questions until the buffer of questions supplied by players is full enough
        }

        setupFragmentPostThought(Util.getRandomColor());
    }

    private void handlePostThought(List<Object> args){
        mGameBuffer.addThought((String) args.get(0)); //never null, always present
        setupFragmentNextPlayer();
    }

    private void handleDoneResponding(List<Object> args){
        setupFragmentPostThought((int) args.get(0)); //go to art screen for next player
    }
    private void handleNextPlayerIsReady(List<Object> args){
        //pool is always ready
        setupFragmentPostResponse();
    }

    private void setupFragmentIntro(){
        IntroFragment introFragment = new IntroFragment();
        addFragment(introFragment);
    }

    private void setupFragmentGameSettings(){
        GameSettingsFragment gameSettingsFragment = new GameSettingsFragment();
        addFragment(gameSettingsFragment);
    }

    private void setupFragmentPostThought(int color){
        PostThoughtFragment postQuestion = PostThoughtFragment.newInstance(color);
        addFragment(postQuestion);
    }

    private void setupFragmentPostResponse(){
        String openThought = mGameBuffer.removeOpenThought();
        PostResponseFragment postResponseFragment = PostResponseFragment.newInstance(openThought);
        addFragment(postResponseFragment);
    }

    private void setupFragmentNextPlayer(){
        String flavor = FlavorGenerator.grabFlavor();
        NextPlayerFragment nextPlayerFragment = NextPlayerFragment.newInstance(flavor);
        addFragment(nextPlayerFragment);
    }

    //#TODO manage backstack
    private void addFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction= fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }
}
