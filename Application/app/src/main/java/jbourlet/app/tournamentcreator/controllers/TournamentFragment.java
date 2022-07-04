package jbourlet.app.tournamentcreator.controllers;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import jbourlet.app.tournamentcreator.models.Tournament;

public class TournamentFragment extends Fragment {
    protected Tournament mTournament;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
}
