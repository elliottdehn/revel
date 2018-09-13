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
            Fragment fragment = new IntroFragment();

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, fragment);
            transaction.commit();
        }
    }

    //this method is really ugly. I need to find a better structure for the application/way to do this.
    //check for null in cases where it matters only.
    public void onFragmentInteraction(int id, @Nullable List<Object> args) {

        //decided to use a separate button in each fragment in order to ensure
        //i did not need to use a state variable to keep track of what stage the game is on
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        switch(id){
            case R.id.button_intro:
                Fragment fragment = new GameSettingsFragment();

                transaction.replace(R.id.contentFragment, fragment);
                transaction.commit();
                break;
            case R.id.button_startGame:
                Object firstArg = args.get(0);
                Object secondArg = args.get(1);

                //should always be allowed, this is on me
                int playerCount = (int) firstArg;
                boolean sameTurnSelfAnswer = (boolean) secondArg;

                mGameBuffer = new GameBuffer(playerCount, sameTurnSelfAnswer);

                PostThoughtFragment postQuestion = PostThoughtFragment.newInstance();

                fm = getSupportFragmentManager();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.contentFragment, postQuestion);
                transaction.commit();

                break;
            case R.id.button_postThought:
                mGameBuffer.addThought((String) args.get(0));

                //if game is ready for this
                GameBuffer.Pair thoughtPair = mGameBuffer.removePair();
                ReadPairFragment readPairFragment = ReadPairFragment.newInstance(thoughtPair.getLeft(), thoughtPair.getRight());

                fm = getSupportFragmentManager();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.contentFragment, readPairFragment);
                transaction.commit();

                break;
            case R.id.button_doneReadingPair:
                break;
            case R.id.button_nextPlayer:
                break;
            case R.id.button_nextPlayerIsReady:
                break;

        }
    }
}
