package bibliotheque.cuvilliers_magy.example.bibliotheque.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import static android.R.attr.author;

/**
 * Created by cuvilliers on 29/09/16.
 */

public class Book {

    protected int ID;
    protected String title;
    protected String categorie;
    protected String publisher;
    protected String description;
    protected String image;
    protected ArrayList<String> authors;

    public int getID(){
        return this.ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Book(int ID, String title, ArrayList<String> authors, String description, String categorie, String publisher, String image) {
        super();
        this.ID = ID;
        this.authors = authors;
        this.title = title;
        this.description = description;
        this.categorie = categorie;
        this.publisher = publisher;
        this.image = image;
    }

    public ArrayList<String> getAllAuthors(){
        return this.authors;
    }

}