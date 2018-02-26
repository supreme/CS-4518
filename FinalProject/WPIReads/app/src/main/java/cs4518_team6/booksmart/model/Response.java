package cs4518_team6.booksmart.model;

import com.google.gson.Gson;

/**
 * Represents the result of calling an endpoint of the backend API.
 * Created by Stephen Andrews.
 */
public class Response {

    private String message;
    private Boolean status;

    public Response(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    public static Response get(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Response.class);
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }
}
