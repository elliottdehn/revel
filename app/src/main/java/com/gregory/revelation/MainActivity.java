package com.gregory.revelation;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.hide();
        }

        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) { //this is done so that rotations don't reset the fragment
            Fragment fragment = new Intro();

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, fragment);
            transaction.commit();
        }
    }

    //this method is really ugly. I need to find a better structure for the application/way to do this.
    public void onFragmentInteraction(int id, List<Object> args) {
        if(id == R.id.beginButton) {
            Fragment fragment = new PlayerCount();

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, fragment);
            transaction.commit();
        } else if (id == R.id.goButton) {
            //this is pretty ugly...
            Fragment fragment;
            if(args != null){
                if(args.size() > 0){
                    Object firstArg = args.get(0);
                    if(firstArg instanceof Integer){
                        fragment = GameField.newInstance((int) firstArg);
                    } else {
                        fragment = GameField.newInstance(3);
                    }
                } else {
                    fragment = GameField.newInstance(3);
                }
            } else {
                fragment = GameField.newInstance(3);
            }

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, fragment);
            transaction.commit();
        }
    }
}
