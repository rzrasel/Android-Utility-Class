package com.sm.cmdss.SharePref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;

/**
 * Created by Rz Rasel on 2017-08-07.
 */

public class SharePrefPrivateHandler {
    private Context context;
    public static final String PREFS_NAME = "AOP_PREFS";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private MODE mode = MODE.PRIVATE;
    private String retVal;

    private enum MODE {
        PRIVATE("private", Context.MODE_PRIVATE);
        private String modeName;
        private int modeValue;

        MODE(String argModeName, int argModeValue) {
            this.modeName = argModeName;
            this.modeValue = argModeValue;
        }

        public String getModeName() {
            return this.modeName;
        }

        public int getModeValue() {
            return this.modeValue;
        }
    }

    //SharedPreferences  settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    public SharePrefPrivateHandler(Context argContext) {
        this.context = argContext;
        //mode = MODE.PRIVATE;
    }

    public SharePrefPrivateHandler setData(String argKey, String argValue) {
        //sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, mode.getModeValue());
        editor = sharedPreferences.edit();
        editor.putString(argKey, argValue);
        return this;
    }

    public void saveData() {
        if (editor != null) {
            editor.commit();
        }
    }

    public String getValue(String argKey) {
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, mode.getModeValue());
        retVal = sharedPreferences.getString(argKey, null);
        return retVal;
    }

    public Map<String, ?> getAllKeyValue() {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, mode.getModeValue());
        Map<String, ?> mapKeyValueItems = sharedPreferences.getAll();

        /*for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }*/
        return mapKeyValueItems;
    }

    public void clearByKey(String argKey) {
        //removeValue
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, mode.getModeValue());
        editor = sharedPreferences.edit();

        editor.remove(argKey);
        editor.commit();
    }

    public void clearAll() {
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, mode.getModeValue());
        editor = sharedPreferences.edit();

        editor.clear();
        editor.commit();
    }
}
/*
https://www.tutorialspoint.com/android/android_shared_preferences.htm
http://androidopentutorials.com/android-sharedpreferences-tutorial-and-example/

https://stackoverflow.com/questions/23635644/how-can-i-view-the-shared-preferences-file-using-android-studio
if (preferences.contains("yourKey"))
https://stackoverflow.com/questions/7057845/save-arraylist-to-sharedpreferences
https://stackoverflow.com/questions/3876680/is-it-possible-to-add-an-array-or-object-to-sharedpreferences-on-android
*/