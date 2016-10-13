package bibliotheque.cuvilliers_magy.example.bibliotheque;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

import bibliotheque.cuvilliers_magy.example.bibliotheque.database.MySQLiteHelper;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.DetailFragment;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.MyListFragment;

public class BookList extends AppCompatActivity implements MyListFragment.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_livres);

        MySQLiteHelper dbhelper = new MySQLiteHelper(this);

        ListView bookList = (ListView) findViewById(R.id.booklist);
        List<Map<String, String>> listOfBook;

        listOfBook = dbhelper.getAllBooks();

       /* SimpleAdapter listAdapter = new SimpleAdapter(this.getBaseContext(), listOfBook, R.layout.book_detail,
                new String[] {"img", "author", "title", "isbn"},
                new int[] {R.id.img, R.id.author, R.id.title, R.id.isbn});

        bookList.setAdapter(listAdapter); */
    }

    @Override
    public void onRssItemSelected(String link) {
        DetailFragment fragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.detailFragment);
        fragment.setText(link);
    }
}
