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
        TextView titleView = (TextView) view.findViewById(R.id.titleFragment);
        titleView.setText("Titre : " + this.book.getTitle());
        TextView authorView = (TextView) view.findViewById(R.id.authorFragment);
        authorView.setText("Auteur : " + this.book.getAuthor());
        TextView isbnView = (TextView) view.findViewById(R.id.isbnFragment);
        isbnView.setText("ISBN : " + this.book.getIsbn());
        TextView serieView = (TextView) view.findViewById(R.id.serieFragment);
        serieView.setText("Série : " + this.book.getSerie());
        TextView genreView = (TextView) view.findViewById(R.id.genreFragment);
        genreView.setText("Genre : " + this.book.getGenre());
        TextView resumeView = (TextView) view.findViewById(R.id.resumeFragment);
        resumeView.setText("Résumé : " + this.book.getResume());
        TextView editeurView = (TextView) view.findViewById(R.id.editeurFragment);
        editeurView.setText("Editeur : " + this.book.getEditeur());
        return view;
    }
}
