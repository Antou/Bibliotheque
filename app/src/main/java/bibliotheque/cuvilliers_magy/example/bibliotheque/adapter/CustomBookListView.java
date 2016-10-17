package bibliotheque.cuvilliers_magy.example.bibliotheque.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;
import bibliotheque.cuvilliers_magy.example.bibliotheque.database.MySQLiteHelper;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;

/**
 * Created by Alessandro on 17/10/2016.
 */

public class CustomBookListView extends Activity {

    ListView list;
    BookAdapter adapter;
    public CustomBookListView customListView = null;
    public ArrayList<Book> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_livres);

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
        Book book = bookList.get(mPosition);
        Toast.makeText(customListView,
                ""+book.getTitle(), Toast.LENGTH_LONG).show();
    }
}
