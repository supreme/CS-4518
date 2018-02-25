package cs4518_team6.booksmart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton mAddWantedBook;
    private ListView mWantedBookList;
    private static ArrayList<String> wantedArray;
    private static ProfileWantedBookAdapter wantedAdapter;
    private ImageButton mAddOwnedBook;
    private ListView mOwnedBookList;
    private static ArrayList<String> ownedArray;
    private static ArrayAdapter<String> ownedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: Send user back to login screen if they're not logged in.
        // This is for if the user logs out then navigates back to this screen using the back button
        /*if (not logged in){
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
        }*/

        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Profile");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView navName = header.findViewById(R.id.nav_name);
        navName.setText("New Name"); //TODO: Get user's name from db
        TextView navEmail = header.findViewById(R.id.nav_email_address);
        navEmail.setText("New Email"); //TODO: Get user's email from db

        mAddWantedBook = findViewById(R.id.add_wanted_book);
        mAddWantedBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIsbnFromUser();
            }
        });

        mWantedBookList = findViewById(R.id.wanted_book_list);
        wantedArray = new ArrayList<String>();
        wantedAdapter = new ProfileWantedBookAdapter(ProfileActivity.this, wantedArray);
        mWantedBookList.setAdapter(wantedAdapter);

        addWantedBook("Example 1");
        addWantedBook("Example 2");

        mAddOwnedBook = findViewById(R.id.add_owned_book);
        mAddOwnedBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });

        mOwnedBookList = findViewById(R.id.owned_book_list);
        ownedArray = new ArrayList<String>();
        ownedAdapter = new ProfileOwnedBookAdapter(ProfileActivity.this, ownedArray);
        mOwnedBookList.setAdapter(ownedAdapter);

        addOwnedBook("Example 3");
        addOwnedBook("Example 4");

        populateLists();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(ProfileActivity.this, SearchActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_add_book) {
            Intent intent = new Intent(ProfileActivity.this, AddBookActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
    }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void populateLists(){
        //TODO: Add book titles from database to wantedArray and ownedArray
        wantedAdapter.notifyDataSetChanged();
        ownedAdapter.notifyDataSetChanged();
    }

    public void addWantedBook(String title){
        if (wantedArray.contains(title)){
            Toast.makeText(getApplicationContext(), "Book is already in your wanted list", Toast.LENGTH_SHORT);
        }
        else {
            wantedArray.add(title);
            wantedAdapter.notifyDataSetChanged();
        }
    }

    public static void removeWantedBook(String title){
        //TODO: Remove from backend database
        wantedArray.remove(title);
        wantedAdapter.notifyDataSetChanged();
    }

    public void addOwnedBook(String title){
        if (ownedArray.contains(title)){
            Toast.makeText(getApplicationContext(), "Book is already in your wanted list", Toast.LENGTH_SHORT);
        }
        else {
            //TODO: Add to backend database
            ownedArray.add(title);
            ownedAdapter.notifyDataSetChanged();
        }
    }

    public static void removeOwnedBook(String title){
        //TODO: Remove from backend database
        ownedArray.remove(title);
        ownedAdapter.notifyDataSetChanged();
    }

    private void getIsbnFromUser(){
        final EditText textIsbn = new EditText(this);

        new AlertDialog.Builder(this)
                .setTitle("New Wanted Book")
                .setMessage("Enter ISBN")
                .setView(textIsbn)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String isbn = textIsbn.getText().toString();
                        //TODO: Use google books API to get title from isbn
                        String title = isbn;
                        addWantedBook(title);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }
}
