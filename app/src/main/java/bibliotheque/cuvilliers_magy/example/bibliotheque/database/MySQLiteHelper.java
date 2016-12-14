package bibliotheque.cuvilliers_magy.example.bibliotheque.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;

/**
 * Created by magy on 06/10/16.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String BOOK_TABLE = "book";
    public static final String AUTHOR_TABLE = "author";
    public static final String BOOK_BY_AUTHOR_TABLE = "book_by_author";

    // BOOK //
    public static final String TITLE_COLUMN = "title";
    public static final String DESCRITPION_COLUMN = "description";
    public static final String CATEGORIE_COLUMN = "categorie";
    public static final String IMAGE_COLUMN = "image";
    public static final String PUBLISHER_COLUMN = "publisher";

    // AUTHOR //
    public static final String AUTHOR_NAME_COLULMN = "name";

    // BOOK_BY_AUTHOR //
    public static final String BOOK_ID_COLUMN = "idBook";
    public static final String AUTHOR_ID_COLUMN = "idAuthor";

    public static final String DATABASE_NAME = "livres.db";
    public static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase database;
    public static final String DATABASE_CREATE_BOOK = "" +
            "CREATE TABLE IF NOT EXISTS book (id integer primary key autoincrement, title VARCHAR(50)," +
            "description VARCHAR(500), categorie VARCHAR(100)," +
            "publisher VARCHAR(30), image VARCHAR(200));\n";

    public static final String DATABASE_CREATE_BOOK_BY_AUTHOR = "" +
            "CREATE TABLE IF NOT EXISTS book_by_author (idBook integer, idAuthor integer," +
            " primary key(idBook, idAuthor));\n";

    public static final String DATABASE_CREATE_AUTHOR = "" +
            "CREATE TABLE IF NOT EXISTS author (idAuthor integer primary key autoincrement, name VARCHAR(50));\n";


    public MySQLiteHelper(Context context) {
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

    public static int addBook(Book book) {
        ContentValues values = new ContentValues();
        values.put(TITLE_COLUMN, book.getTitle());
        values.put(DESCRITPION_COLUMN, book.getDescription());
        values.put(CATEGORIE_COLUMN, book.getCategorie());
        values.put(PUBLISHER_COLUMN, book.getPublisher());
        values.put(IMAGE_COLUMN, book.getImage());
        long bookID = database.insertWithOnConflict(BOOK_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        return ((int) bookID);
    }

    public static void deleteBookByID(String bookID) {
        database.delete(BOOK_TABLE, "id=?", new String[]{bookID});
        database.delete(BOOK_BY_AUTHOR_TABLE, "idBook=?", new String[]{bookID});
    }

    public static void linkBookWithAuthor(int bookID, int authorID) {
        ContentValues values = new ContentValues();
        values.put("idBook", bookID);
        values.put("idAuthor", authorID);
        database.insertWithOnConflict(BOOK_BY_AUTHOR_TABLE, null, values, SQLiteDatabase.CONFLICT_ABORT);
    }

    public static int getAuthorID(String authorName) {
        int authorID = -1;
        String[] columns = new String[1];
        columns[0] = AUTHOR_ID_COLUMN;
        Cursor cursor = database.query(AUTHOR_TABLE, columns,
                "name = ?", new String[]{authorName}, null, null, null);
        cursor.moveToFirst();
        authorID = cursor.getInt(0);
        cursor.close();
        return authorID;
    }

    public static int addAuthor(String authorName) {
        ContentValues values = new ContentValues();
        values.put(AUTHOR_NAME_COLULMN, authorName);
        // CONFLICT ABORT IF AUTHOR ALREADY EXISTS
        long authorID = database.insertWithOnConflict(AUTHOR_TABLE, null, values, SQLiteDatabase.CONFLICT_ABORT);
        if (authorID == -1) {
            // IF AUTHOR ALREADY EXISTS GET HIS ID
            return getAuthorID(authorName);
        } else {
            // ELSE RETURN NEW ID INSERTED
            Log.v("New author ID : ", Long.toString(authorID));
            return ((int) authorID);
        }
    }

    public static String[] getAllAuthorsIDByBookID(int bookID) {
        String[] columns = new String[2];
        columns[0] = BOOK_ID_COLUMN;
        columns[1] = AUTHOR_ID_COLUMN;
        Log.v("get authors with ID : ", Integer.toString(bookID));
        Cursor cursor = database.query(BOOK_BY_AUTHOR_TABLE, columns,
                "idBook = ?", new String[]{Integer.toString(bookID)}, null, null, null);

        cursor.moveToFirst();
        String[] authorsID = new String[cursor.getCount()];
        int index = 0;
        while (!cursor.isAfterLast()) {
            int currentAuthorID = cursor.getInt(1);
            Log.v("Author ID : ", Integer.toString(cursor.getInt(1)));
            authorsID[index] = Integer.toString(currentAuthorID);
            index++;
            cursor.moveToNext();
        }
        cursor.close();
        return authorsID;
    }

    public static ArrayList<String> getAllAuthorsByBookID(int bookID) {
        String[] authorsID = getAllAuthorsIDByBookID(bookID);
        String[] columns = new String[]{AUTHOR_NAME_COLULMN};

        Cursor cursor = database.query(AUTHOR_TABLE, columns,
                "idAuthor IN (?)", authorsID, null, null, null);
        cursor.moveToFirst();
        ArrayList<String> authorsName = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            authorsName.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return authorsName;
    }

    public ArrayList<Book> getAllBooks() {

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
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String categorie = cursor.getString(3);
            String publisher = cursor.getString(4);
            String image = cursor.getString(5);

            ArrayList<String> authors = getAllAuthorsByBookID(id);
            Log.v("Authors", authors.toString());
            Book currentBook = new Book(id, title, authors, description, categorie, publisher, image);
            bookList.add(currentBook);
            cursor.moveToNext();
        }
        cursor.close();
        return bookList;
    }
}
