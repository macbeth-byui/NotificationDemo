package t.macbeth.notificationdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import java.util.Calendar;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("NotificationDemo","BootReceiver onReceive");

        SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String message = sharedPreferences.getString("message", "");
        int time = sharedPreferences.getInt("time", 0);
        Log.d("NotificationDemo", "Shared Preferences Read: time="+time);

        if (time > 0) {
            // Create an alarm to go off every `time` milliseconds
            // https://developer.android.com/training/scheduling/alarms
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            if (alarmManager == null) {
                Log.d("NotificationDemo", "Unable to create AlarmManager object.");
                return;
            }

            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + time*1000, time*1000, alarmPendingIntent);
            Log.d("NotificationDemo", "Alarm set for "+time+" sec");
        }
    }
}
