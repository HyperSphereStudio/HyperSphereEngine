package com.HyperSphere;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import com.HyperSphere.BaseGame.R;
import com.HyperSphere.FireBase.FireBaseMessageManager;
import com.HyperSphere.Manager.Launcher;
import com.HyperSphere.Manager.DI;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

public class AndroidLauncher extends AndroidApplication {

    public static boolean usingAndroid = false;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createChannel();
        initGameAnalytics();
        usingAndroid = true;
        context = this;
        FirebaseApp.initializeApp(this);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        initialize(new Launcher(new DI() {
            @Override
            public void sendAnalytic(int code, String s) {
                System.out.println("Message:" + s + " Code:" + code);
            }

            @Override
            public void purchase(String s) {
                System.out.println("Attempted Purchase:" + s);
            }

            @Override
            public void verifyPurchase(String s) {
                System.out.println("Verify Purchase:" + s);
            }
        }), config);
    }

    public void initGameAnalytics(){/*
        GameAnalytics.configureBuild("android " + (App Version));
        GameAnalytics.configureGameEngineVersion("2.0.0");
        GameAnalytics.initializeWithGameKey(this, PutKeyHere, PutSecretHere);*/
    }

    private void createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !FireBaseMessageManager.wasCreated) {
            FireBaseMessageManager.wasCreated = true;
            String channelId = getResources().getString(R.string.default_notification_channel_id);
            String channelName = getResources().getString(R.string.app_name);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
    }


    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }


}
