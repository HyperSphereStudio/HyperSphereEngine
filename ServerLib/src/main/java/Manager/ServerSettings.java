package Manager;

public interface ServerSettings {

    byte[] getSecret16();

    int TCP_PORT();

    int UDP_PORT();

    int start_batchAt();

    String rootPath();

    void sendToErrorLog(Exception e);

    String emailUsername();

    String emailPassword();

    String VerifiedWebPagePath();

    String ErrorMessageWebPagePath();

    String WrongLinkWebPagePath();

    String ActionNotAvailableWebPagePath();

    String ServerName();

    int WebServerPort();

    String ServiceAccountID();

    String ServiceAccountPrivateKeyP12Path();

    String FireBaseAccountPrivateKeyJsonPath();

    String FireBaseDataURL();

    String URLACTIVELINK();
}
