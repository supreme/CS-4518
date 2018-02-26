package cs4518_team6.booksmart;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText mSearchField;
    private ListView mBuyList;
    private static ArrayList<String> buyArray;
    private static SearchBookAdapter buyAdapter;
    private ListView mSellList;
    private static ArrayList<String> sellArray;
    private static SearchBookAdapter sellAdapter;
    private ImageButton searchButton;


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

        mBuyList = findViewById(R.id.buy_list);
        buyArray = new ArrayList<String>();
        buyAdapter = new SearchBookAdapter(SearchActivity.this, buyArray, "BUY");
        mBuyList.setAdapter(buyAdapter);


        mSellList = findViewById(R.id.sell_list);
        sellArray = new ArrayList<String>();
        sellAdapter = new SearchBookAdapter(SearchActivity.this, sellArray, "SELL");
        mSellList.setAdapter(sellAdapter);


        searchButton = findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyArray.add("Fundamentals of Calculus");
                buyArray.add("Fundamentals of Calculus");
                sellArray.add("Fundamentals of Calculus");
                sellArray.add("Fundamentals of Calculus");
                populateLists();
            }
        });

        populateLists();
    }

    public void populateLists(){
        //TODO: Add book titles from database to buyArray and sellArray
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
