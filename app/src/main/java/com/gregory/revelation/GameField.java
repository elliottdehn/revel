package com.gregory.revelation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameField#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameField extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "argPlayerCount";

    private OnFragmentInteractionListener mListener;
    private GameBuffer mGameBuffer;

    public GameField() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GameField.
     */
    // TODO: Rename and change types and number of parameters
    public static GameField newInstance(int bufferSize) {
        GameField fragment = new GameField();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, bufferSize);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) { //if we do this on rotation, the game will mess up
            if (getArguments() != null) {
                int playerCount = getArguments().getInt(ARG_PARAM1);
                mGameBuffer = new GameBuffer(playerCount);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_field, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //set up visibility for first screen
        Button beginButton = view.findViewById(R.id.nextButton);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(R.id.nextButton);
            }
        });
    }

    //this is where we need to handle game state
    public void onButtonPressed(int id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(id, null);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
