package com.gregory.revelation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameField#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameField extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "argPlayerCount";
    private static final String ARG_PARAM2 = "argSameTurnSelfAnswerToggle";

    private enum InputState {
        POST_QUESTION, READ_PAIR, ANSWER_QUESTION, PASS_THE_PHONE
    }

    private OnFragmentInteractionListener mListener;

    private InputState inputState;

    public GameField() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GameField.
     */
    public static GameField newInstance(int bufferSize, boolean sameTurnSelfAnswer) {
        GameField fragment = new GameField();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, bufferSize);
        args.putBoolean(ARG_PARAM2, sameTurnSelfAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) { //if we do this on rotation, the game will mess up
            if (getArguments() != null) {
                int playerCount = getArguments().getInt(ARG_PARAM1);
                boolean sameTurnSelfAnswer = getArguments().getBoolean(ARG_PARAM2);
                mGameBuffer = new GameBuffer(playerCount, sameTurnSelfAnswer);
            }
            this.inputState = InputState.POST_QUESTION;
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
        goToStateVisibility(InputState.POST_QUESTION, view);

        Button nextStageButton = view.findViewById(R.id.nextButton);
        nextStageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(R.id.nextButton);
            }
        });
    }

    //this is where we need to handle game state
    public void onButtonPressed(int id) {
        //#TODO: this is *pretty* bad.
        //The obvious alternative is making each step of the game its own fragment
        //I avoided this because of perceived issues but it is obvious now that fragments are better

        //change inputState
        View view = getView();
        if(view != null) {
            if (mGameBuffer.getState() == GameBuffer.State.FULL) {
                switch (inputState) {
                    case POST_QUESTION:
                        EditText editQuestion = view.findViewById(R.id.editQuestion);
                        String questionInput = editQuestion.toString();
                        mGameBuffer.putOpenQuestion(questionInput);
                        editQuestion.getText().clear();

                        goToStateVisibility(InputState.READ_PAIR, view);

                        inputState = InputState.READ_PAIR;
                        break;
                    case READ_PAIR:
                        //nothing, just go to the next stage
                        inputState = InputState.ANSWER_QUESTION;
                        break;
                    case ANSWER_QUESTION:
                        EditText editAnswer = view.findViewById(R.id.editAnswer);
                        String answer = editAnswer.toString();

                        String question = view.findViewById(R.id.questionForPlayer).toString();
                        mGameBuffer.assignAnswer(question, answer);

                        goToStateVisibility(InputState.PASS_THE_PHONE, view);
                        inputState = InputState.PASS_THE_PHONE;
                        break;
                    case PASS_THE_PHONE:
                        inputState = InputState.READ_PAIR;
                        break;
                }
            } else if (mGameBuffer.getState() == GameBuffer.State.COLLECTING_QUESTIONS) {
                //submit the question and reset the ui
                if(inputState == InputState.POST_QUESTION){

                    EditText editQuestion = view.findViewById(R.id.editQuestion);
                    String questionInput = editQuestion.toString();
                    mGameBuffer.putOpenQuestion(questionInput);
                    editQuestion.getText().clear();

                    if(mGameBuffer.getState() == GameBuffer.State.COLLECTING_QUESTIONS) {
                        goToStateVisibility(InputState.PASS_THE_PHONE, view);
                    } else {
                        //only other possible transition is to COLLECTING_ANSWERS
                        goToStateVisibility(InputState.ANSWER_QUESTION, view);
                    }
                } else {
                    //this is PASS_THE_PHONE state
                    goToStateVisibility(InputState.POST_QUESTION, view);
                }

                //pass the phone
            } else if (mGameBuffer.getState() == GameBuffer.State.COLLECTING_ANSWERS) {
                //submit answer
                //no matter what, pass the phone
            }
        }
    }

    //this helps a little bit with this horrible code
    private void goToStateVisibility(InputState inputState, View view){

        EditText enterQuestionBox = view.findViewById(R.id.editQuestion);

        TextView readQuestionBox = view.findViewById(R.id.questionShow);
        TextView readAnswerBox = view.findViewById(R.id.answerShow);

        TextView questionToPlayerBox = view.findViewById(R.id.questionForPlayer);
        EditText enterAnswerBox = view.findViewById(R.id.editAnswer);

        LottieAnimationView background = view.findViewById(R.id.passToNextPlayerBackground);
        TextView flavor = view.findViewById(R.id.flavorText);

        Button button = view.findViewById(R.id.nextButton);

        //I would just make them all invisible up here and then visible down there
        //but I don't want any kind of flicker happening as a result. could be annoying
        switch(inputState){
            case POST_QUESTION:
                button.setText(R.string.submit);

                enterQuestionBox.setVisibility(View.VISIBLE);
                enterAnswerBox.getText().clear();

                readQuestionBox.setVisibility(View.INVISIBLE);
                readAnswerBox.setVisibility(View.INVISIBLE);


                questionToPlayerBox.setVisibility(View.INVISIBLE);
                enterAnswerBox.setVisibility(View.INVISIBLE);

                background.setVisibility(View.INVISIBLE);
                flavor.setVisibility(View.INVISIBLE);

                break;
            case READ_PAIR:
                button.setText(R.string.doneReading);

                enterQuestionBox.setVisibility(View.INVISIBLE);

                readQuestionBox.setVisibility(View.VISIBLE);
                readAnswerBox.setVisibility(View.VISIBLE);

                GameBuffer.Pair questionAnswerPair = mGameBuffer.getQuestionAnswerPair();
                readQuestionBox.setText(questionAnswerPair.getLeft());
                readAnswerBox.setText(questionAnswerPair.getRight());

                questionToPlayerBox.setVisibility(View.INVISIBLE);
                enterAnswerBox.setVisibility(View.INVISIBLE);

                background.setVisibility(View.INVISIBLE);
                flavor.setVisibility(View.INVISIBLE);
                break;
            case ANSWER_QUESTION:
                button.setText(R.string.submit);
                enterQuestionBox.setVisibility(View.INVISIBLE);

                readQuestionBox.setVisibility(View.INVISIBLE);
                readAnswerBox.setVisibility(View.INVISIBLE);

                questionToPlayerBox.setVisibility(View.VISIBLE);
                enterAnswerBox.setVisibility(View.VISIBLE);

                questionToPlayerBox.setText(mGameBuffer.removeOpenQuestion());
                enterAnswerBox.getText().clear();

                background.setVisibility(View.INVISIBLE);
                flavor.setVisibility(View.INVISIBLE);
                break;
            case PASS_THE_PHONE:
                button.setText(R.string.imReady);
                enterQuestionBox.setVisibility(View.INVISIBLE);

                readQuestionBox.setVisibility(View.INVISIBLE);
                readAnswerBox.setVisibility(View.INVISIBLE);

                questionToPlayerBox.setVisibility(View.INVISIBLE);
                enterAnswerBox.setVisibility(View.INVISIBLE);

                background.setVisibility(View.VISIBLE);
                flavor.setVisibility(View.VISIBLE);
                flavor.setText(FlavorGenerator.grabFlavor());
                break;
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
