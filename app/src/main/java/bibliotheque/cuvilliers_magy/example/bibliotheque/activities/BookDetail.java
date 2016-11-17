package bibliotheque.cuvilliers_magy.example.bibliotheque.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;

public class BookDetail extends AppCompatActivity {

    protected Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_book_detail);
        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");

        TextView bookTitle = (TextView) findViewById(R.id.titleFragment);
        bookTitle.setText(title);
    }

}
