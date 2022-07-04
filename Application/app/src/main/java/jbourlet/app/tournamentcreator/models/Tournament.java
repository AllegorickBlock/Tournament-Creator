package jbourlet.app.tournamentcreator.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Tournament implements Serializable {
    int mId;
    String mTitleTournament;
    int mNumberPlayer;
    boolean mBoolLooser;
    int mBO;
    boolean mFinish;

    public Tournament(int mId, String mTitleTournament, int mNumberPlayer, boolean mBoolLooser, int mBO) {
        this.mId = mId;
        this.mTitleTournament = mTitleTournament;
        this.mNumberPlayer = mNumberPlayer;
        this.mBoolLooser = mBoolLooser;
        this.mBO = mBO;
        this.mFinish = false;
    }

    public void setmTitleTournament(String mTitleTournament) { this.mTitleTournament = mTitleTournament;
    }

    public int getmId() { return mId; }

    public String getmTitleTournament() {
        return mTitleTournament;
    }

    public boolean ismFinish() {
        return mFinish;
    }

    public int getmNumberPlayer() {
        return mNumberPlayer;
    }

    public void setmNumberPlayer(int mNumberPlayer) {
        this.mNumberPlayer = mNumberPlayer;
    }

    public boolean ismBoolLooser() {
        return mBoolLooser;
    }

    public void setmBoolLooser(boolean mBoolLooser) {
        this.mBoolLooser = mBoolLooser;
    }

    public int getmBO() {
        return mBO;
    }

    public void setmBO(int mBO) {
        this.mBO = mBO;
    }

    public Boolean getmFinish() {return mFinish;}

}
