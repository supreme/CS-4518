package cs4518_team6.wpireads;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddBookActivity extends AppCompatActivity {

    private Button mAddBook;
    private Button mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        Button mAddBook = findViewById(R.id.add_book);
        mAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add new book to list in profile
                Intent intent = new Intent(AddBookActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        Button mCancel = findViewById(R.id.cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBookActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
