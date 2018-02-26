package cs4518_team6.booksmart.model;

import android.os.StrictMode;

/**
 * Parent class of all model objects.
 * Created by Stephen Andrews.
 */
public class Model {

    static {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
