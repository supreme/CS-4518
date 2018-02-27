package cs4518_team6.booksmart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.util.ArrayList;
import java.util.List;

import cs4518_team6.booksmart.model.Book;
import cs4518_team6.booksmart.model.Listing;
import cs4518_team6.booksmart.model.User;

public class AddBookActivity extends AppCompatActivity {


    private Button mAddBook;
    private Button mCancel;
    private ImageButton mGallery;
    private ImageButton mCamera;
    private CheckBox mSale;
    private EditText mSalePrice;
    private CheckBox mTrade;
    private AutoCompleteTextView mIsbn;
    private AutoCompleteTextView mAuthors;
    private AutoCompleteTextView mTitle;
    private Spinner mCondition;
    private int numPics;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        numPics = 0;

        mGallery = findViewById(R.id.gallery);
        mGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBookActivity.this, BookGalleryActivity.class);
                startActivity(intent);
            }
        });

        mCamera = findViewById(R.id.camera);
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePicture.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
            }
        });

        mSale = findViewById(R.id.check_box_sell);
        mSalePrice = findViewById(R.id.sale_price);
        mTrade = findViewById(R.id.check_box_trade);


        mCancel = findViewById(R.id.cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBookActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        mIsbn = findViewById(R.id.isbn_label);
        mAuthors = findViewById(R.id.authors_label);
        mTitle = findViewById(R.id.title_label);
        mCondition = findViewById(R.id.condition);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.condition_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCondition.setAdapter(adapter);

        mAddBook = findViewById(R.id.add_book);
        mAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ProfileActivity.ownedMap.keySet().contains(mTitle.getText().toString())){
                    Toast.makeText(AddBookActivity.this, "Book is already in your owned list", Toast.LENGTH_SHORT).show();
                }
                else {
                    Book book = Book.get(mIsbn.getText().toString());
                    ProfileActivity.addOwnedBook(book.getTitle(), book.getIsbn());

                    // Add to publicly available listings
                    if (mSale.isChecked() || mTrade.isChecked()) {
                        addListing(book);
                    }
                    Intent intent = new Intent(AddBookActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Add this book as a new public listing if the user checks either
     * the sale or trade checkbox.
     * @book The book to list.
     */
    private void addListing(Book book) {
        List<Listing.ListingType> listingTypes = new ArrayList<>();
        User user = CurrentUser.getInstance().getUser();
        Double price = mSalePrice.getText().toString().equals("")
                ? null : Double.valueOf(mSalePrice.getText().toString());
        if (mSale.isChecked()) {
            listingTypes.add(Listing.ListingType.FOR_SALE);
            listingTypes.add(Listing.ListingType.FOR_LOAN);
        }

        if (mTrade.isChecked()) {
            listingTypes.add(Listing.ListingType.FOR_TRADE);
        }


        Listing.add(user.getUsername(), book.getIsbn(), mCondition.getSelectedItem().toString(), price, listingTypes);
        List<Listing> listings = Listing.getAll();
        Log.d("steve", "Listings: " + listings.size());
        for (Listing listing : listings) {
            Log.d("steve", listing.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            if(numPics == 0)
                mGallery.setImageBitmap(image);

            BarcodeDetector detector = new BarcodeDetector.Builder(getApplicationContext())
                                                            .setBarcodeFormats(Barcode.EAN_8 | Barcode.EAN_13 | Barcode.ISBN)
                                                            .build();

            if(!detector.isOperational()) {
                Toast.makeText(getApplicationContext(), "Barcode detector is not operational", Toast.LENGTH_SHORT).show();
            }
            else {
                Frame frame = new Frame.Builder().setBitmap(image).build();
                SparseArray<Barcode> barcodes = detector.detect(frame);

                if(barcodes.size() == 1){
                    Barcode code = barcodes.valueAt(0);
                    String isbn = code.rawValue;

                    if(mIsbn.getText().toString().isEmpty())
                        mIsbn.setText(isbn);
                    else if(!mIsbn.getText().toString().equals(isbn)) {
                        Toast.makeText(getApplicationContext(),"A book can only have one ISBN code!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                numPics++;
                //TODO: Add picture to database
            }
        }
    }
}
