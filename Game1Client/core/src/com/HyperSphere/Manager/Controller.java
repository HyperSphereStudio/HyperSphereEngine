package com.HyperSphere.Manager;
import com.HyperSphere.Assets.Assets;
import com.esotericsoftware.kryonet.Connection;
import Manager.Control;
import States.State;
import Utils.DeviceInterface;
import Utils.DeviceManager;

public class Controller extends Control {

    public static CSettings CSettings = new CSettings();

    public Controller(DI di) {
        super(new DeviceManager(new DeviceInterface() {
            @Override
            public void sendAnalytic(short i, String s) {
                di.sendAnalytic(i, s);
            }

            @Override
            public void purchase(String s) {
                di.purchase(s);
            }

            @Override
            public void verifyPurchase(String s) {
                di.verifyPurchase(s);
            }
        }), CSettings, Assets.loadShader());
    }

    @Override
    public void onCreate() {
        Assets.startLoading();
        State.setState(new TestState());
    }



    @Override
    public void onRender() {
        Assets.partialLoading();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDispose() {

    }

    @Override
    public void onResize(int i, int i1) {

    }

    @Override
    public void onReceived(Connection connection, Object o) {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnection() {

    }


}
