package cs4518_team6.booksmart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class SearchBookAdapter extends ArrayAdapter<String> {
        private final Context context;
        private ArrayList<String> values;

        public SearchBookAdapter(Context context, ArrayList<String> values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.search_book, parent, false);
            TextView title = view.findViewById(R.id.title);
            title.setText(values.get(position));

            ImageButton trade = view.findViewById(R.id.trade);
            // TODO: Check if book is set to accept trades
            // if (user is not accepting trades for book)
            // trade.setVisibility(View.GONE);

            // TODO: Let user see what books are available for trade if book is flagged for trading
            /*trade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });*/

            ImageView sale = view.findViewById(R.id.for_sale);
            TextView salePrice = view.findViewById(R.id.sale_price);
            // TODO: Check if book is set to sell
            // if (user is not selling book)
            // sell.setVisibility(View.GONE);
            // salePrice.setVisibility(View.GONE);
            // else salePrice.setText(price from database);

            ImageButton message = view.findViewById(R.id.message);
            //TODO: Implement SMS
            /*message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    context.startActivity(intent);
                }
            });*/

            return view;
        }
}

