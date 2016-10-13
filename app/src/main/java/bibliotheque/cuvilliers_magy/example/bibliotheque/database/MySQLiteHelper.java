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

/**
 * Created by magy on 06/10/16.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "livres.db";
    public static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase database;
    public static final String DATABASE_CREATE = "" +
            "CREATE TABLE livre (id integer primary key, titre VARCHAR(30), auteur VARCHAR(30));\n";

    public static final String DATABASE_INSERT = "" +
            "INSERT INTO livre VALUES (259, \"JUL\", \"ça fait 5 ans que j'nique le game\");\n";

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = super.getReadableDatabase();
        //database.execSQL(DATABASE_INSERT);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Map<String, String>> getAllBooks(){

        String[] columns = new String[3];
        columns[0] = "id";
        columns[1] = "titre";
        columns[2] = "auteur";

        Cursor cursor = database.query("livre", columns,
        null, null, null, null, null, null);

        List<Map<String, String>> listOfBook = new ArrayList<Map<String, String>>();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Log.d("TEST", database.toString());
            int id = cursor.getInt(0);
            String titre = cursor.getString(1);
            String auteur = cursor.getString(2);

            Map<String, String> bookMap = new HashMap<>();
            bookMap.put("img", String.valueOf(R.mipmap.ic_launcher)); // use available img
            bookMap.put("author", auteur);
            bookMap.put("title", titre);
            bookMap.put("isbn", Integer.toString(id));

            listOfBook.add(bookMap);
            cursor.moveToNext();
        }
        // Close the cursor
        cursor.close();
        Log.d("MAP", listOfBook.toString());
        return listOfBook;
    }
}
