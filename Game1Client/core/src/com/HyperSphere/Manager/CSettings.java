package com.HyperSphere.Manager;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hyperspherestudio.kryonet.HSSettings;

import Graphics.Components.AbstractGraphic;
import Manager.ClientSettings;

class CSettings implements ClientSettings {


    @Override
    public int TILESIZE() {
        return 0;
    }

    @Override
    public int GAMEUNIT() {
        return 0;
    }

    @Override
    public void handleException(Exception e) {

    }

    @Override
    public TextureRegion defaultGetRegion(String s) {
        return null;
    }

    @Override
    public int maxGameCoordX() {
        return 0;
    }

    @Override
    public int maxGameCoordY() {
        return 0;
    }

    @Override
    public AbstractGraphic defaultGetGraphic(String s) {
        return null;
    }

    @Override
    public byte[] getSecret16() {
        return new byte[]{63, 87, 70, -123, -72, -114, -11, 3, 72, 89, -81, 71, 53, 9, -70, 125};
    }

    @Override
    public float getDefaultTransparency() {
        return 0;
    }

    @Override
    public float getVolume() {
        return 0;
    }

    @Override
    public boolean playMusic() {
        return false;
    }

    @Override
    public boolean playSound() {
        return false;
    }

    @Override
    public boolean clientVersion() {
        return false;
    }

    @Override
    public int tcpPort() {
        return 0;
    }

    @Override
    public String ipAddress() {
        return null;
    }

    @Override
    public int udpPort() {
        return 0;
    }

    @Override
    public void initializationThread() {

    }

    @Override
    public Object packetReadingLock() {
        return null;
    }

    @Override
    public HSSettings hssSettings() {
        return null;
    }

    @Override
    public String getLibraryName() {
        return null;
    }

    @Override
    public String version() {
        return null;
    }

    @Override
    public String GameAnalyticsKey() {
        return null;
    }

    @Override
    public String GameAnalyticsSecret() {
        return null;
    }


    public static String getVersion(){
        return Controller.getClientSettings().version();
    }
}