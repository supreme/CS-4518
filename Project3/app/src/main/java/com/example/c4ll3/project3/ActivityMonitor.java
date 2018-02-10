package com.example.c4ll3.project3;

/**
 * Monitors a user's activities.
 * @author Stephen Andrews
 */
public class ActivityMonitor {

    /**
     * The current activity a user is performing.
     */
    private String currentActivity;

    /**
     * The previous activity the user was performing.
     */
    private String previousActivity;

    /**
     * The start time of an activity.
     */
    private long startTime;

    /**
     * The end time of an activity.
     */
    private long endTime;

    public ActivityMonitor() {
        currentActivity = "";
        previousActivity = "";
        startTime = 0;
        endTime = 0;
    }

    /**
     * Whether or not the activity service needs to send an update to the main activity.
     * @return <boolean></boolean>
     */
    public boolean needsUpdate(String apiResponse) {
        if (currentActivity.isEmpty()) { // First update
            currentActivity = apiResponse;
            startTime = System.currentTimeMillis();
            endTime = startTime;
            return true;
        } else if (!currentActivity.equals(apiResponse)){
            previousActivity = currentActivity;
            currentActivity = apiResponse;
            endTime = System.currentTimeMillis();
            return true;
        }

        return false;
    }

    /**
     * Get the current activity the user was doing.
     * @return The current activity.
     */
    public String getCurrentActivity() {
        return currentActivity;
    }

    /**
     * Get the previous activity the user was doing.
     * @return The previous activity.
     */
    public String getPreviousActivity() {
        return previousActivity;
    }

    /**
     * Gets the duration the user was performing an activity for.
     * @return The duration in seconds.
     */
    public int getActivityDuration() {
        int timeSpent = ((int) (endTime - startTime)) / 1000;
        startTime = System.currentTimeMillis();

        return timeSpent;
    }
}
