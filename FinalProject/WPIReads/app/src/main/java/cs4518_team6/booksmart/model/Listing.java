package cs4518_team6.booksmart.model;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs4518_team6.booksmart.Constants;

/**
 * Represents a book being available for purchasing, loaning, or swapping.
 * Created by Stephen Andrews.
 */
public class Listing extends Model {

    private static final String ENDPOINT = "/listings/";

    /**
     * Represents the available listing types.
     */
    public enum ListingType {
        FOR_TRADE,
        FOR_LOAN,
        FOR_SALE
    }

    private Integer listingId;
    private String username;
    private String isbn;
    private String condition;
    private Double price;
    private String[] listingTypes;

    public static boolean add(String username, String isbn, String condition, Double price, final List<ListingType> listingTypes) {
        String url = Constants.API_URL + ENDPOINT + "add";
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("isbn", isbn);
        data.put("condition", condition);
        if (price != null) {
            data.put("price", String.valueOf(price));
        }

        List<Integer> listingTypeIds = new ArrayList<>();
        for (ListingType l : listingTypes) {
            listingTypeIds.add(l.ordinal() + 1);
        }
        data.put("listingTypes", listingTypeIds.toString());

        return HttpRequest.post(url).form(data).code() == HttpURLConnection.HTTP_OK;
    }

    /**
     * Delete a listing.
     * @param listingId The id of the listing to delete.
     */
    public static boolean delete(int listingId) {
        String url = Constants.API_URL + ENDPOINT + "delete/" + listingId;

        return HttpRequest.delete(url).code() == HttpURLConnection.HTTP_OK;
    }

    public static List<Listing> getAll() {
        String url = Constants.API_URL + ENDPOINT;
        HttpRequest request = HttpRequest.get(url);
        String response = request.body();

        return Arrays.asList(new Gson().fromJson(response, Listing[].class));

    }

    public Integer getListingId() {
        return listingId;
    }

    public String getUsername() {
        return username;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCondition() {
        return condition;
    }

    public Double getPrice() {
        return price;
    }

    public String[] getListingTypes() {
        return listingTypes;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "listingId=" + listingId +
                ", username='" + username + '\'' +
                ", isbn='" + isbn + '\'' +
                ", condition='" + condition + '\'' +
                ", price=" + price +
                ", listingTypes=" + Arrays.toString(listingTypes) +
                '}';
    }
}
