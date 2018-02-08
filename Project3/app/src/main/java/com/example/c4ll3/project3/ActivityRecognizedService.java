package com.example.c4ll3.project3;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Handles the activity state.
 * Created by Stephen Andrews on 2/6/18.
 */

public class ActivityRecognizedService extends IntentService {

    /**
     * A {@link Messenger} to send data back to the main thread.
     */
    private Messenger messenger;

    /**
     * Default constructor for AndroidManifest.xml.
     */
    public ActivityRecognizedService() {
        super("ActivityRecognizedService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ActivityRecognizedService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle bundle = intent.getExtras();
        messenger = (Messenger) bundle.get("messenger");

        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

            // Get probable activities and filter for still, walking, or running
            List<DetectedActivity> activities = result.getProbableActivities();
            String activity = getActivityString(activities);
            if (activity.equals(getString(R.string.activity_unknown))) {
                return;
            }

            // Send message to main activity with activity
            Message message = Message.obtain();
            Bundle data = new Bundle();
            data.putString(Constants.ACTIVITY_MESSAGE_TAG, activity);
            message.setData(data);

            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parses a list of {@link DetectedActivity}s to determine the most probable activity
     * and returns its string representation.
     * @param activities The activities to consider in order of most probable.
     * @return A string representation of the most probable activity.
     */
    private String getActivityString(List<DetectedActivity> activities) {
        for (DetectedActivity activity : activities) {
            if (activity.getConfidence() < Constants.ACTIVITY_CONFIDENCE_THRESHOLD) {
                continue;
            }

            switch (activity.getType()) {
                case DetectedActivity.STILL:
                    return getString(R.string.activity_still);
                case DetectedActivity.WALKING:
                    return getString(R.string.activity_walking);
                case DetectedActivity.RUNNING:
                    return getString(R.string.activity_running);
            }
        }

        return getString(R.string.activity_unknown);
    }
}
