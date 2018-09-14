package com.gregory.revelation.fragments;

import android.content.Context;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostResponseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostResponseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "thoughtParam";

    // TODO: Rename and change types of parameters
    private String thought;

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
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_response, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView thoughtToRespondTo = view.findViewById(R.id.textView_questionForPlayer);
        thoughtToRespondTo.setText(thought);

        Button postResponse = view.findViewById(R.id.button_nextPlayer);
        postResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(R.id.button_nextPlayer);
            }
        });
    }

    public void onButtonPressed(int id) {
        if (mListener != null) {
            if(id == R.id.button_nextPlayer){
                View view = getView();
                if(view != null){

                    ArrayList<Object> args = new ArrayList<>();
                    String response = view.findViewById(R.id.editText_editResponse).toString();
                    args.add(thought);
                    args.add(response);

                    mListener.onFragmentInteraction(id, args);
                }
            }
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
