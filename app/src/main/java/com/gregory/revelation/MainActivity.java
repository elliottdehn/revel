package com.gregory.revelation;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gregory.revelation.fragments.GameSettingsFragment;
import com.gregory.revelation.fragments.IntroFragment;
import com.gregory.revelation.fragments.NextPlayerFragment;
import com.gregory.revelation.fragments.PostResponseFragment;
import com.gregory.revelation.fragments.PostThoughtFragment;
import com.gregory.revelation.fragments.ReadPairFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private GameBuffer mGameBuffer;

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

    //#TODO: find a way to make the state logic more easily understood/changed
    public void onFragmentInteraction(int id, List<Object> args) {
        switch(id){
            case R.id.button_intro:
                setupFragmentGameSettings();
                break;
            case R.id.button_startGame:
                Object firstArg = args.get(0);
                Object secondArg = args.get(1);

                //should always be allowed, this is on me
                int playerCount = (int) firstArg;
                boolean sameTurnSelfAnswer = (boolean) secondArg;

                mGameBuffer = new GameBuffer(playerCount, sameTurnSelfAnswer);

                setupFragmentPostThought();
                break;
            case R.id.button_postThought:

                mGameBuffer.addThought((String) args.get(0)); //never null, always present

                if(mGameBuffer.pairPoolReady()) { //are we ready to start reading out pairs?
                    setupFragmentReadPair();
                } else {
                    if(mGameBuffer.questionPoolReady()){ //are we ready to answer questions?
                        setupFragmentPostResponse();
                    } else{ //we are still collecting questions. go to next player
                        setupFragmentNextPlayer();
                    }
                }
                break;
            case R.id.button_doneReadingPair:
                setupFragmentPostResponse(); //always answer a question after reading a pair out
                break;
            case R.id.button_nextPlayer:
                //always comes from the answer submit screen, so add that pair
                mGameBuffer.addAnswer((String) args.get(0), (String) args.get(1));
                setupFragmentNextPlayer(); //go to art screen for next player
                break;
            case R.id.button_nextPlayerIsReady:
                setupFragmentPostThought(); //go back to step one. hopefully, the state machine works.
                break;

        }
    }
    private void setupFragmentIntro(){
        IntroFragment introFragment = new IntroFragment();
        addFragment(introFragment);
    }

    private void setupFragmentGameSettings(){
        GameSettingsFragment gameSettingsFragment = new GameSettingsFragment();
        addFragment(gameSettingsFragment);
    }

    private void setupFragmentPostThought(){
        PostThoughtFragment postQuestion = PostThoughtFragment.newInstance();
        addFragment(postQuestion);
    }

    private void setupFragmentReadPair(){
        GameBuffer.Pair thoughtPair = mGameBuffer.removePair();
        ReadPairFragment readPairFragment = ReadPairFragment.newInstance(thoughtPair.getLeft(), thoughtPair.getRight());
        addFragment(readPairFragment);
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
