package cs4518_team6.booksmart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class ProfileOwnedBookAdapter extends ArrayAdapter<String> {
        private final Context context;
        private ArrayList<String> values;

        public ProfileOwnedBookAdapter(Context context, ArrayList<String> values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.owned_book_profile, parent, false);
            TextView title = view.findViewById(R.id.title);
            title.setText(values.get(position));

            final String titleString = title.getText().toString();

            ImageButton delete = view.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProfileActivity.removeOwnedBook(titleString);
                }
            });

            ImageButton edit = view.findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), AddBookActivity.class);
                    intent.putExtra("EXISTING_BOOK", titleString);
                    context.startActivity(intent);
                }
            });

            return view;
        }
}

