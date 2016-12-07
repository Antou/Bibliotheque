package bibliotheque.cuvilliers_magy.example.bibliotheque.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by cuvilliers on 29/09/16.
 */

public class Book {
    protected String title;
    protected String author;
    protected String isbn;
    protected String serie;
    protected String genre;
    protected String editeur;
    protected String annee;
    protected int couverture;
    protected ArrayList<String> illustrations;
    protected String resume;
    protected ArrayList<String> annotations;

    public Book(String author, String title, String isbn, String serie, String genre, String editeur, String annee, int couverture, ArrayList<String> illustrations,
        String resume, ArrayList<String> annotations ) {
        super();
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.serie = serie;
        this.genre = genre;
        this.editeur = editeur;
        this.annee = annee;
        this.couverture = couverture;
        this.illustrations = illustrations;
        this.resume = resume;
        this.annotations = annotations;
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getSerie() {
        return serie;
    }
    public void setSerie(String serie) {
        this.serie = serie;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getEditeur() {
        return editeur;
    }
    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }
    public String getAnnee() {
        return annee;
    }
    public void setAnnee(String annee) {
        this.annee = annee;
    }
    public int getCouverture() {
        return this.couverture ;
    }
    public void setCouverture(int couverture) {
        this.couverture = couverture;
    }
    public ArrayList<String> getIllustrations() {
        return illustrations;
    }
    public void setIllustrations(ArrayList<String> illustrations) {
        this.illustrations = illustrations;
    }
    public String getResume() {
        return resume;
    }
    public void setResume(String resume) {
        this.resume = resume;
    }
    public ArrayList<String> getAnnotations() {
        return annotations;
    }
    public void setAnnotations(ArrayList<String> annotations) {
        this.annotations = annotations;
    }



}