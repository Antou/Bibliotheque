package bibliotheque.cuvilliers_magy.example.bibliotheque;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bibliotheque.cuvilliers_magy.example.bibliotheque.database.MySQLiteHelper;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.BookCollection;

public class BookList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_livres);

        MySQLiteHelper dbhelper = new MySQLiteHelper(this);

        ListView bookList = (ListView) findViewById(R.id.booklist);

        BookCollection books = new BookCollection();
        List<Map<String, String>> listOfBook; //= new ArrayList<Map<String, String>>();

        /*books.add(new Book("Jimmy","There is no need to be upset","823"));
        books.add(new Book("***","Mon combat contre l'alcoolisme","476"));
        books.add(new Book("JUL","ça fait 5 ans que j'nique le game","259"));*/

        /*for( Book book : books.getBooks()) {
            Map<String, String> bookMap = new HashMap<String, String>();
            bookMap.put("img", String.valueOf(R.mipmap.ic_launcher)); // use available img
            bookMap.put("author", book.getAuthor());
            bookMap.put("title", book.getTitle());
            bookMap.put("isbn", book.getIsbn());
            listOfBook.add(bookMap);
        }*/

        listOfBook = dbhelper.getAllBooks();

        SimpleAdapter listAdapter = new SimpleAdapter(this.getBaseContext(), listOfBook, R.layout.book_detail,
                new String[] {"img", "author", "title", "isbn"},
                new int[] {R.id.img, R.id.author, R.id.title, R.id.isbn});

        bookList.setAdapter(listAdapter);
    }
}
