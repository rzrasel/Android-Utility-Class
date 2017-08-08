package com.sm.cmdss.Receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by Rz Rasel on 2016-12-19.
 */

public class BootAlarmManager {
    private Context context;
    private Class serviceClass;
    private static final long INTERVAL = 10 * 1000; // unit: ms
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public BootAlarmManager(Context argContext, Class<?> argServiceClass) {
        context = argContext;
        serviceClass = argServiceClass;
        onInitData();
    }

    private void onInitData() {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, INTERVAL, pendingIntent);
        Intent intent = new Intent(context, serviceClass);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), INTERVAL, pendingIntent);
        if (pendingIntent == null) {
            //onCreateServiceControllerPendIntent();
        }
    }

    private void onCreateServiceControllerPendIntent() {
        Intent intent = new Intent(context, serviceClass);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public void onCanclePendingIntent() {
        /*if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        } else {
            createServiceControllerPendIntent();
            alarmManager.cancel(pendingIntent);
        }
        // MainActivity.this.stopService(ServiceController
        // .getServiceIntent());
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), NetStatusMonitorService.class);
        stopService(intent);*/
    }
}
/*
Is it possible to run my service for indefinite time in android
http://stackoverflow.com/questions/18219657/how-do-i-keep-a-service-alive-indefinitely
*/