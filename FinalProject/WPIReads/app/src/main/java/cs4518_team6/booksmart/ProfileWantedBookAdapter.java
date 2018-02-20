package cs4518_team6.booksmart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class ProfileWantedBookAdapter extends ArrayAdapter<String> {
        private final Context context;
        private ArrayList<String> values;

        public ProfileWantedBookAdapter(Context context, ArrayList<String> values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.wanted_book_profile, parent, false);
            TextView title = view.findViewById(R.id.title);
            title.setText(values.get(position));

            final String titleString = title.getText().toString();

            ImageButton delete = view.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProfileActivity.removeWantedBook(titleString);
                }
            });

            return view;
        }
}

