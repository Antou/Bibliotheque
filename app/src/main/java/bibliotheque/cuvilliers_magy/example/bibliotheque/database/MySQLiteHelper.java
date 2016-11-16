package bibliotheque.cuvilliers_magy.example.bibliotheque.database;

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
            "CREATE TABLE IF NOT EXISTS livre (id integer primary key, titre VARCHAR(30)," +
            "auteur VARCHAR(30), resume VARCHAR(300));\n";

    public static final String DATABASE_INSERT = "" +
            "INSERT INTO livre VALUES (1, \"Booba le petit ourson\", \"Christophe\", \"Les aventures de l'ourson Booba\")," +
            "(2, \"Titeuf : Po Juste\", \"ZEP\", \"Titeuf de retour pour de nouvelles bêtises\");\n";

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = super.getReadableDatabase();
        database.execSQL(DATABASE_CREATE);
        //database.execSQL(DATABASE_INSERT);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Book> getAllBooks(){

        String[] columns = new String[3];
        columns[0] = "id";
        columns[1] = "titre";
        columns[2] = "auteur";

        Cursor cursor = database.query("livre", columns,
        null, null, null, null, null, null);

        ArrayList<Book> bookList = new ArrayList<>();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String titre = cursor.getString(1);
            String auteur = cursor.getString(2);

            Book currentBook = new Book(auteur, titre, Integer.toString(id));
            bookList.add(currentBook);

            cursor.moveToNext();
        }
        // Close the cursor
        cursor.close();
        return bookList;
    }
}
