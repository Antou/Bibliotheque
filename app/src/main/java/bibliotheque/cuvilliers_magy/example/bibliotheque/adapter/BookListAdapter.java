package bibliotheque.cuvilliers_magy.example.bibliotheque.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;
import bibliotheque.cuvilliers_magy.example.bibliotheque.activities.BookListViewActivity;
import bibliotheque.cuvilliers_magy.example.bibliotheque.model.Book;

import static bibliotheque.cuvilliers_magy.example.bibliotheque.activities.AddBookActivity.resources;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class BookListAdapter extends BaseAdapter implements View.OnClickListener {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    Book tempValues = null;
    private Drawable currentImage;

    /*************  CustomAdapter Constructor *****************/
    public BookListAdapter(Activity a, ArrayList d, Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        data = d;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        public TextView title;
        public ImageView image;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
                vi = inflater.inflate(R.layout.item_list, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
                holder.title = (TextView) vi.findViewById(R.id.title);
                holder.image = (ImageView)vi.findViewById(R.id.bookImage);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0) {
            // NO DATA TO DISPLAY
        }
        else {
            /***** Get each Model object from Arraylist ********/
            tempValues = null;
            tempValues = (Book) data.get(position);

            /************  Set Model values in Holder elements ***********/
            holder.title.setText(tempValues.getTitle());
            holder.image.setImageResource(R.drawable.titeuf);
            //holder.image.setImageDrawable(this.getDrawableFromURL(tempValues.getImage()));

            /******** Set Item Click Listener for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
    }
    

    @Override
    public void onClick(View v) {

    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            BookListViewActivity sct = (BookListViewActivity) activity;
            sct.onItemClick(mPosition);
        }
    }
}