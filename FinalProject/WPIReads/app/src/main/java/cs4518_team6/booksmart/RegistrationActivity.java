package cs4518_team6.booksmart;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cs4518_team6.booksmart.model.Response;
import cs4518_team6.booksmart.model.User;

public class RegistrationActivity extends AppCompatActivity {

    private TextView firstNameField;
    private TextView lastNameField;
    private TextView emailField;
    private TextView passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstNameField = findViewById(R.id.firstName);
        lastNameField = findViewById(R.id.lastName);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);

        Button button = findViewById(R.id.register_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });
    }

    private void attemptRegistration(){
        Response response = User.register(
                emailField.getText().toString(),
                passwordField.getText().toString(),
                firstNameField.getText().toString(),
                lastNameField.getText().toString());

        // TODO: Check both passwords and see if they match
        if (response.getStatus()){
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
            startActivity(intent);
        } else {
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
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
