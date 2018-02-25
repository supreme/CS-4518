package cs4518_team6.booksmart;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button buton = findViewById(R.id.register_button);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });
    }

    private void attemptRegistration(){
        boolean success = false;
        //TODO: Registration logic - check backend if user already exists, valid username, etc
        if (success){
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
        else {
            //TODO: Add why registration failed
            Toast.makeText(this, "Registration failed!", Toast.LENGTH_LONG).show();
        }
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
