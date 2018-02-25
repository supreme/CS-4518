package cs4518_team6.booksmart;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BookGalleryActivity extends AppCompatActivity {

    private GridView gallery;
    private ArrayList<Bitmap> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_gallery);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //TODO: Add images from database
        images = new ArrayList<Bitmap>();
        //images.add(PictureUtils.getScaledBitmap([image file], BookGalleryActivity.this));

        gallery = findViewById(R.id.gallery_view);
        gallery.setAdapter(new GalleryAdapter(this, images, metrics.widthPixels));
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
}
