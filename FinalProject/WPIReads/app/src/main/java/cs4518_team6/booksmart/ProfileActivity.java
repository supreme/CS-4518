package cs4518_team6.booksmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {

    private Button mAddWantedBook;
    private Button mAddOwnedBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button mAddWantedBook = findViewById(R.id.add_wanted_book);
        mAddWantedBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Open dialogue to have user enter information for a book they want
            }
        });

        Button mAddOwnedBook = findViewById(R.id.add_owned_book);
        mAddOwnedBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });
    }
}
