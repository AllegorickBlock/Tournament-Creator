package jbourlet.app.tournamentcreator.models;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static jbourlet.app.tournamentcreator.models.DatabaseTournament.TOURNAMENT_TABLE_CREATE;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db.sqlite";
    public static final int DATABASE_VERSION = 1;
    private static DatabaseHandler sInstance;

    //private final Context mycontext;

    public static synchronized DatabaseHandler getInstance(Context context){
        if(sInstance == null){sInstance = new DatabaseHandler(context);}
        return sInstance;
    }

    private DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TOURNAMENT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        onCreate(db);
    }
}