package cs4518_team6.booksmart.model;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.Arrays;

import cs4518_team6.booksmart.Constants;

/**
 * Represents a book.
 * Created by Stephen Andrews.
 */
public class Book extends Model {

    private static final String ENDPOINT = "/books/";
    private String isbn;
    private String[] authors;
    private String description;
    private String publisher;
    private String publishedDate;
    private String title;
    private String thumbnail;
    private String smallThumbnail;
    private String subtitle;

    public Book(String isbn, String[] authors, String description, String publisher,
                String publishedDate, String title, String thumbnail, String smallThumbnail, String subtitle) {
        this.isbn = isbn;
        this.authors = authors;
        this.description = description;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.title = title;
        this.thumbnail = thumbnail;
        this.smallThumbnail = smallThumbnail;
        this.subtitle = subtitle;
    }

    /**
     * Get a book for a specified ISBN-10 identifier.
     * @param isbn The isbn of the book to get.
     * @return The {@link Book} or null if invalid ISBN.
     */
    public static Book get(String isbn) {
        String url = Constants.API_URL + ENDPOINT + isbn;
        HttpRequest request = HttpRequest.get(url);
        String data = request.body();
        Log.d("steve", data);

        switch (request.code()) {
            case HttpURLConnection.HTTP_OK:
                Gson gson = new Gson();
                return gson.fromJson(data, Book.class);
            default:
                return null;
        }
    }

    public String getIsbn() {
        return isbn;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getDescription() {
        return description;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", authors=" + Arrays.toString(authors) +
                ", description='" + description + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", smallThumbnail='" + smallThumbnail + '\'' +
                ", subtitle='" + subtitle + '\'' +
                '}';
    }
}
