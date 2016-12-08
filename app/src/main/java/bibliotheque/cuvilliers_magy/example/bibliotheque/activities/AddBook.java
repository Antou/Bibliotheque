package bibliotheque.cuvilliers_magy.example.bibliotheque.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;

import static bibliotheque.cuvilliers_magy.example.bibliotheque.database.MySQLiteHelper.insertValues;

public class AddBook extends AppCompatActivity {

    Button valider;
    Button annuler;
    EditText titre;
    EditText auteur;
    EditText ISBN;
    EditText serie;
    EditText genre;
    EditText editeur;
    EditText annee;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

        valider = (Button) findViewById(R.id.valider);
        valider.setOnClickListener(myhandler1);

        annuler = (Button) findViewById(R.id.annuler);
        annuler.setOnClickListener(myhandler2);

        titre =  (EditText) findViewById(R.id.titre);
        auteur =  (EditText) findViewById(R.id.auteur);
        ISBN =  (EditText) findViewById(R.id.ISBN);
        serie =  (EditText) findViewById(R.id.serie);
        genre =  (EditText) findViewById(R.id.genre);
        editeur =  (EditText) findViewById(R.id.editeur);
        annee =  (EditText) findViewById(R.id.annee);


    }

        View.OnClickListener myhandler1 = new View.OnClickListener() {
            public void onClick(View v) {
                insertValues(3,titre.getText().toString(),auteur.getText().toString(),genre.getText().toString(),serie.getText().toString(),editeur.getText().toString());
                Intent intent = new Intent(ctx, BookListViewActivity.class);
                startActivity(intent);
            }
        };

        View.OnClickListener myhandler2 = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ctx, BookListViewActivity.class);
                startActivity(intent);
            }
         };

}
