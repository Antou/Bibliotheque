package bibliotheque.cuvilliers_magy.example.bibliotheque.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return Integer.toString(this.book.getID());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // On gonfle le xml book detail
        View view = inflater.inflate(R.layout.book_detail, container, false);

        TextView title = (TextView) view.findViewById(R.id.titleFragment);
        title.setText(this.book.getTitle());

        TextView authorView = (TextView) view.findViewById(R.id.authorFragment);
        //authorView.setText("Auteur : " + this.book.getAuthor());

        TextView resumeView = (TextView) view.findViewById(R.id.resumeFragment);
        resumeView.setText("Résumé : " + this.book.getDescription());

        TextView editeurView = (TextView) view.findViewById(R.id.editeurFragment);
        editeurView.setText("Editeur : " + this.book.getPublisher());

        ImageView imageView = (ImageView) view.findViewById(R.id.imageFragment);
        imageView.setImageResource(R.drawable.titeuf);

        return view;
    }

}
