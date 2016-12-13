package bibliotheque.cuvilliers_magy.example.bibliotheque.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;
import bibliotheque.cuvilliers_magy.example.bibliotheque.database.MySQLiteHelper;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;
import bibliotheque.cuvilliers_magy.example.bibliotheque.scan.BarcodeCaptureActivity;

public class AddBookActivity extends AppCompatActivity {

    protected Button valider;
    protected Button annuler;
    protected EditText titre;
    protected EditText auteur;
    protected EditText ISBN;
    protected EditText serie;
    protected EditText genre;
    protected EditText editeur;
    protected EditText annee;

    public static Context context;
    public static Context ctx;
    public static Resources resources;

    protected FloatingActionButton scanButton;

    static int RC_BARCODE_CAPTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        ctx = this;
        setContentView(R.layout.add_book);

        valider = (Button) findViewById(R.id.valider);
        valider.setOnClickListener(myhandler1);

        annuler = (Button) findViewById(R.id.annuler);
        annuler.setOnClickListener(myhandler2);

        titre = (EditText) findViewById(R.id.titre);
        auteur = (EditText) findViewById(R.id.auteur);
        ISBN = (EditText) findViewById(R.id.ISBN);
        serie = (EditText) findViewById(R.id.serie);
        genre = (EditText) findViewById(R.id.genre);
        editeur = (EditText) findViewById(R.id.editeur);
        annee = (EditText) findViewById(R.id.annee);

        resources = getResources();
        // Set up buttons
        this.buildButtonsAction();
    }

    public static void parseGetResponse(String response) throws JSONException {
        ArrayList<String> booksFound = new ArrayList<>();
        JSONObject resultObject = new JSONObject(response);
        JSONArray bookArray = resultObject.getJSONArray("items");
        // Init values for books
        String imageLink = "";
        String title;
        String description = "";
        String publisher = "";
        for (int i = 0; i < bookArray.length(); i++) {
            JSONObject bookObject = bookArray.getJSONObject(i);
            JSONObject volumeObject = bookObject.getJSONObject("volumeInfo");
            title = volumeObject.getString("title");
            try {
                JSONObject imagesObject = volumeObject.getJSONObject("imageLinks");
                imageLink = imagesObject.getString("thumbnail");
            } catch (JSONException exception) {
                // No image available
            }
            try {
                description = volumeObject.getString("description");
            }   catch (JSONException exception){
                // No description available
            }
            try {
                publisher = volumeObject.getString("publisher");
            }   catch (JSONException exception){
                // No publisher available
            }

            String categorie = "";
            ArrayList<String> authors = new ArrayList<>();

            JSONArray authorsArray = volumeObject.getJSONArray("authors");
            JSONArray categoriesArray = volumeObject.getJSONArray("categories");

            for (int j = 0; i < authorsArray.length(); i++) {
                authors.add(authorsArray.getString(j));
            }
            for (int j = 0; i < categoriesArray.length(); i++) {
                categorie += categoriesArray.getString(j);
            }

            Book currentBook = new Book(-1, title, description, categorie, publisher, imageLink);
            // Converting book to JSON
            String bookJSON = new Gson().toJson(currentBook);
            booksFound.add(bookJSON);
            // Converting book list to JSON
            String jsonBooks = new Gson().toJson(booksFound);

            // Go back to main activity with books found
            Intent intent = new Intent(ctx, BookListViewActivity.class);
            intent.putExtra("books", jsonBooks);
            intent.putExtra("addMode", true);
            ctx.startActivity(intent);
        }
    }

    public static void launchRequestToFindBook(String isbn) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ctx);
        //String url = "https://www.googleapis.com/books/v1/volumes?q=" + isbn + "isbn";
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + isbn;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse the response JSON
                        try {
                            parseGetResponse(response);
                        }   catch (JSONException exception){
                            // PARSING ERROR
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast message and redirect to main activity
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Log.d("Scan", "Barcode read: " + barcode.displayValue);
                    // Launching GET Request with barcode
                    launchRequestToFindBook(barcode.rawValue);
                } else {
                    // NO BARCODE CAPTURED
                    Log.d("Scan", "No barcode captured, intent data is null");
                }
            } else {

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void buildButtonsAction() {
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
            //insertValues(3,titre.getText().toString(),auteur.getText().toString(),genre.getText().toString(),serie.getText().toString(),editeur.getText().toString());
            Intent intent = new Intent(v.getContext(), BookListViewActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener myhandler2 = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), BookListViewActivity.class);
            startActivity(intent);
        }
    };

}
