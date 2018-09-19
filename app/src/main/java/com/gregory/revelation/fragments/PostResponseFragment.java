package com.gregory.revelation.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gregory.revelation.OnFragmentInteractionListener;
import com.gregory.revelation.R;
import com.gregory.revelation.Util;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostResponseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostResponseFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "thoughtParam";

    private String thought;
    private int generatedColor;
    private OnFragmentInteractionListener mListener;

    public PostResponseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param thought the thought to respond to
     * @return A new instance of fragment PostResponseFragment.
     */
    public static PostResponseFragment newInstance(String thought) {
        PostResponseFragment fragment = new PostResponseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, thought);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            thought = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_respond_to_question, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView thoughtToRespondTo = view.findViewById(R.id.textView_questionForPlayer);
        thoughtToRespondTo.setText(getResources().getString(R.string.text_display_tapToRevealThought));

        thoughtToRespondTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parent = v.getRootView();

                TextView questionView = (TextView) v;

                //set the box to a random color
                //originally, being able to tap repeatedly for new colors was a mistake
                //but I actually quite like it. I'll be keeping it!
                generatedColor = Util.getRandomColor();
                questionView.setBackgroundColor(generatedColor);

                questionView.setText(thought);

                Button doneButton = parent.findViewById(R.id.button_doneResponding);
                doneButton.setEnabled(true);
                doneButton.setText(getResources().getString(R.string.button_doneResponding));
                doneButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });

        Button doneButton = view.findViewById(R.id.button_doneResponding);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(R.id.button_doneResponding);
            }
        });

        doneButton.setEnabled(false);
        doneButton.setText("waiting...");
    }

    public void onButtonPressed(int id) {
        if (mListener != null) {
            ArrayList<Object> args = new ArrayList<>();
            args.add(generatedColor);
            mListener.onFragmentInteraction(id, args);
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
