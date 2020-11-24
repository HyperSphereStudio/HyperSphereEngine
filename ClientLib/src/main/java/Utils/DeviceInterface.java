package Utils;

public interface DeviceInterface {

    void sendAnalytic(short errorMessage, String message);

    void purchase(String purchaseID);

    void verifyPurchase(String string);
}
