package com;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * Created by Rz Rasel on 2017-08-22.
 */

public class BaseWebView extends WebView {
    //UTF-8 Charset
    public static final String CHARSET_UTF8 = "UTF-8";
    //Path to webview html template
    public static final String HTML_TEMPLATE_ERROR = "html/template_webview_error.html";
    //Substring where the error message will be placed
    public static final String HTML_TEMPLATE_CONTENT = "##CONTENT##";

    public BaseWebView(Context context) {
        super(context.getApplicationContext());
        enablePlugins(false);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context.getApplicationContext(), attrs);
        enablePlugins(false);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context.getApplicationContext(), attrs, defStyle);
        enablePlugins(false);
    }

    public void loadErrorPage(String error) {
        try {
            // Load HTML template from assets
            String htmlContent = "";
            InputStream is = getContext().getAssets().open(HTML_TEMPLATE_ERROR);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                htmlContent += line;
            }
            br.close();

            htmlContent = htmlContent.replace(HTML_TEMPLATE_CONTENT, error);

            loadData(htmlContent, "text/html; charset=" + CHARSET_UTF8, CHARSET_UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // Workaround for problems in Android 4.2.x
        // You can't type properly in input texts and nor textareas until you
        // touch the webview
        // You can't change the font size until you touch the webview
        // http://stackoverflow.com/questions/15127762/webview-fails-to-render-until-touched-android-4-2-2
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        //if (SupportVersion.JellyBean()) {
        if (currentApiVersion <= Build.VERSION_CODES.JELLY_BEAN) {
            invalidate();
        }
        super.onDraw(canvas);
    }

    protected void enablePlugins(final boolean enabled) {
        // Android 4.3 and above has no concept of plugin states
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion <= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return;
        }

        //if (SupportVersion.Froyo()) {
        if (currentApiVersion >= android.os.Build.VERSION_CODES.FROYO) {
            // Note: this is needed to compile against api level 18.
            try {
                Class<Enum> pluginStateClass = (Class<Enum>) Class.forName("android.webkit.WebSettings$PluginState");

                Class<?>[] parameters = {pluginStateClass};
                Method method = getSettings().getClass().getDeclaredMethod("setPluginState", parameters);

                Object pluginState = Enum.valueOf(pluginStateClass, enabled ? "ON" : "OFF");
                method.invoke(getSettings(), pluginState);
            } catch (Exception e) {
                System.out.println("Unable to modify WebView plugin state.");
            }
        } else {
            try {
                Method method = Class.forName("android.webkit.WebSettings").getDeclaredMethod("setPluginsEnabled", boolean.class);
                method.invoke(getSettings(), enabled);
            } catch (Exception e) {
                System.out.println("Unable to " + (enabled ? "enable" : "disable")
                        + "WebSettings plugins for BaseWebView.");
            }
        }
    }
}
/*
https://gist.github.com/Aracem/5546538
http://gurushya.com/customizing-android-webview/
https://stackoverflow.com/questions/7813914/android-webview-is-there-a-way-to-know-what-the-previous-url-is

https://gist.github.com/markcerqueira/10b7ec3e526ec123429ac887a955b6b6
https://gist.github.com/yava555/6120617

https://gist.github.com/ldaqiangl/a0bd1a2961a7f07747733eb8a20834be
https://gist.github.com/canujohann/03e2cfafb4344ca6c6cc
https://gist.github.com/terrytowne/0f32995915328c31a70c


https://gist.github.com/hndr91/566b2abd7ff13f849956

https://gist.github.com/azatyan/ac14deabc26a8e0fe0d0835df5852ec2
https://gist.github.com/gunta/1260776

https://gist.github.com/xoan/2953856
https://gist.github.com/adrobisch/734117
https://gist.github.com/JBirdVegas/5119685

--CAN_ACCEPT
https://gist.github.com/slightfoot/c470b49905b9d61c6cdc
https://gist.github.com/ManzzBaria/bb9e4dda407ddfd474dc
https://gist.github.com/justinthomas-syncbak/649403e7a712625b4aa8
https://gist.github.com/stevesohcot/9e164de106b5a4e969c822fa74207716

Proguard
https://gist.github.com/arnozhang/7b6b7f68ccf16de00ab679c3c6bf16f2
https://gist.github.com/xtools-at/cf3eb1654d1162ef9a93c5354154344c
https://gist.github.com/akyao/2280497
https://stackoverflow.com/questions/14002446/directly-put-html-code-in-a-webview-android

https://stackoverflow.com/questions/41741794/how-to-inject-a-javascript-script-in-a-webview-before-the-html-load-in-android-7

https://gist.github.com/ixiyang/f7b3f35e021700f4a06c

https://stackoverflow.com/questions/8200945/how-to-get-html-content-from-a-webview
*/