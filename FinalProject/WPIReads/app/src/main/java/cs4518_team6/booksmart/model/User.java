package cs4518_team6.booksmart.model;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import cs4518_team6.booksmart.Constants;

/**
 * Represents a user interacting with the app.
 * Created by Stephen Andrews.
 */
public class User extends Model {

    /**
     * API endpoint for managing users.
     */
    public static final String ENDPOINT = "/users/";

    private String username;
    private String firstName;
    private String lastName;
    private Book[] owned;
    private Book[] wanted;

    /**
     * Add a {@link Book} to the user's wanted list.
     * @param isbn The isbn of the book to add.
     */
    public boolean addWanted(String isbn) {
        String url = Constants.API_URL + ENDPOINT + "wanted";
        Map<String, String> data = new HashMap<>();
        data.put("isbn", isbn);
        data.put("username", username);

        return HttpRequest.post(url).form(data).code() == HttpURLConnection.HTTP_OK;
    }

    /**
     * Remove a {@link Book} from the user's wanted list.
     * @param isbn The isbn of the book to remove.
     * @return <code>true</code> if successful, otherwise <code>false</code>
     */
    public boolean removeWanted(String isbn) {
        String url = Constants.API_URL + ENDPOINT + username + "/wanted/" + isbn;

        return HttpRequest.delete(url).code() == HttpURLConnection.HTTP_OK;
    }

    /**
     * Add a {@link Book} to the user's wanted list.
     * @param isbn The isbn of the book to add.
     * @return <code>true</code> if successful, otherwise <code>false</code>
     */
    public boolean addOwned(String isbn) {
        String url = Constants.API_URL + ENDPOINT + "owned";
        Map<String, String> data = new HashMap<>();
        data.put("isbn", isbn);
        data.put("username", username);

        return HttpRequest.post(url).form(data).code() == HttpURLConnection.HTTP_OK;
    }

    /**
     * Remove a {@link Book} from the user's owned list.
     * @param isbn The isbn of the book to remove.
     * @return <code>true</code> if successful, otherwise <code>false</code>
     */
    public boolean removeOwned(String isbn) {
        String url = Constants.API_URL + ENDPOINT + username + "/owned/" + isbn;

        return HttpRequest.delete(url).code() == HttpURLConnection.HTTP_OK;
    }

    /**
     * Get a user object by a specified username.
     * @param username The username of the user to get.
     * @return The user if it exists, otherwise null.
     */
    public static User get(String username) {
        String url = Constants.API_URL + ENDPOINT + username;
        HttpRequest request = HttpRequest.get(url);

        switch(request.code()) {
            case HttpURLConnection.HTTP_OK:
                return new Gson().fromJson(request.body(), User.class);
            default:
                return null;
        }
    }

    /**
     * Register a new user.
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @return
     */
    public static Response register(String username, String password, String firstName, String lastName) {
        String url = Constants.API_URL + ENDPOINT + "register";
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        data.put("firstName", firstName);
        data.put("lastName", lastName);

        HttpRequest request = HttpRequest.post(url).form(data);
        String response = request.body();
        Log.d("steve", response);
        return Response.get(response);
    }

    /**
     * Attempts to login a user.
     * @param username The username of the user (email).
     * @param password The user's password.
     * @return A user object if successful, otherwise null.
     */
    public static User login(String username, String password) {
        String url = Constants.API_URL + ENDPOINT + "login";
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);

        HttpRequest request = HttpRequest.post(url).form(data);
        Response response = Response.get(request.body());
        if (response.getStatus()) {
            return get(username);
        }

        return null;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Book[] getOwned() {
        return owned;
    }

    public Book[] getWanted() {
        return wanted;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", owned=" + owned +
                ", wanted=" + wanted +
                '}';
    }
}
