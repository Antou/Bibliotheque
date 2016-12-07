package bibliotheque.cuvilliers_magy.example.bibliotheque.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;
import bibliotheque.cuvilliers_magy.example.bibliotheque.adapter.BookAdapter;
import bibliotheque.cuvilliers_magy.example.bibliotheque.database.MySQLiteHelper;
import bibliotheque.cuvilliers_magy.example.bibliotheque.fragment.BookDetailFragment;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;

/**
 * Created by Alessandro on 17/10/2016.
 */

public class CustomBookListView extends AppCompatActivity {

    ListView list;
    BookAdapter adapter;
    public CustomBookListView customListView = null;
    public ArrayList<Book> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_livres);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundColor(0xFF160203);
        setSupportActionBar(myToolbar);

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
        // Print details for each book when clicked on
        Book book = bookList.get(mPosition);
        this.detailBookFragment(book);
    }

    public void detailBookFragment(Book book){
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            // Redirecting to another activity
            this.showBookDetailPortrait(book);
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

    public void showBookDetailPortrait(Book book){
        Intent intent = new Intent(this, BookDetail.class);
        intent.putExtra("book", new Gson().toJson(book));
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Landscape mode
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            // Portrait mode (must destroy fragment)
            FragmentManager fm = this.getFragmentManager();
            FragmentTransaction fTransaction = fm.beginTransaction();
            Fragment fragment = fm.findFragmentById(R.id.bookFragment);
            if (fragment != null){
                fTransaction.remove(fragment);
                fTransaction.commit();
            }
        }
    }
}
