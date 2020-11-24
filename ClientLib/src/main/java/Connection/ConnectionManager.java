package Connection;

import Connection.Objects.Packet;
import Connection.Reader.PacketManager;
import Manager.Control;
import com.esotericsoftware.kryonet.Connection;
import com.hyperspherestudio.kryonet.HyperListener;
import com.hyperspherestudio.kryonet.HyperSphereClient;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectionManager {

    public static void start() {
        if (Control.getHyperSphereClient() == null) {
            Control.setHyperSphereClient(new HyperSphereClient(Control.getClientSettings().hssSettings()));
            register();
            Control.setPacketManager(new PacketManager());
            Control.getPacketManager().start();
            Control.getHyperSphereClient().addListener(new Listener());
            new Thread(Control.getHyperSphereClient()).start();
            Control.getHyperSphereClient().start();
            new Thread(() -> {
                try {
                    Control.getHyperSphereClient().connect(5000, Control.getClientSettings().ipAddress(), Control.getClientSettings().tcpPort(), Control.getClientSettings().udpPort());
                } catch (IOException ignored) {
                    new Thread(() -> Control.getHyperSphereClient().reconnectLoop(6000)).start();
                }
            }).start();

        }
    }

    public static void stop() {
        if(Control.getHyperSphereClient() != null)
        Control.getHyperSphereClient().stop();
        if(Control.getPacketManager() != null)
        Control.getPacketManager().stop();
    }

    private static void register() {
        Control.getHyperSphereClient().getKryo().register(Packet.class);
        Control.getHyperSphereClient().getKryo().register(ArrayList.class);
        Control.getHyperSphereClient().getKryo().register(int[].class);
        Control.getHyperSphereClient().getKryo().register(boolean[].class);
        for (Class c : Control.getRegisteredClasses()) {
            Control.getHyperSphereClient().getKryo().register(c);
        }
        Control.getRegisteredClasses().clear();
    }
}

class Listener extends HyperListener {


    public Listener() {
        super(Control.getHyperSphereClient().getKryo());
    }

    @Override
    public void Disconnected(Connection connection) {
        new Thread(() -> Control.getHyperSphereClient().reconnectLoop(6000)).start();
        Control.control.onDisconnection();
    }

    @Override
    public void Connected(Connection connection) {
        Control.control.onConnected();
    }

    @Override
    public void Received(Connection connection, Object o) {
        if (!Control.isPaused()) {
            PacketManager.queue.add(o);
            Control.getPacketManager().interrupt();
        }
        Control.control.onReceived(connection, o);
    }
}
