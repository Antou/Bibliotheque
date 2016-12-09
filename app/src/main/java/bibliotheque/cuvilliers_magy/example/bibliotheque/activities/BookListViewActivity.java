package bibliotheque.cuvilliers_magy.example.bibliotheque.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Gallery;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;
import bibliotheque.cuvilliers_magy.example.bibliotheque.adapter.BookGalleryAdapter;
import bibliotheque.cuvilliers_magy.example.bibliotheque.adapter.BookListAdapter;
import bibliotheque.cuvilliers_magy.example.bibliotheque.database.MySQLiteHelper;
import bibliotheque.cuvilliers_magy.example.bibliotheque.fragment.BookDetailFragment;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;
import bibliotheque.cuvilliers_magy.example.bibliotheque.scan.BarcodeCaptureActivity;

/**
 * Created by Alessandro on 17/10/2016.
 */

public class BookListViewActivity extends AppCompatActivity {

    static int RC_BARCODE_CAPTURE = 2;

    ListView list;
    BookListAdapter adapter;
    public BookListViewActivity customListView = null;
    public ArrayList<Book> bookList = new ArrayList<>();
    public int[] images;
    private FloatingActionButton addButton;
    private int viewMode = 0; // 0 : LIST -- 1 : GALLERY
    private Book currentBookSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (this.viewMode == 0)
            setContentView(R.layout.activity_liste_livres);
        else
            setContentView(R.layout.activity_liste_livres_mosaique);

        customListView = this;

        MySQLiteHelper dbhelper = new MySQLiteHelper(this);
        bookList = dbhelper.getAllBooks();

        Resources res = getResources();
        list = (ListView)findViewById(R.id.booklist);  // List defined in XML ( See Below )

        if (this.viewMode == 0){
            adapter = new BookListAdapter(customListView, bookList, res);
            list.setAdapter(adapter);
        }

        else {
            int nbImages = bookList.size();
            this.images = new int[nbImages];
            for (int i = 0; i < bookList.size(); i++){
                //images[i] = bookList.get(i).getCouverture();
            }
            Gallery gallery = (Gallery) findViewById(R.id.gallery);
            gallery.setSpacing(1);
            final BookGalleryAdapter galleryImageAdapter= new BookGalleryAdapter(this, images);
            gallery.setAdapter(galleryImageAdapter);
        }

        // Set up buttons
        this.buildButtonsAction();
        // Set up the search
        this.buildSearchView();
    }

    protected void buildButtonsAction(){
        this.addButton = (FloatingActionButton) findViewById(R.id.addBookButton);
        this.addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddBookActivity.class);
                //intent.putExtra("book", new Gson().toJson(book));
                startActivity(intent);
            }
        });

    }

    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {
        // Print details for each book when clicked on
        Book book = bookList.get(mPosition);
        Log.v("BOOK title", book.getTitle());
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
        Intent intent = new Intent(this, BookDetailActivity.class);
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

    public void buildSearchView() {
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bookList = MySQLiteHelper.searchBooksByTitle(query);
                Resources res = getResources();
                adapter = new BookListAdapter(customListView, bookList, res);
                list.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

}
