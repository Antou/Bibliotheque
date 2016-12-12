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

    public static final String BOOK_TABLE = "book";
    public static final String AUTHOR_TABLE = "author";
    public static final String BOOK_BY_AUTHOR_TABLE = "book_by_author";

    public static final String TITLE_COLUMN = "title";
    public static final String DESCRITPION_COLUMN = "description";
    public static final String CATEGORIE_COLUMN = "categorie";
    public static final String IMAGE_COLUMN = "image";
    public static final String PUBLISHER_COLUMN = "publisher";

    public static final String DATABASE_NAME = "livres.db";
    public static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase database;
    public static final String DATABASE_CREATE_BOOK = "" +
            "CREATE TABLE IF NOT EXISTS book (id integer primary key autoincrement, title VARCHAR(50)," +
            "description VARCHAR(500), categorie VARCHAR(100)," +
            "publisher VARCHAR(30), image integer);\n";

    public static final String DATABASE_CREATE_BOOK_BY_AUTHOR = "" +
            "CREATE TABLE IF NOT EXISTS book_by_author (idBook integer, idAuthor integer," +
            " primary key(idBook, idAuthor));\n";

    public static final String DATABASE_CREATE_AUTHOR = "" +
            "CREATE TABLE IF NOT EXISTS author (id integer primary key autoincrement," +
            " name VARCHAR(50));\n";


    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME);
        database = super.getWritableDatabase();
        database.execSQL(DATABASE_CREATE_BOOK);
        database.execSQL(DATABASE_CREATE_AUTHOR);
        database.execSQL(DATABASE_CREATE_BOOK_BY_AUTHOR);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void addBook(Book book){
        Log.v("ADD", "BOOK");
        ContentValues values = new ContentValues();
        values.put(TITLE_COLUMN, book.getTitle());
        values.put(DESCRITPION_COLUMN, book.getDescription());
        values.put(CATEGORIE_COLUMN, book.getCategorie());
        values.put(PUBLISHER_COLUMN, book.getPublisher());
        values.put(IMAGE_COLUMN, R.drawable.titeuf);
        database.insertWithOnConflict(BOOK_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void deleteBookByID(String bookID){
        database.delete(BOOK_TABLE, "id=?", new String[]{bookID});
        database.delete(BOOK_BY_AUTHOR_TABLE, "idBook=?", new String[]{bookID});
    }

    public static ArrayList<Book> searchBooksByTitle(String query){
        ArrayList<Book> bookList = new ArrayList<>();
        String[] columns = new String[6];
        columns[0] = "id";
        columns[1] = TITLE_COLUMN;
        columns[2] = DESCRITPION_COLUMN;
        columns[3] = CATEGORIE_COLUMN;
        columns[4] = PUBLISHER_COLUMN;
        columns[5] = IMAGE_COLUMN;

        Cursor cursor = database.query(true, BOOK_TABLE, columns, TITLE_COLUMN + " LIKE ?",
                new String[] {"%"+ query + "%" }, null, null, null,
                null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String categorie = cursor.getString(3);
            String publisher = cursor.getString(4);
            String image = cursor.getString(5);

            Book currentBook = new Book(id, title, description, categorie, publisher, image);
            bookList.add(currentBook);
            cursor.moveToNext();
        }
        cursor.close();
        return bookList;
    }

    public ArrayList<Book> getAllBooks(){

        String[] columns = new String[6];
        columns[0] = "id";
        columns[1] = TITLE_COLUMN;
        columns[2] = DESCRITPION_COLUMN;
        columns[3] = CATEGORIE_COLUMN;
        columns[4] = PUBLISHER_COLUMN;
        columns[5] = IMAGE_COLUMN;

        Cursor cursor = database.query(BOOK_TABLE, columns,
        null, null, null, null, null, null);

        ArrayList<Book> bookList = new ArrayList<>();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String categorie = cursor.getString(3);
            String publisher = cursor.getString(4);
            String image = cursor.getString(5);

            Book currentBook = new Book(id, title, description, categorie, publisher, image);
            bookList.add(currentBook);
            cursor.moveToNext();
        }
        cursor.close();
        return bookList;
    }
}
