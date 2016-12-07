package bibliotheque.cuvilliers_magy.example.bibliotheque.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
    public String getBookID(){
        return this.book.getIsbn();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.book_detail, container, false);

        TextView title = (TextView) view.findViewById(R.id.titleFragment);
        title.setText(this.book.getTitle());

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

        ImageView imageView = (ImageView) view.findViewById(R.id.imageFragment);
        imageView.setImageResource(R.drawable.titeuf);

        return view;
    }

}
