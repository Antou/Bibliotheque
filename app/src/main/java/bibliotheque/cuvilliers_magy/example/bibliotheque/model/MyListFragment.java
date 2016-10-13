package bibliotheque.cuvilliers_magy.example.bibliotheque.model;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bibliotheque.cuvilliers_magy.example.bibliotheque.R;

public class MyListFragment extends Fragment {

    private OnItemSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview,
                container, false);
        Button button = (Button) view.findViewById(R.id.updateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetail("fake");
            }
        });
        return view;
    }

    public interface OnItemSelectedListener {
        void onRssItemSelected(String link);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    // triggers update of the details fragment
    public void updateDetail(String uri) {
        // create fake data
        String newTime = String.valueOf(System.currentTimeMillis());
        // send data to activity
        listener.onRssItemSelected(newTime);
    }
}