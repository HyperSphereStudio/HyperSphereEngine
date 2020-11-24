package Manager;

import Graphics.Components.AbstractGraphic;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.kryonet.Connection;
import com.hyperspherestudio.kryonet.HSSettings;

public interface ClientSettings {

    int TILESIZE();

    int GAMEUNIT();

    void handleException(Exception e);

    TextureRegion defaultGetRegion(String name);

    int maxGameCoordX();

    int maxGameCoordY();

    AbstractGraphic defaultGetGraphic(String name);

    byte[] getSecret16();

    float getDefaultTransparency();

    float getVolume();

    boolean playMusic();

    boolean playSound();

   boolean clientVersion();

    int tcpPort();

    String ipAddress();

    int udpPort();

    void initializationThread();

    Object packetReadingLock();

    HSSettings hssSettings();

    String getLibraryName();

    String version();

    String GameAnalyticsKey();

    String GameAnalyticsSecret();
}
