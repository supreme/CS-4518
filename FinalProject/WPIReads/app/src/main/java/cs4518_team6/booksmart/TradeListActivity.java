package cs4518_team6.booksmart;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class TradeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().getStringExtra("TYPE_FLAG").equals("BUY"))
            setTitle("Seller's Wanted Books");
        if (getIntent().getStringExtra("TYPE_FLAG").equals("SELL"))
            setTitle("Buyer's Owned Books");

        ListView tradeList = findViewById(R.id.trade_list);
        ArrayList<String> bookArray = new ArrayList<String>();
        SearchBookAdapter adapter = new SearchBookAdapter(TradeListActivity.this, bookArray,
                getIntent().getStringExtra("TYPE_FLAG"), getIntent().getStringExtra("TRADE_BOOK"));
        tradeList.setAdapter(adapter);
        bookArray.add("Example 1");

        adapter.notifyDataSetChanged();
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
