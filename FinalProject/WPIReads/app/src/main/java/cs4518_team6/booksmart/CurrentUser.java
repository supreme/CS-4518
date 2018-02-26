package cs4518_team6.booksmart;

import cs4518_team6.booksmart.model.User;

/**
 * Singleton for maintaining the current, logged in, user.
 * Created by Stephen Andrews.
 */
public class CurrentUser {

    /**
     * The singleton instance.
     */
    private static CurrentUser instance;

    /**
     * The current, logged in, user.
     */
    private User user;

    /**
     * Private constructor.
     * @param username The username of the user who logged in.
     */
    private CurrentUser(String username) {
        this.user = User.get(username);
    }

    /**
     * Get the singleton instance. Used on initial creation.
     * @param username The username of the user who logged in.
     * @return The singleton instance.
     */
    public static CurrentUser getInstance(String username) {
        if (instance == null) {
            instance = new CurrentUser(username);
        }

        return instance;
    }

    /**
     * Get the singleton instance.
     * @return The singleton.
     */
    public static CurrentUser getInstance() {
        return instance;
    }

    /**
     * Returns the current user.
     * @return The current user.
     */
    public User getUser() {
        return user;
    }
}
