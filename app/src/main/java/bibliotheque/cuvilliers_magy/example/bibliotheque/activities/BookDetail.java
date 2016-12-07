package bibliotheque.cuvilliers_magy.example.bibliotheque.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;
import bibliotheque.cuvilliers_magy.example.bibliotheque.database.MySQLiteHelper;
import bibliotheque.cuvilliers_magy.example.bibliotheque.fragment.BookDetailFragment;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;

public class BookDetail extends AppCompatActivity {

    private Button deleteButton;
    private String currentBookID;
    private FragmentManager fm = this.getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Intent intent = getIntent();
        String bookJSON = intent.getStringExtra("book");

        FragmentTransaction fTransaction = this.fm.beginTransaction();

        BookDetailFragment bookFragment = new BookDetailFragment();
        bookFragment.setBook(new Gson().fromJson(bookJSON, Book.class));
        fTransaction.replace(R.id.bookFragment, bookFragment);
        fTransaction.commit();

        // Setting button to use onClick method on them
        this.currentBookID = bookFragment.getBookID();
        final FloatingActionButton button = (FloatingActionButton) findViewById(R.id.deleteButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // First delete book in database
                MySQLiteHelper.deleteBookByID(currentBookID);
                // Then go back to book list
                startActivity(new Intent(BookDetail.this, CustomBookListView.class));
            }
        });
    }

    private View.OnClickListener deleteButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.v("DELETE", "Book deleted : " + currentBookID);
            // First delete book in database
            MySQLiteHelper.deleteBookByID(currentBookID);
            // Then delete the fragment
            Fragment fragment = fm.findFragmentByTag("bookDetailFragment");
            fm.beginTransaction().remove(fragment).commit();
        }

    };

}
