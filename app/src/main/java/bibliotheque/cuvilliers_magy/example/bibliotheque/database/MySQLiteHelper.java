package bibliotheque.cuvilliers_magy.example.bibliotheque.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;

/**
 * Created by magy on 06/10/16.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "livres.db";
    public static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase database;
    public static final String DATABASE_CREATE = "" +
            "CREATE TABLE IF NOT EXISTS livre (id integer primary key autoincrement, titre VARCHAR(30)," +
            "auteur VARCHAR(30), resume VARCHAR(300), genre VARCHAR(30), serie VARCHAR(30)," +
            "editeur VARCHAR(30), image integer, isbn VARCHAR(15));\n";


    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME);
        database = super.getWritableDatabase();
        database.execSQL(DATABASE_CREATE);
        //this.insertTestValues();
    }

    public static void insertValues(int id,String titre, String auteur, String genre, String serie, String editeur){
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("titre", titre);
        values.put("auteur", auteur);
        values.put("resume", "La calotte de tes morts");
        values.put("genre", genre);
        values.put("serie", serie);
        values.put("editeur", editeur);
        values.put("image", R.drawable.booba);

        database.insertWithOnConflict("livre", null, values, SQLiteDatabase.CONFLICT_REPLACE);

    }

    public void insertTestValues(){
        ContentValues values = new ContentValues();
        //values.put("id", 2);
        values.put("titre", "Titeuf : po juste");
        values.put("auteur", "zep");
        values.put("resume", "Titeuf de retour pour de nouvelles bêtises");
        values.put("genre", "Comédie");
        values.put("serie", "titeuf le retour");
        values.put("editeur", "glenat");
        values.put("image", R.drawable.titeuf);
        values.put("isbn", "6666655555");

        ContentValues secondValues = new ContentValues();
        //secondValues.put("id", 1);
        secondValues.put("titre", "Booba le petit ourson");
        secondValues.put("auteur", "92i");
        secondValues.put("resume", "Qu'est-ce que je vais faire de tout cet oseille ?");
        secondValues.put("genre", "Rap francais");
        secondValues.put("serie", "Le duc de boulogne");
        secondValues.put("editeur", "Skyrock");
        secondValues.put("image", R.drawable.booba);
        secondValues.put("isbn", "5555566666");

        database.insertWithOnConflict("livre", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        database.insertWithOnConflict("livre", null, secondValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void deleteBookByID(String bookID){
        database.delete("livre", "id=?", new String[]{bookID});
    }

    public static ArrayList<Book> searchBooksByTitle(String query){
        ArrayList<Book> bookList = new ArrayList<>();
        String[] columns = new String[8];
        columns[0] = "id";
        columns[1] = "titre";
        columns[2] = "auteur";
        columns[3] = "resume";
        columns[4] = "genre";
        columns[5] = "serie";
        columns[6] = "editeur";
        columns[7] = "image";
        columns[8] = "isbn";

        Cursor cursor = database.query(true, "livre", columns, "titre" + " LIKE ?",
                new String[] {"%"+ query + "%" }, null, null, null,
                null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Log.v("CURSOR MOVING", "CURSOR MOVING");
            int id = cursor.getInt(0);
            String titre = cursor.getString(1);
            String auteur = cursor.getString(2);
            String resume = cursor.getString(3);
            String genre = cursor.getString(4);
            String serie = cursor.getString(5);
            String editeur = cursor.getString(6);
            int couverture = cursor.getInt(7);
            String isbn = cursor.getString(8);

            Book currentBook = new Book(auteur, titre, isbn, serie,genre,editeur,"",couverture
                    ,new ArrayList<String>(),resume,new ArrayList<String>());
            bookList.add(currentBook);

            cursor.moveToNext();
        }
        cursor.close();

        return bookList;
    }

    public ArrayList<Book> getAllBooks(){

        String[] columns = new String[8];
        columns[0] = "id";
        columns[1] = "titre";
        columns[2] = "auteur";
        columns[3] = "resume";
        columns[4] = "genre";
        columns[5] = "serie";
        columns[6] = "editeur";
        columns[7] = "image";

        Cursor cursor = database.query("livre", columns,
        null, null, null, null, null, null);

        ArrayList<Book> bookList = new ArrayList<>();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String titre = cursor.getString(1);
            String auteur = cursor.getString(2);
            String resume = cursor.getString(3);
            String genre = cursor.getString(4);
            String serie = cursor.getString(5);
            String editeur = cursor.getString(6);
            int couverture = cursor.getInt(7);

            Book currentBook = new Book(auteur, titre, Integer.toString(id),serie,genre,editeur,"",couverture
                    ,new ArrayList<String>(),resume,new ArrayList<String>());
            bookList.add(currentBook);

            cursor.moveToNext();
        }
        cursor.close();

        return bookList;
    }
}
