package jbourlet.app.tournamentcreator.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ListTournament extends ArrayList<Tournament>{
    private static ListTournament sListTournament;
    
    private ArrayList<Tournament> mTournaments;

    public ListTournament() {
        mTournaments = new ArrayList<>();
    }

    public void addTournament(Tournament tournament){

        mTournaments.add(tournament);
    }

    public void removeTournament(Tournament tournamentId){
        /*for (Tournament tournament : mTournaments){
            if (tournamentId == tournament.getmId() ){
                mTournaments.remove(tournament);
            }
        }*/


    }

    public static ListTournament get(){
        if (sListTournament == null){
            sListTournament = new ListTournament();
        }
        return sListTournament;
    }
    public List<Tournament> getmTournaments(){
        return mTournaments;
    }

}

