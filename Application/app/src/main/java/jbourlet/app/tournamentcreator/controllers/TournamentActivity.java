package jbourlet.app.tournamentcreator.controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import jbourlet.app.tournamentcreator.R;
import jbourlet.app.tournamentcreator.models.Tournament;

public class TournamentActivity extends AppCompatActivity {

    private LinearLayout mRootTournament;
    private int numberPlayer;
    private int playerLeft;
    private ArrayList<TextView> listOfPseudo;
    private ArrayList<ArrayList> listOfListPseudo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tournament_activity);

        Intent mIntent = getIntent();
        Tournament mTournament = (Tournament)mIntent.getSerializableExtra("tournament");

        mRootTournament = (LinearLayout) findViewById(R.id.tournament_Root);
        numberPlayer = mTournament.getmNumberPlayer();
        playerLeft = numberPlayer;
        listOfListPseudo = new ArrayList<ArrayList>();

        initBracket();
   }

   //Initialise le tournois
    private void initBracket() {
        int nbrRoundMax = numberPlayer / 2;
        for (int i = 0; i < nbrRoundMax +1; i++){
            generateRound(playerLeft,i);
        }

    }

    //génére les différents round du tournois dépendant du nombre de participants initiaux
    private void generateRound(int mplayerleft, int nbrRound) {
        LinearLayout roundTournament = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        switch (nbrRound){
            case 0:
                params.setMargins(10,0,10,0);
                break;
            case 1:
                params.setMargins(10,150,10,0);
                break;
            case 2:
                params.setMargins(10,250,10,0);
                break;
            case 3:
                params.setMargins(10,325,10,0);
                break;

        }
        roundTournament.setTag(nbrRound);
        roundTournament.setOrientation(LinearLayout.VERTICAL);
        listOfPseudo = new ArrayList<>();

        for(int i = 0;i < mplayerleft;i++){
            TextView playerView = getUserView();
            listOfPseudo.add(playerView);
            if (nbrRound == 0){setClickOnPlayerName(playerView,roundTournament);}
            roundTournament.addView(playerView);
        }
        roundTournament.setLayoutParams(params);
        this.listOfListPseudo.add(listOfPseudo);
        mRootTournament.addView(roundTournament);
        playerLeft = playerLeft/2;
        generateButtonMatch(playerLeft,nbrRound);
    }

    //Permet d'afficher les boutons qui correspond à 1 match
    private void generateButtonMatch(int playerleft, int nbrRound) {
        if(playerleft >= 1){
            LinearLayout buttonRoundTournament = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonRoundTournament.setTag(nbrRound);
            switch (nbrRound){
                case 0:
                    params.setMargins(10,50,10,0);
                    break;
                case 1:
                    params.setMargins(10,185,10,0);
                    break;
                case 2:
                    params.setMargins(10,315,10,0);
                    break;
            }

            buttonRoundTournament.setOrientation(LinearLayout.VERTICAL);
            buttonRoundTournament.setLayoutParams(params);

            for (int i = 0; i < playerleft; i++){
                View buttonRoundView = getButtonRoundView();
                buttonRoundView.setTag(i);
                setOnClickMatch(buttonRoundView,buttonRoundTournament);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params2.bottomMargin = 70;
                buttonRoundTournament.addView(buttonRoundView,params2);
                }
            mRootTournament.addView(buttonRoundTournament);
        }
    }

    private View getButtonRoundView() {
        Button button = new Button(getApplicationContext());
        button.setPadding(0,30,0,30);
        button.setText("Match");
        return button;
    }

    private TextView getUserView(){
        TextView namePlayer = getTextView();
        namePlayer.setPadding(0,30,0,30);
        return namePlayer;
    }

    private TextView getTextView() {
        TextView textView = new TextView(getApplicationContext());
        textView.setText("Default");
        return textView;
    }

    private void setClickOnPlayerName(final TextView tournamentView, final LinearLayout roundTournament){

        tournamentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPseudo = tournamentView.getText().toString();
                popUpNewPseudo(tournamentView,roundTournament,oldPseudo);
            }
        });
    }

    private void setOnClickMatch(final View buttonRoundTournament, final LinearLayout linearLayout) {
        buttonRoundTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopUpMatch(buttonRoundTournament,linearLayout);
            }

            private void openPopUpMatch(View buttonRound, LinearLayout linearLayout) {
                generatePopUpMatch(buttonRound,linearLayout);
            }
        });
    }

    private void popUpNewPseudo(final TextView tournamentView, LinearLayout roundTournament, String oldPseudo) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_pseudo,null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height,true);

        ((TextView)popupWindow.getContentView().findViewById(R.id.old_pseudo)).setText(oldPseudo);

        Button mchangePseudoButton = popupView.findViewById(R.id.change_pseudo);
        mchangePseudoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newPseudoEdit = ((EditText)popupWindow.getContentView().findViewById((R.id.pseudo_player)));
                final String newPseudo = newPseudoEdit.getText().toString();
                tournamentView.setText(newPseudo);
            }
            });
        popupWindow.showAtLocation(tournamentView, Gravity.CENTER,0,0);
    }

    private void generatePopUpMatch(View view, LinearLayout linearLayout){


        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_match,null);

        int idTagBtn = Integer.parseInt(view.getTag().toString());
        int idLinearBtn = Integer.parseInt(linearLayout.getTag().toString());

        int idTagBtn_2 = (idTagBtn*2);
        final TextView textView1 = (TextView)listOfListPseudo.get(idLinearBtn).get(idTagBtn_2);
        final TextView textView2 = (TextView)listOfListPseudo.get(idLinearBtn).get(idTagBtn_2+1);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height,true);

        ((TextView)popupWindow.getContentView().findViewById(R.id.name_player1)).setText(textView1.getText().toString());
        ((TextView)popupWindow.getContentView().findViewById(R.id.name_player2)).setText(textView2.getText().toString());
        Button winner1Button = popupView.findViewById(R.id.winner_1);
        Button winner2Button = popupView.findViewById(R.id.winner_2);

        final int finalIdTagBtn = idTagBtn;
        final int finalIdLinearBtn = idLinearBtn;
        winner1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWinnerPseudo(textView1,finalIdLinearBtn, finalIdTagBtn);
            }
        });
        winner2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWinnerPseudo(textView2,finalIdLinearBtn, finalIdTagBtn);
            }
        });
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        }

    private void setWinnerPseudo(TextView textView1, int idLinearBtn, int idTagBtn) {
        LinearLayout ll = new LinearLayout(getApplicationContext());
        switch (idLinearBtn){
            case 0:
                ll = (LinearLayout)mRootTournament.getChildAt(idLinearBtn+2);
                break;
            case 1:
                ll = (LinearLayout)mRootTournament.getChildAt(idLinearBtn+3);
                break;
            case 2:
                ll = (LinearLayout)mRootTournament.getChildAt(idLinearBtn+4);
                break;
        }
        TextView tv = (TextView) ll.getChildAt(idTagBtn);
        tv.setText(textView1.getText().toString());
    }

}


