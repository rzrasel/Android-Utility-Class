package com.sm.navigationdrawerone;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActSplash extends AppCompatActivity {
    private final int CONST_REDIRECT_WINDOW = 1;
    private final int CONST_REDIRECT_TIME = 1000 * 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        /*Message message = handlerRedirectWindow.obtainMessage(CONST_REDIRECT_WINDOW);
        handlerRedirectWindow.sendMessageDelayed(message, CONST_REDIRECT_TIME);*/
        onRedirectWindow(ActDashboard.class, true);
    }

    private Handler handlerRedirectWindow = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message argMessage) {
            //super.handleMessage(msg);
            if (argMessage.what == CONST_REDIRECT_WINDOW) {
                //counter++;
                //sysId.setText("Time(s): " + counter);
                /*Message message = handlerRedirectWindow.obtainMessage(CONST_REDIRECT_WINDOW);
                handlerRedirectWindow.sendMessageDelayed(message, 2000);
                changeButtonIcon();*/
                onRedirectWindow(ActDashboard.class, true);
            }
        }
    };

    private void onRedirectWindow(Class argClass, boolean argIsCloseSelf) {
        Intent intent = new Intent(getApplicationContext(), argClass);
        //Intent intent = new Intent(getApplicationContext(), ActOTPCode.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        //bundle.putSerializable(APPConstants.SESSION.KEY, userSession);
        intent.putExtras(bundle);
        startActivity(intent);
        if (argIsCloseSelf)
            finish();
    }
}
