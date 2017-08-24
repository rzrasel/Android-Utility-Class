package com.rz.facebookpowerpost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ActLogin extends AppCompatActivity {
    private Activity activity;
    private Context context;
    private CallbackManager fbCallbackManager;
    private AccessTokenTracker fbAccessTokenTracker;
    private Button sysBtnFbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        context = this;
        super.onCreate(savedInstanceState);
        onFacebookSDKInitialize(context);
        setContentView(R.layout.act_login);
        getSupportActionBar().setTitle("Login");
        sysBtnFbLogin = (Button) findViewById(R.id.sysBtnFbLogin);
        sysBtnFbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View argView) {
                System.out.println("|----|KEY_HASH|onClick");
                onFbLogin();
            }
        });
        if (isFbUserLoggedIn()) {
            System.out.println("|----|KEY_HASH|isFbUserLoggedIn True");
        } else {
            System.out.println("|----|KEY_HASH|isFbUserLoggedIn False");
        }
    }

    protected void onFacebookSDKInitialize(Context argContext) {
        //FacebookSdk.sdkInitialize(getApplicationContext());
        fbCallbackManager = CallbackManager.Factory.create();
        fbAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken argOldAccessToken, AccessToken argCurrentAccessToken) {
                System.out.println("|----|KEY_HASH|argOldAccessToken|" + argOldAccessToken);
                System.out.println("|----|KEY_HASH|argCurrentAccessToken|" + argCurrentAccessToken);
            }
        };
    }

    private void onFbLogin() {
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        final Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,last_name,link,email,picture");
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));
        LoginManager.getInstance().registerCallback(fbCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult argLoginResult) {
                        System.out.println("|----|KEY_HASH|onSuccess");
                        GraphRequest request = GraphRequest.newMeRequest(argLoginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject argJSONObject, GraphResponse argResponse) {
                                if (argResponse.getError() != null) {
                                    // handle error
                                    System.out.println("ERROR");
                                } else {
                                    System.out.println("Success");
                                    try {

                                        String jsonResult = String.valueOf(argJSONObject);
                                        System.out.println("JSON Result" + jsonResult);

                                        //String str_email = argJSONObject.getString("email");
                                        String str_id = argJSONObject.getString("id");
                                        String str_firstname = argJSONObject.getString("first_name");
                                        String str_lastname = argJSONObject.getString("last_name");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("|----|KEY_HASH|onCancel");
                    }

                    @Override
                    public void onError(FacebookException argException) {
                        System.out.println("|----|KEY_HASH|onError");
                    }
                });
    }

    @Override
    protected void onActivityResult(int argRequestCode, int argResultCode, Intent argData) {
        super.onActivityResult(argRequestCode, argResultCode, argData);
        fbCallbackManager.onActivityResult(argRequestCode, argResultCode, argData);
    }

    private boolean hasFbUserLoggedIn() {
        boolean retVal = false;
        fbAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken argOldAccessToken, AccessToken argNewAccessToken) {
                onUpdateWithFbToken(argNewAccessToken);
            }
        };
        return retVal;
    }

    private boolean onUpdateWithFbToken(AccessToken argCurrentAccessToken) {
        if (argCurrentAccessToken != null)
            return true;
        else
            return false;
    }

    private boolean isFbUserLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
/*

ProfileTracker fbProfileTracker = new ProfileTracker() {
    @Override
    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
        // User logged in or changed profile
    }
};

Profile profile = Profile.getCurrentProfile();
if (profile != null) {
    Log.v(TAG, "Logged, user name=" + profile.getFirstName() + " " + profile.getLastName());
}

*/