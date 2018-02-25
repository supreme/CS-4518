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
        private boolean buy = false;
        private boolean trade;

        public SearchBookAdapter(Context context, ArrayList<String> values, String typeFlag, boolean trade) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
            if (typeFlag.equals("BUY"))
                this.buy = true;
            this.trade = trade;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.search_book, parent, false);
            TextView title = view.findViewById(R.id.title);
            title.setText(values.get(position));

            ImageView sale = view.findViewById(R.id.for_sale);
            TextView salePrice = view.findViewById(R.id.price);
            // Only display price if user wants to buy, not sell
            if (buy) {
                // TODO: Check if book is set to sell
                // if (user is selling book)
                // salePrice.setText(price from database);

                // Can only display book report for books that exist
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), BookReportActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //TODO: intent.putExtra("BOOK_ID"", [pull book ID from db])
                        // Either some book ID or putExtra title, isbn, authors, condition, and images
                        context.startActivity(intent);
                    }
                });
            }
            else {
                sale.setVisibility(View.GONE);
                salePrice.setVisibility(View.GONE);
            }

            ImageButton message = view.findViewById(R.id.message);
            //TODO: Implement SMS
            /*message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });*/

            ImageButton tradeButton = view.findViewById(R.id.trade);

            // If user is only trading, we don't need to set anything else
            if (trade){
                sale.setVisibility(View.GONE);
                salePrice.setVisibility(View.GONE);
                tradeButton.setVisibility(View.GONE);
                return view;
            }


            // TODO: Check if book is set to accept trades
            // if (user is not accepting trades for book)
            // tradeButton.setVisibility(View.GONE);
            tradeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), TradeListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //TODO: intent.putExtra("USER_ID"", [pull user ID from db]) so we can get their list of owned books
                    if (buy)
                        intent.putExtra("TYPE_FLAG", "BUY");
                    else
                        intent.putExtra("TYPE_FLAG", "SELL");

                    context.startActivity(intent);
                }
            });

            return view;
        }
}

