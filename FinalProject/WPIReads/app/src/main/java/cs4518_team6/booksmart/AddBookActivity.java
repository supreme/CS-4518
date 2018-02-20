package cs4518_team6.booksmart;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddBookActivity extends AppCompatActivity {

    private Button mAddBook;
    private Button mCancel;
    private ImageButton mGallery;
    private ImageButton mCamera;
    private CheckBox mSale;
    private EditText mSalePrice;
    private CheckBox mTrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        mGallery = findViewById(R.id.gallery);
        mGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: View gallery
            }
        });

        mCamera = findViewById(R.id.camera);
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Take picture and add to database
            }
        });

        mSale = findViewById(R.id.check_box_sell);
        mSalePrice = findViewById(R.id.sale_price);
        mTrade = findViewById(R.id.check_box_trade);
        //TODO: If either of these are checked, add to database so we can populate search

        mAddBook = findViewById(R.id.add_book);
        mAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add new book to backend
                Intent intent = new Intent(AddBookActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        mCancel = findViewById(R.id.cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBookActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
