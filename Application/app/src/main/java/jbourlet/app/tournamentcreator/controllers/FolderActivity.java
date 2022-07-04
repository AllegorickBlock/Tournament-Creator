package jbourlet.app.tournamentcreator.controllers;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jbourlet.app.tournamentcreator.R;
import jbourlet.app.tournamentcreator.models.DatabaseTournament;
import jbourlet.app.tournamentcreator.models.ListTournament;
import jbourlet.app.tournamentcreator.models.Tournament;

public class FolderActivity extends AppCompatActivity {

    private LinearLayout mListTournament;
    private TextView mTitleTournament;
    private TextView mNumberPlayer;
    private CheckBox mTournamentEnd;
    private Button mLoadButton;
    private Button mDelButton;
    private ListTournament listTournaments;
    private int idTournament;
    private Tournament mTournament;
    private DatabaseTournament tournamentDB;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        tournamentDB = new DatabaseTournament(this);

        mRecyclerView   = (RecyclerView) findViewById(R.id.tournament_recycler_view);
        mListTournament  = (LinearLayout) findViewById(R.id.list_tournament);
        mTitleTournament = (TextView) findViewById(R.id.title_tournament);
        mNumberPlayer    = (TextView) findViewById(R.id.number_player);
        mTournamentEnd   = (CheckBox) findViewById(R.id.tournament_end);
        mLoadButton      = (Button) findViewById(R.id.load_button);
        mDelButton       = (Button) findViewById(R.id.del_button);

        updateTournamentList();

        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTournament == null){
                    Toast.makeText(FolderActivity.this, "D'abord charger un tournoi", Toast.LENGTH_SHORT).show();
                }
                else{ Intent intent = new Intent(FolderActivity.this, TournamentActivity.class);
                intent.putExtra("tournament", mTournament);
                startActivity(intent);}


            }
        });


        mDelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    removeDb(mTournament);
                    mListTournament.removeAllViews();
                    updateTournamentList();
                    clearTournamentView();
                }
              catch (Exception e){
                  Toast.makeText(FolderActivity.this, "D'abord charger un tournoi", Toast.LENGTH_SHORT).show();
              }

            }
        });
    }

    //Retir le tournoi de la db et refresh la liste après la suppression
    private void removeDb(Tournament mTournament) {
        DatabaseTournament dbTournament = new DatabaseTournament(this);
        dbTournament.supTournament(mTournament);
        Toast.makeText(FolderActivity.this, "Tournoi supprimé : " + mTournament.getmTitleTournament(), Toast.LENGTH_SHORT).show();
        this.mTournament = null;
        updateTournamentList();
    }
    //Récupère la liste de la db et l'affiche
    private void updateTournamentList() {

        tournamentDB.open();
        listTournaments = tournamentDB.readDatabaseList();
        tournamentDB.close();

        if (listTournaments != null){
            for (final Tournament tournament : listTournaments.getmTournaments()){
                View tournamentView = getTournamentView(tournament);
                mListTournament.addView(tournamentView);
            }
        }
        else {
            Tournament tournament = new Tournament(1,"@string/defaut",5,false,1);
            View tournamentView = getTournamentView(tournament);
            mListTournament.addView(tournamentView);
        }

    }
    private View getTournamentView(Tournament tournament) {

        LinearLayout columnTournament = new LinearLayout(getApplicationContext());
        columnTournament.setOrientation(LinearLayout.VERTICAL);
        columnTournament.setPadding(8,8,8,8);

        TextView titleView = getTextView(tournament.getmTitleTournament());
        TextView numberView = getTextView(tournament.getmNumberPlayer()+" joueurs");
        titleView.setTextSize(20);
        columnTournament.addView(titleView);
        columnTournament.addView(numberView);
        setClickOnListView(tournament,columnTournament);
        return columnTournament;
    }

    private TextView getTextView(String text) {
        TextView textView = new TextView(getApplicationContext());
        textView.setText(text);
        textView.setLayoutParams(new
                FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return textView;
    }

    private void setClickOnListView(final Tournament tournament, View columnTournament){
        columnTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTournamentView(tournament);
            }
        });
    }

    //récupère le tournoi de la liste pour l'afficher
    private void updateTournamentView(Tournament tournament) {
        String titleTour = tournament.getmTitleTournament();
        int playerTour = tournament.getmNumberPlayer();
        boolean finishTour = tournament.getmFinish();

        mTitleTournament.setText(titleTour);
        mNumberPlayer.setText(""+playerTour);
        mTournamentEnd.setChecked(finishTour);
        idTournament = tournament.getmId();
        this.mTournament = tournament;
    }

    private void clearTournamentView() {
        mTitleTournament.setText("");
        mNumberPlayer.setText("");
    }

}


