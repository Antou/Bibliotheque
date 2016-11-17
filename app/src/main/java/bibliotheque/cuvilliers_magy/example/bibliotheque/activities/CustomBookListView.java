package bibliotheque.cuvilliers_magy.example.bibliotheque.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;
import bibliotheque.cuvilliers_magy.example.bibliotheque.adapter.BookAdapter;
import bibliotheque.cuvilliers_magy.example.bibliotheque.database.MySQLiteHelper;
import bibliotheque.cuvilliers_magy.example.bibliotheque.fragment.BookDetailFragment;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;

/**
 * Created by Alessandro on 17/10/2016.
 */

public class CustomBookListView extends Activity {

    ListView list;
    BookAdapter adapter;
    public CustomBookListView customListView = null;
    public ArrayList<Book> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_livres);

        customListView = this;

        MySQLiteHelper dbhelper = new MySQLiteHelper(this);
        bookList = dbhelper.getAllBooks();

        Resources res =getResources();
        list = ( ListView )findViewById( R.id.booklist );  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        adapter = new BookAdapter(customListView, bookList, res);
        list.setAdapter( adapter );
    }

    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {
        Book book = bookList.get(mPosition);
        this.detailBookFragment(book);
    }

    public void detailBookFragment(Book book){

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            return;
        }

        else {
            FragmentManager fm = this.getFragmentManager();
            FragmentTransaction fTransaction = fm.beginTransaction();

            BookDetailFragment bookFragment = new BookDetailFragment();
            bookFragment.setBook(book);
            fTransaction.replace(R.id.bookFragment, bookFragment);
            fTransaction.commit();
        }
    }
}
