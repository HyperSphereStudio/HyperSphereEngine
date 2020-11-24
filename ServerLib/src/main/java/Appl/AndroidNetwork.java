package Appl;
import Manager.Control;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import java.io.*;
import java.util.Collections;

public class AndroidNetwork {
    private static AndroidPublisher androidPublisher;
    private static String packageName = "com.HyperSphere";

    public static void startAndroidNetwork() {
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

            GoogleCredential googleCredential = new GoogleCredential.Builder()
                    .setTransport(httpTransport)
                    .setJsonFactory(jacksonFactory)
                    .setServiceAccountId(Control.serverSettings.ServiceAccountID())
                    .setServiceAccountScopes(Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER))
                    .setServiceAccountPrivateKeyFromP12File(new File(Control.serverSettings.ServiceAccountPrivateKeyP12Path())).build();

            androidPublisher = new AndroidPublisher.Builder(httpTransport, jacksonFactory, googleCredential).setApplicationName(Control.serverSettings.ServerName()).build();

            FileInputStream serviceAccount =
                    new FileInputStream(Control.serverSettings.FireBaseAccountPrivateKeyJsonPath());

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(Control.serverSettings.FireBaseDataURL())
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (Exception e) {
            Control.logError(e);
        }

    }


    public static void sendNotification(String token, String title, String m) {
        Message message = Message.builder()
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(m)
                        .build())
                .setToken(token)
                .build();
        try {
            System.out.println("Sending Notification:" + FirebaseMessaging.getInstance().send(message));
        } catch (FirebaseMessagingException e) {
            Control.logError(e);
        }
    }


    public static void refundPurchase(String orderID) throws IOException {
        AndroidPublisher.Orders.Refund refund = androidPublisher.orders().refund(packageName, orderID);
        refund.execute();
    }

}
