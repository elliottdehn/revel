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

    //this method is really ugly. I need to find a better structure for the application/way to do this.
    //check for null in cases where it matters only.
    public void onFragmentInteraction(int id, List<Object> args) {

        //decided to use a separate button in each fragment in order to ensure
        //i did not need to use a state variable to keep track of what stage the game is on

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

                if(mGameBuffer.pairPoolReady()) {
                    setupFragmentReadPair();
                } else {
                    if(mGameBuffer.questionPoolReady()){
                        //question pool has bufferSize questions excluding this player's?
                        //answer questions
                        setupFragmentPostResponse();
                    } else{
                        //next player screen
                        setupFragmentNextPlayer();
                    }
                }
                break;
            case R.id.button_doneReadingPair:
                //if you're at the point where you are reading, you always answer a question after
                setupFragmentPostResponse();
                break;
            case R.id.button_nextPlayer:
                //nothing special to do here
                setupFragmentNextPlayer();
                break;
            case R.id.button_nextPlayerIsReady:
                //always answer a question after hitting ready
                setupFragmentPostThought();
                break;

        }
    }
    private void setupFragmentIntro(){
        Fragment fragment = new IntroFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }

    private void setupFragmentGameSettings(){
        Fragment fragment = new GameSettingsFragment();
        addFragment(fragment);
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
