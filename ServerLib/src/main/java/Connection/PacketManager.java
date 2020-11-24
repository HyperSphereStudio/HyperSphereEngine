package Connection;

import Manager.Control;
import Utilities.Thread.HThread;
import com.esotericsoftware.kryonet.Connection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacketManager extends HThread {

    public ConcurrentHashMap<Packet, Connection> packetList = new ConcurrentHashMap<>();

    public PacketManager(int fps, String name) {
        super("Packet Manager:" + name,fps, null);
    }


    public void addPacket(Packet packet, Connection connection){
        packetList.put(packet, connection);
    }

    @Override
    public void update() {
        if(packetList != null) {
            for (Map.Entry<Packet, Connection> entry : packetList.entrySet()) {
                try {
                    entry.getKey().getContents(entry.getValue());
                } catch (Exception e2) {
                    Control.logError(e2);
                }
                packetList.remove(entry.getKey());
            }
        }
    }

}
