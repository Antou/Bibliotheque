package bibliotheque.cuvilliers_magy.example.bibliotheque.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;

public class BookDetailFragment extends Fragment {

    private Book book;

    public BookDetailFragment() {
        // Required empty public constructor
    }

    public void setBook(Book book){
        this.book = book;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_liste_livres, container, false);
        TextView textView = (TextView) view.findViewById(R.id.titleFragment);
        textView.setText(this.book.getTitle());
        return view;
    }
}
