package com.gregory.revelation.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gregory.revelation.GameBuffer;
import com.gregory.revelation.MainActivity;
import com.gregory.revelation.OnFragmentInteractionListener;
import com.gregory.revelation.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostThoughtFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostThoughtFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private static final String ARG_PARAM1 = "color";
    private int color;

    public PostThoughtFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PostThoughtFragment.
     */
    public static PostThoughtFragment newInstance(int color) {
        PostThoughtFragment fragment = new PostThoughtFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
             color = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_question, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        view.findViewById(R.id.postQuestionLayout).setBackgroundColor(color);

        EditText questionBox = view.findViewById(R.id.editText_editThought);

        questionBox.setImeOptions(EditorInfo.IME_ACTION_DONE);
        questionBox.setRawInputType(InputType.TYPE_CLASS_TEXT);
        questionBox.requestFocus();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(questionBox, InputMethodManager.SHOW_IMPLICIT);

        Button postThoughtButton = view.findViewById(R.id.button_postThought);
        postThoughtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(R.id.button_postThought);
            }
        });
    }

    public void onButtonPressed(int id) {
        if (mListener != null) {
            if(id == R.id.button_postThought) {
                View view = getView();
                if(view != null) {
                    EditText thoughtText = getView().findViewById(R.id.editText_editThought);
                    String thought = thoughtText.getText().toString();
                    if (thought.length() >= 4) {
                        ArrayList<Object> args = new ArrayList<>();
                        args.add(thought);

                        mListener.onFragmentInteraction(id, args);
                    } else {
                        Activity activity = getActivity();
                        if(activity != null) {
                            Context context = activity.getApplicationContext();
                            CharSequence text = getResources().getString(R.string.warning_thoughtTooShort);
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                        //how does this ever happen?
                    }
                }
                //how does this ever happen?
            }
            //no other options on this fragment right now
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
