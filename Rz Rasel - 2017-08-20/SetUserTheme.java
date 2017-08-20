package com.sm.cmdss.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.library.LogWriter;
import com.sm.cmdss.Models.SysUserType;
import com.sm.cmdss.R;
import com.sm.cmdss.RtlSpacingHelper;

import java.util.Locale;

/**
 * Created by Rz Rasel on 23-Aug-16.
 */
public class SetUserTheme {
    //|------------------------------------------------------------|
    private static int setTheme;
    public static int THEME_BANK = SysUserType.TYPE.BANK.getUserPriority();
    public static int THEME_DISTRIBUTOR = SysUserType.TYPE.DISTRIBUTOR.getUserPriority();
    public static int THEME_DEALER = SysUserType.TYPE.DEALER.getUserPriority();
    public static int THEME_AGENT = SysUserType.TYPE.AGENT.getUserPriority();
    //|------------------------------------------------------------|
    //|------------------------------------------------------------|

    public static void OnCreateTheme(Activity argActivity) {
        /*switch (setTheme) {
            case THEME_BANK:
                argActivity.setTheme(R.style.ThemeBank);
                break;
            case THEME_DISTRIBUTOR:
                argActivity.setTheme(R.style.ThemeDistributor);
                break;
            case THEME_DEALER:
                argActivity.setTheme(R.style.ThemeDealer);
                break;
            case THEME_AGENT:
                argActivity.setTheme(R.style.ThemeAgent);
                break;
            default:
                argActivity.setTheme(R.style.ThemeAgent);
        }*/
        if (setTheme == THEME_BANK) {
            argActivity.setTheme(R.style.ThemeBank);
        } else if (setTheme == THEME_DISTRIBUTOR) {
            argActivity.setTheme(R.style.ThemeDistributor);
        } else if (setTheme == THEME_DEALER) {
            argActivity.setTheme(R.style.ThemeDealer);
        } else if (setTheme == THEME_AGENT) {
            argActivity.setTheme(R.style.ThemeAgent);
        } else {
            argActivity.setTheme(R.style.ThemeAgent);
        }
    }

    //|------------------------------------------------------------|
    public static void ApplyTheme(Activity argActivity, SysUserType.TYPE argUserType) {
        if (SysUserType.TYPE.BANK == argUserType) {
            setTheme = THEME_BANK;
        } else if (SysUserType.TYPE.DISTRIBUTOR == argUserType) {
            setTheme = THEME_DISTRIBUTOR;
        } else if (SysUserType.TYPE.DEALER == argUserType) {
            setTheme = THEME_DEALER;
        } else if (SysUserType.TYPE.AGENT == argUserType) {
            setTheme = THEME_AGENT;
        }
        /*argActivity.finish();
        argActivity.startActivity(new Intent(argActivity, argActivity.getClass()));*/
        OnCreateTheme(argActivity);
    }

    //|------------------------------------------------------------|
    public static void setStatusBarColor(Activity argActivity, int argColorResource) {
        //CALLING > SetUserTheme.setStatusBarColor(activity, Color.parseColor("#ff8800"));
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFA2C13E")));
        /*this.llmainLayout.setBackgroundColor(Color.parseColor(SharePreferenceUtil.getAppThemeColor(con)));
        this.mToolbar.setBackgroundColor(Color.parseColor(SharePreferenceUtil.getAppThemeColor(con)));*/
        if (Build.VERSION.SDK_INT > 19) {
            Window window = argActivity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(RtlSpacingHelper.UNDEFINED);
            float[] hsv = new float[3];
            Color.colorToHSV(argColorResource, hsv);
            hsv[2] = hsv[2] * 0.8f;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(Color.HSVToColor(hsv));
            }
            ActionBar actionBar = ((AppCompatActivity) argActivity).getSupportActionBar();
            if (actionBar != null) {
                ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00793A"));
                actionBar.setBackgroundDrawable(colorDrawable);
                //bar.setBackgroundDrawable(new ColorDrawable(0xff00DDED));
            }
        }
    }

    //|------------------------------------------------------------|
    public static void GetDefaultLang(Context argContext) {
        LogWriter.Log("LANGUAGE" + Locale.getDefault().getLanguage());
        LogWriter.Log("LANGUAGE" + argContext.getResources().getConfiguration().locale.getLanguage());
    }

    //|------------------------------------------------------------|
    public static void SetAppLang(Context argContext, String argTargetLang) {
        //|------------------------------------------------------------|
        Locale locale = new Locale(argTargetLang);
        Configuration config = argContext.getResources().getConfiguration();

        //locale = new Locale(lang);
        Locale.setDefault(locale);
        //config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        argContext.getResources().updateConfiguration(config, argContext.getResources().getDisplayMetrics());
        //|------------------------------------------------------------|
        //getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //|------------------------------------------------------------|
    }

    //|------------------------------------------------------------|
    public static String GetString(Context argContext, int argId) {
        String retVal = "";
        Resources resources = argContext.getResources();
        retVal = resources.getString(argId);
        return retVal;
    }
    //|------------------------------------------------------------|
}
//http://mrbool.com/how-to-change-the-layout-theme-of-an-android-application/25837