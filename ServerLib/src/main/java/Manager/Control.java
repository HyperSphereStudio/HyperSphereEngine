package Manager;
import Appl.AndroidNetwork;
import Appl.WebSever.JavaHTTPServer;
import Appl.WebSever.URLManager;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.hyperspherestudio.kryonet.HSSettings;
import com.hyperspherestudio.kryonet.HyperListener;
import com.hyperspherestudio.kryonet.HyperSphereServer;
import java.util.ArrayList;
import Connection.Packet;
//Author Johnathan Bizzano
public abstract class Control {

    public static ServerSettings serverSettings;
    public static HyperSphereServer hyperSphereServer;
    public static URLManager urlManager = new URLManager();
    private static ArrayList<Class> classes = new ArrayList<>();

    private static void setServerSettings(ServerSettings serverSettings) {
        Control.serverSettings = serverSettings;
    }

    public static void main(ServerSettings serverSettings) {
        setServerSettings(serverSettings);
        System.out.println("Initializing HyperSphereEngine (Server) created by Johnathan Bizzano!");
    }

    public void startKryonetServer() {
        hyperSphereServer = new HyperSphereServer(new HSSettings(serverSettings.TCP_PORT(), serverSettings.UDP_PORT(), serverSettings.start_batchAt()));
        registerClass(Packet.class);
        registerClass(boolean[].class);
        registerClass(int[].class);
        for (Class c : classes) {
            hyperSphereServer.getKryo().register(c);
        }
        classes.clear();
        hyperSphereServer.addListener(new Listener(hyperSphereServer.getKryo(), this));
        hyperSphereServer.start();
    }

    public abstract void onConnected(Connection c);

    public abstract void onDisconnected(Connection c);

    public abstract void onReceived(Object o, Connection c);

    public static void startWebServer(){
       JavaHTTPServer.start();
    }

    public static void startAndroidNetwork(){
        AndroidNetwork.startAndroidNetwork();
    }

    public static void registerClass(Class clzz) {
        classes.add(clzz);
    }

    public static String root(){
        return serverSettings.rootPath();
    }

    public static void logError(Exception e){
        serverSettings.sendToErrorLog(e);
    }

}class Listener extends HyperListener{

    public Control control;
    public Listener(Kryo kryo, Control control) {
        super(kryo);
        this.control = control;
    }

    @Override
    public void Disconnected(Connection connection) {
        control.onDisconnected(connection);
    }

    @Override
    public void Connected(Connection connection) {
        control.onConnected(connection);
    }

    @Override
    public void Received(Connection connection, Object o) {
        control.onReceived(o, connection);

    }
}
