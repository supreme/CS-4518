package cs4518_team6.booksmart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cs4518_team6.booksmart.model.Book;

public class BookReportActivity extends AppCompatActivity {

    private ImageButton mGallery;
    private TextView mIsbn;
    private TextView mAuthors;
    private TextView mTitle;
    private TextView mCondition;
    private int numPics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        numPics = 0;

        mGallery = findViewById(R.id.gallery);
        mGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookReportActivity.this, BookGalleryActivity.class);
                startActivity(intent);
            }
        });

        mIsbn = findViewById(R.id.isbn_value);
        mAuthors = findViewById(R.id.authors_value);
        mTitle = findViewById(R.id.title_value);
        mCondition = findViewById(R.id.condition_value);

        String isbn = getIntent().getStringExtra("BOOK_ID");
        String condition = getIntent().getStringExtra("CONDITION");
        Book book = Book.get(isbn);
        mIsbn.setText(isbn);
        mAuthors.setText(TextUtils.join(", ", book.getAuthors()));
        mTitle.setText(book.getTitle());
        mCondition.setText(condition);
        mGallery.setImageBitmap(getBitmapFromURL(book.getThumbnail()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
