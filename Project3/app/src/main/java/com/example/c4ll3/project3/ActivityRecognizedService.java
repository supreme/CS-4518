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

        Log.d("steve", "blah: " + intent.getStringExtra("blah"));
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities());
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
        for(DetectedActivity activity : probableActivities) {
            Bundle data = new Bundle();
            Message message = Message.obtain();

            // TODO: activity.getConfidence() >= SOME_THRESHOLD
            switch(activity.getType()) {
                case DetectedActivity.RUNNING: {
                    Log.d("steve", "Running: " + activity.getConfidence() );
                    data.putString("ACTIVITY", "Running");
                    message.setData(data);
                    break;
                }
                case DetectedActivity.STILL: {
                    Log.d("steve", "Still: " + activity.getConfidence() );
                    data.putString("ACTIVITY", "Still");
                    message.setData(data);
                    break;
                }
                case DetectedActivity.WALKING: {
                    Log.d("steve", "Walking: " + activity.getConfidence() );
                    data.putString("ACTIVITY", "Walking");
                    message.setData(data);
                    break;
                }
            }

            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
