package cs4518_team6.booksmart.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs4518_team6.booksmart.Constants;
import cs4518_team6.booksmart.LoginActivity;

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
    private List<Book> owned;
    private List<Book> wanted;

    public User(String username, String firstName, String lastName, List<Book> owned, List<Book> wanted) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.owned = owned;
        this.wanted = wanted;
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
     * @param context The context the login is being executed from.
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
