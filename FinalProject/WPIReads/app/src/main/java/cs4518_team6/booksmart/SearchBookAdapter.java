package cs4518_team6.booksmart;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs4518_team6.booksmart.model.Listing;


public class SearchBookAdapter extends ArrayAdapter<String> {
        private final Context context;
        private ArrayList<String> values;
        private List<Listing> listings;
        private boolean buy = false;
        private boolean trade;
        private String tradeBook;

        public SearchBookAdapter(Context context, ArrayList<String> values, String typeFlag, List<Listing> listings) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
            this.listings = listings;
            if (typeFlag.equals("BUY"))
                this.buy = true;
            this.trade = false;
        }

        // This constructor is for trading
        public SearchBookAdapter(Context context, ArrayList<String> values, String typeFlag, String tradeBook) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
            if (typeFlag.equals("BUY"))
                this.buy = true;
            this.trade = true;
            this.tradeBook = tradeBook;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.search_book, parent, false);
            final TextView title = view.findViewById(R.id.title);
            final Listing listing = listings.get(position);
            title.setText(values.get(position));

            ImageView sale = view.findViewById(R.id.for_sale);
            TextView salePrice = view.findViewById(R.id.price);
            // Only display price if user wants to buy, not sell
            if (buy) {
                // TODO: Check if book is set to sell
                if (listing.getPrice() != null && listing.forSale()) {
                    int price = listing.getPrice().intValue();
                    salePrice.setText(String.valueOf(price));
                }

                // Can only display book report for books that exist
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), BookReportActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("BOOK_ID", listing.getIsbn());
                        intent.putExtra("CONDITION", listing.getCondition());
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
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phoneNumber = "9999999999";
                    String textMessage;
                    if (buy) {
                        if (trade)
                            textMessage = "Hello, I would like to trade my book " + title.getText() + " for your book " + tradeBook + ".\n\nSent via Book Smart";
                        else
                        textMessage = "Hello, I would like to buy your book " + title.getText() + ".\n\nSent via Book Smart";
                    }
                    else if (trade)
                        textMessage = "Hello, I would like to trade you my book " + tradeBook + " for your book " + title.getText() + ".\n\nSent via Book Smart";
                    else
                        textMessage = "Hello, I would like to sell you my book " + title.getText() + ".\n\nSent via Book Smart";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("sms_body", textMessage);
                    context.startActivity(intent);
                }
            });

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
                    intent.putExtra("TRADE_BOOK", title.getText());
                    //TODO: intent.putExtra("USER_ID"", [pull user ID from db]) so we can get their list of owned/wanted books
                    if (buy) {
                        intent.putExtra("TYPE_FLAG", "BUY");
                        // User is buying book, so pull up seller's wanted books
                    }
                    else {
                        intent.putExtra("TYPE_FLAG", "SELL");
                        // User is selling book, so pull up buyer's owned books
                    }

                    context.startActivity(intent);
                }
            });

            return view;
        }
}

