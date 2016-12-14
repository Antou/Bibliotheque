package bibliotheque.cuvilliers_magy.example.bibliotheque.fragment;

import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // On gonfle le xml book detail
        View view = inflater.inflate(R.layout.book_detail, container, false);

        TextView title = (TextView) view.findViewById(R.id.titleFragment);
        title.setText(this.book.getTitle());

        TextView authorView = (TextView) view.findViewById(R.id.authorFragment);
        authorView.setText("Auteur : " + this.book.getAllAuthors());

        TextView resumeView = (TextView) view.findViewById(R.id.resumeFragment);
        resumeView.setText("Résumé : " + this.book.getDescription());

        TextView editeurView = (TextView) view.findViewById(R.id.editeurFragment);
        editeurView.setText("Editeur : " + this.book.getPublisher());

        ImageView imageView = (ImageView) view.findViewById(R.id.imageFragment);
        Picasso.with(getContext()).load(this.book.getImage()).resize(50, 50).into(imageView);

        return view;
    }

}
