package Control;
import Manager.Control;
import Manager.ServerSettings;
import com.esotericsoftware.kryonet.Connection;

public class Controller extends Control {

    public static void main(String[] args) {
        Control.main(new Settings());
    }

    public Controller(ServerSettings serverSettings) {
        Control.serverSettings = serverSettings;
    }

    @Override
    public void onConnected(Connection connection) {

    }

    @Override
    public void onDisconnected(Connection connection) {

    }

    @Override
    public void onReceived(Object o, Connection connection) {

    }
}class Settings implements ServerSettings {


    @Override
    public byte[] getSecret16() {
        return new byte[]{63, 87, 70, -123, -72, -114, -11, 3, 72, 89, -81, 71, 53, 9, -70, 125};
    }

    @Override
    public int TCP_PORT() {
        return 0;
    }

    @Override
    public int UDP_PORT() {
        return 0;
    }

    @Override
    public int start_batchAt() {
        return 0;
    }

    @Override
    public String rootPath() {
        return null;
    }

    @Override
    public void sendToErrorLog(Exception e) {

    }

    @Override
    public String emailUsername() {
        return null;
    }

    @Override
    public String emailPassword() {
        return null;
    }

    @Override
    public String VerifiedWebPagePath() {
        return null;
    }

    @Override
    public String ErrorMessageWebPagePath() {
        return null;
    }

    @Override
    public String WrongLinkWebPagePath() {
        return null;
    }

    @Override
    public String ActionNotAvailableWebPagePath() {
        return null;
    }

    @Override
    public String ServerName() {
        return null;
    }

    @Override
    public int WebServerPort() {
        return 0;
    }

    @Override
    public String ServiceAccountID() {
        return null;
    }

    @Override
    public String ServiceAccountPrivateKeyP12Path() {
        return null;
    }

    @Override
    public String FireBaseAccountPrivateKeyJsonPath() {
        return null;
    }

    @Override
    public String FireBaseDataURL() {
        return null;
    }

    @Override
    public String URLACTIVELINK() {
        return null;
    }
}
