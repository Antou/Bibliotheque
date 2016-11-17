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
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;

public class BookList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_livres);

        MySQLiteHelper dbhelper = new MySQLiteHelper(this);

        ListView bookList = (ListView) findViewById(R.id.booklist);
        List<Book> listOfBook;

        listOfBook = dbhelper.getAllBooks();

    }




}
