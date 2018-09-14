package com.gregory.revelation.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gregory.revelation.OnFragmentInteractionListener;
import com.gregory.revelation.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReadPairFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadPairFragment extends Fragment {
    private static final String ARG_PARAM1 = "thought";
    private static final String ARG_PARAM2 = "response";

    private String thought;
    private String response;

    private OnFragmentInteractionListener mListener;

    public ReadPairFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param thought the thought to be read out loud
     * @param response the response to be read out loud
     * @return A new instance of fragment ReadPairFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadPairFragment newInstance(String thought, String response) {
        ReadPairFragment fragment = new ReadPairFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, thought);
        args.putString(ARG_PARAM2, response);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            thought = getArguments().getString(ARG_PARAM1);
            response = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_pair, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(id, null);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //set up visibility for first screen

        TextView thoughtView = view.findViewById(R.id.textView_questionToBeRead);
        TextView answerView = view.findViewById(R.id.textView_answerToBeRead);

        thoughtView.setText(thought);
        answerView.setText(response);

        Button doneReadingButton = view.findViewById(R.id.button_doneReadingPair);
        doneReadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(R.id.button_doneReadingPair);
            }
        });
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
