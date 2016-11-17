package bibliotheque.cuvilliers_magy.example.bibliotheque.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            return view;
        }

        TextView title = (TextView) view.findViewById(R.id.titleFragment);
        title.setText(this.book.getTitle());
        return view;
    }

}
