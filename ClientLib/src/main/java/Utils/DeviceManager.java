package Utils;

public class DeviceManager {

    private DeviceInterface deviceInterface;
    public boolean hasPurchase, hasError;

    public DeviceManager(DeviceInterface deviceInterface){
        this.deviceInterface = deviceInterface;
    }

    public void purchase(String purchaseID){
        if(hasPurchase)
        deviceInterface.purchase(purchaseID);
    }

    public void verifyPurchase(String string){
        if(hasPurchase){
            deviceInterface.verifyPurchase(string);
        }
    }

    public void sendAnalytic(short analytic, String message){
        if(hasError)
        deviceInterface.sendAnalytic(analytic, message);
    }

}
