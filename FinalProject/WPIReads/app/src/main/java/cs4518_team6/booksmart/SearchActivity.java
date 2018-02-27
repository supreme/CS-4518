package cs4518_team6.booksmart;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

import cs4518_team6.booksmart.model.Book;
import cs4518_team6.booksmart.model.Listing;

public class SearchActivity extends AppCompatActivity {

    // All of the book listings
    private List<Listing> listings;

    private EditText mSearchField;
    private ListView mBuyList;
    private static ArrayList<String> buyArray;
    private static SearchBookAdapter buyAdapter;
    private ListView mSellList;
    private static ArrayList<String> sellArray;
    private static SearchBookAdapter sellAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec buy = tabHost.newTabSpec("Buy Tab");
        buy.setIndicator("Buy");
        buy.setContent(R.id.buy_list);

        TabHost.TabSpec sell = tabHost.newTabSpec("Sell Tab");
        sell.setIndicator("Sell");
        sell.setContent(R.id.sell_list);

        tabHost.addTab(buy);
        tabHost.addTab(sell);

        listings = Listing.getAll();
        mBuyList = findViewById(R.id.buy_list);
        buyArray = new ArrayList<String>();
        buyAdapter = new SearchBookAdapter(SearchActivity.this, buyArray, "BUY", listings);
        mBuyList.setAdapter(buyAdapter);

        mSellList = findViewById(R.id.sell_list);
        sellArray = new ArrayList<String>();
        sellAdapter = new SearchBookAdapter(SearchActivity.this, sellArray, "SELL", listings);
        mSellList.setAdapter(sellAdapter);
        sellArray.add("Example 3");
        sellArray.add("Example 4");

        populateLists();
    }

    public void populateLists(){
        for (Listing listing : listings) {
            Book book = Book.get(listing.getIsbn());
            buyArray.add(book.getTitle());
        }

        buyAdapter.notifyDataSetChanged();
        sellAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
