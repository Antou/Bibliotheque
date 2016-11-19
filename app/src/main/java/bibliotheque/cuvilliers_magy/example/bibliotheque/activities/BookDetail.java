package bibliotheque.cuvilliers_magy.example.bibliotheque.activities;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;
import bibliotheque.cuvilliers_magy.example.bibliotheque.fragment.BookDetailFragment;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;

public class BookDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_book_detail);
        Intent intent = getIntent();
        String bookJSON = intent.getStringExtra("book");

        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction fTransaction = fm.beginTransaction();

        BookDetailFragment bookFragment = new BookDetailFragment();
        bookFragment.setBook(new Gson().fromJson(bookJSON, Book.class));
        fTransaction.replace(R.id.bookFragment, bookFragment);
        fTransaction.commit();
    }
}
