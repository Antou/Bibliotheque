package bibliotheque.cuvilliers_magy.example.bibliotheque.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;
import bibliotheque.cuvilliers_magy.example.bibliotheque.database.MySQLiteHelper;
import bibliotheque.cuvilliers_magy.example.bibliotheque.fragment.BookDetailFragment;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;

public class BookDetailActivity extends AppCompatActivity {

    private String currentBookID;
    private FragmentManager fm = this.getFragmentManager();
    private boolean addMode;
    private FloatingActionButton deleteButton;
    private FloatingActionButton confirmAddButton;
    private static Book currentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Intent intent = getIntent();
        this.addMode = intent.getBooleanExtra("addMode", false);
        String bookJSON = intent.getStringExtra("book");
        this.currentBook = new Gson().fromJson(bookJSON, Book.class);

        this.deleteButton = (FloatingActionButton) findViewById(R.id.deleteButton);
        this.confirmAddButton = (FloatingActionButton) findViewById(R.id.validateAddBookButton);

        if (this.addMode) {
            this.deleteButton.hide();
        } else {
            this.confirmAddButton.hide();
        }

        FragmentTransaction fTransaction = this.fm.beginTransaction();
        final BookDetailFragment bookFragment = new BookDetailFragment();
        bookFragment.setBook(this.currentBook);
        fTransaction.replace(R.id.bookFragment, bookFragment);
        fTransaction.commit();

        // Setting button to use onClick method on them
        if (!this.addMode){
            confirmAddButton.hide();
            this.currentBookID = bookFragment.getBookID();
            this.deleteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // First delete book in database
                    MySQLiteHelper.deleteBookByID(currentBookID);
                    // Then go back to book list
                    startActivity(new Intent(BookDetailActivity.this, BookListViewActivity.class));
                }
            });
        }
        else {
            this.deleteButton.hide();
            this.confirmAddButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Add book in database
                    int bookID = MySQLiteHelper.addBook(currentBook);
                    ArrayList<String> authors = currentBook.getAllAuthors();
                    if (authors.isEmpty()){

                    }
                    else {
                        for(String author : authors){
                            // Link current book with all authors
                            MySQLiteHelper.addAuthor(author);
                            int authorID = MySQLiteHelper.getAuthorID(author);
                            MySQLiteHelper.linkBookWithAuthor(bookID, authorID);
                        }
                    }
                    // Then go back to book list
                    startActivity(new Intent(BookDetailActivity.this, BookListViewActivity.class));
                }
            });
        }
    }
}

