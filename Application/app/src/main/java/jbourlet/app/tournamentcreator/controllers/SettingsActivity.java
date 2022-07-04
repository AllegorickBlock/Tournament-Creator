package jbourlet.app.tournamentcreator.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import jbourlet.app.tournamentcreator.R;
import jbourlet.app.tournamentcreator.models.DatabaseTournament;
import jbourlet.app.tournamentcreator.models.ListTournament;
import jbourlet.app.tournamentcreator.models.Tournament;

public class SettingsActivity extends AppCompatActivity {

    public static final String ANSWER_EXTRA = "1234";
    private AutoCompleteTextView mlistTournament;
    private EditText mNumberPlayer;
    private CheckBox mLooserBracket;
    private RadioGroup mBO;
    private RadioButton mBoButton;
    private Button mSaveButton;
    private ListTournament listTournament;
    private int numberPlayer;
    private int BO;
    private String nameTournament;
    private boolean losserBracket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mlistTournament = (AutoCompleteTextView) findViewById(R.id.list_preset);
        mNumberPlayer   = (EditText) findViewById(R.id.number_player);
        mLooserBracket  = (CheckBox) findViewById(R.id.checkbox_value);
        mBO             = (RadioGroup) findViewById(R.id.radioGroup_BO);
        mSaveButton     = (Button) findViewById(R.id.saveButton);
        mBoButton       = (RadioButton) findViewById(R.id.radioButton_1);
        listTournament  = new ListTournament();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int selectedId = mBO.getCheckedRadioButtonId();
                    mBoButton = (RadioButton) findViewById(selectedId);
                    nameTournament = mlistTournament.getText().toString();
                    numberPlayer = Integer.parseInt(mNumberPlayer.getText().toString());
                    losserBracket = mLooserBracket.isChecked();
                    BO = Integer.parseInt(mBoButton.getText().toString());
                }
                catch (Exception e){
                    Toast.makeText(SettingsActivity.this, "Veuillez remplir le formulaire", Toast.LENGTH_SHORT).show();
                }


                if (nameTournament.equals("") || numberPlayer == 0 || BO == 0){
                    Toast.makeText(SettingsActivity.this, "Veuillez remplir le formulaire", Toast.LENGTH_SHORT).show();
                }
                else addTournament(nameTournament,numberPlayer,losserBracket,BO,listTournament);
            }
        });
    }
//----------- Ajoute le tournoi à la bdd et clean l'écran
    private void addTournament(String _nameTournament, int _numberPlayer, boolean _losserBracket, int _BO, ListTournament _listtournament) {
        Tournament _tournament = new Tournament(0,_nameTournament,_numberPlayer,_losserBracket,_BO);
        Toast.makeText(this, "Tournoi "+ _tournament.getmTitleTournament() + " créé.", Toast.LENGTH_SHORT).show();
        addTournamentDatabase(_tournament);
        cleanSettings();

    }
//Vide tout les champs du tournoi
    private void cleanSettings() {
        mlistTournament.setText("");
        mNumberPlayer.setText("");
        mLooserBracket.setChecked(false);
        mBO.clearCheck();
    }

    private void addTournamentDatabase(Tournament tournament) {
        DatabaseTournament dbTournament = new DatabaseTournament(this);

        dbTournament.addTournament(tournament);
        dbTournament.readDatabase();
        dbTournament.close();
    }
}
