package bibliotheque.cuvilliers_magy.example.bibliotheque.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.w3c.dom.Text;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;
import bibliotheque.cuvilliers_magy.example.bibliotheque.scan.BarcodeCaptureActivity;

import static bibliotheque.cuvilliers_magy.example.bibliotheque.database.MySQLiteHelper.insertValues;

public class AddBookActivity extends AppCompatActivity {

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

    protected FloatingActionButton scanButton;

    static int RC_BARCODE_CAPTURE = 2;

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

        // Set up buttons
        this.buildButtonsAction();
    }

    protected void launchRequestToFindBook(String isbn){
        
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Log.d("Scan", "Barcode read: " + barcode.displayValue);
                } else {
                    Log.d("Scan", "No barcode captured, intent data is null");
                }
            } else {

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void buildButtonsAction(){
        this.scanButton = (FloatingActionButton) findViewById(R.id.scanBookButton);
        this.scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BarcodeCaptureActivity.class);
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                intent.putExtra(BarcodeCaptureActivity.UseFlash, false);
                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        });
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
