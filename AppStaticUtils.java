package rz.rasel.utils;

import android.app.Activity;
import android.graphics.Typeface;

import java.util.Random;

public class AppStaticUtils {
    public static Typeface initTypeface(Activity argActivity, String argFontName) {
        Typeface typeface = null;
        typeface = Typeface.createFromAsset(argActivity.getAssets(), "fonts/" + argFontName + ".ttf");
        return typeface;
    }

    public static int getRandInt(int argMin, int argMax) {
        Random rand = new Random();
        int randomNum = rand.nextInt((argMax - argMin) + 1) + argMin;
        return randomNum;
    }
}
