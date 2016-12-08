package bibliotheque.cuvilliers_magy.example.bibliotheque.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import java.util.ArrayList;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;


public class BookGalleryAdapter extends BaseAdapter {

    private Context mContext;
    private int[] images;

    public BookGalleryAdapter(Context context, int[] images) {
        mContext = context;
        this.images = images;
    }

    public int getCount() {
        return images.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        ImageView i = new ImageView(mContext);

        i.setImageResource(images[index]);
        return i;
    }

}