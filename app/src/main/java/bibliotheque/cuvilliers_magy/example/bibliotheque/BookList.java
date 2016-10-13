package bibliotheque.cuvilliers_magy.example.bibliotheque;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import bibliotheque.cuvilliers_magy.example.bibliotheque.database.MySQLiteHelper;

public class BookList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_livres);

        MySQLiteHelper dbhelper = new MySQLiteHelper(this);

        ListView bookList = (ListView) findViewById(R.id.booklist);
        List<Map<String, String>> listOfBook;

        listOfBook = dbhelper.getAllBooks();

        final SimpleAdapter listAdapter = new SimpleAdapter(this.getBaseContext(), listOfBook, R.layout.book_detail,
                new String[] {"img", "author", "title", "isbn"},
                new int[] {R.id.img, R.id.author, R.id.title, R.id.isbn});

        bookList.setAdapter(listAdapter);


        // Adding event on Book (récupére le titre du livre)
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Object item = listAdapter.getItem(position);
                String title = ((Map<String, String>) item).get("author");
                Toast.makeText(getApplicationContext(),
                        "Title : " + title, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }




}
