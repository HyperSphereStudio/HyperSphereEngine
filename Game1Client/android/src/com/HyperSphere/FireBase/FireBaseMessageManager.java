package com.HyperSphere.FireBase;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.HyperSphere.AndroidLauncher;
import com.HyperSphere.BaseGame.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import static com.HyperSphere.AndroidLauncher.context;

public class FireBaseMessageManager extends FirebaseMessagingService {
    public static boolean wasCreated;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("Received:" + remoteMessage.getNotification().getTitle() + " M:" + remoteMessage.getNotification().getBody());
        notification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    public void onNewToken(@NonNull String var1) {

    }

    public void notification(String title, String message){
        Intent intent = new Intent(this, AndroidLauncher.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_notification_important)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(!wasCreated)createChannel();
            notificationBuilder.setChannelId(getResources().getString(R.string.default_notification_channel_id));
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    private void createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wasCreated = true;
            String channelId = getResources().getString(R.string.default_notification_channel_id);
            String channelName = getResources().getString(R.string.app_name);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
    }

}
