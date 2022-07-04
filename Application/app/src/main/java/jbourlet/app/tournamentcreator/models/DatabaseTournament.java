package jbourlet.app.tournamentcreator.models;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseTournament {

    public static final String TOURNAMENT_ID = "id";
    public static final String TOURNAMENT_NAME = "nom";
    public static final String NUMBER_PLAYER = "numberPlayer";
    public static final String TOURNAMENT_TABLE_NAME = "Tournament";
    public static final String BOOL_LOOSER = "looser";
    public static final String BO = "bo";

    public static final String TOURNAMENT_TABLE_CREATE =
            "CREATE TABLE "+ TOURNAMENT_TABLE_NAME + " ("+
                    " "+ TOURNAMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " "+ TOURNAMENT_NAME + " TEXT,"+
                    " "+ NUMBER_PLAYER + " REAL,"+
                    " "+ BOOL_LOOSER + " BIT,"+
                    " "+ BO + " REAL"+
                    ");";

    private DatabaseHandler myBaseSQLite;
    private SQLiteDatabase db;


    public DatabaseTournament(Context context){
        myBaseSQLite = DatabaseHandler.getInstance(context);
    }

    public void open(){
        db = myBaseSQLite.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public long addTournament (Tournament tournament){
        open();
        ContentValues values = new ContentValues();
        values.put(TOURNAMENT_NAME,
                tournament.getmTitleTournament());
        values.put(NUMBER_PLAYER,
                tournament.getmNumberPlayer());
        values.put(BOOL_LOOSER,
                tournament.ismBoolLooser());
        values.put(BO,
                tournament.getmBO());

        return db.insert(TOURNAMENT_TABLE_NAME,null,values);


    }

    public int modTournament (Tournament tournament){
        ContentValues values = new ContentValues();
        values.put(TOURNAMENT_NAME, tournament.getmTitleTournament());

        String where = TOURNAMENT_NAME+" = ?";
        String [] whereArgs = {tournament.getmId()+""};

        return db.update(TOURNAMENT_TABLE_NAME,values,where,whereArgs);
    }
//Supprime un tournoi précis
    public int supTournament(Tournament tournament){
        open();
        String where = TOURNAMENT_ID+" = ?";
        String[] whereArgs = {tournament.getmId()+""};

        return db.delete(TOURNAMENT_TABLE_NAME,where,whereArgs);

    }
//Récupère un tournoi précis
    public Tournament getTournament(int id){

        Tournament tournament = new Tournament(0,"",0,false,0);

        Cursor c = db.rawQuery("SELECT * FROM "+TOURNAMENT_NAME+" WHERE "+TOURNAMENT_ID+"="+id,null);
        if (c.moveToFirst()){
            tournament.setmTitleTournament(c.getString(c.getColumnIndex(TOURNAMENT_NAME)));
            
            c.close();
        }
        
        return tournament;
    }
    
    public Cursor getTournament(){
        return db.rawQuery("SELECT * FROM "+TOURNAMENT_TABLE_NAME,null);
    }
//Retounre la liste de tournois
    public ListTournament readDatabaseList() {
        /*--- Read database to list*/
       Cursor c = this.getTournament();

       ListTournament listTournamentdb = new ListTournament();

       if(c.moveToFirst()){
           do {
               int ID = c.getInt(c.getColumnIndex(DatabaseTournament.TOURNAMENT_ID));
               String tournamentName = c.getString(c.getColumnIndex(DatabaseTournament.TOURNAMENT_NAME));
               int numberPlayer = c.getInt(c.getColumnIndex(DatabaseTournament.NUMBER_PLAYER));
               boolean boolLooser =  Boolean.parseBoolean(c.getString(c.getColumnIndex(DatabaseTournament.BOOL_LOOSER))) ;
               int bo = c.getInt(c.getColumnIndex(DatabaseTournament.BO));



               Tournament tournament = new Tournament(ID,tournamentName,numberPlayer,boolLooser,bo);
               listTournamentdb.addTournament(tournament);
           }
           while (c.moveToNext());
       }

        return listTournamentdb;

    }

    //Permet d'avoir la liste des tournoi dans la console
    public void readDatabase() {
               /*--- Read database to log*/
        Cursor c = this.getTournament();
        if(c.moveToFirst()){
            do{
                Log.d("test",
                            c.getInt(c.getColumnIndex(DatabaseTournament.TOURNAMENT_ID))+","+
                                  c.getString(c.getColumnIndex(DatabaseTournament.TOURNAMENT_NAME))+","+
                                  c.getInt(c.getColumnIndex(DatabaseTournament.NUMBER_PLAYER))+","+
                                  c.getInt(c.getColumnIndex(DatabaseTournament.BOOL_LOOSER))+","+
                                    c.getInt(c.getColumnIndex(DatabaseTournament.BO)));
            }
            while (c.moveToNext());
        } 
    }
}