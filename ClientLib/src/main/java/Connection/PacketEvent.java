package Connection;
import Connection.Objects.Packet;
import Connection.Reader.PacketReader;
import Manager.Control;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

public class PacketEvent {

    public PacketReader packetReader;
    public static Short2ObjectOpenHashMap<PacketEvent> packetEvents = new Short2ObjectOpenHashMap<>();
    public String label;
    public boolean lockable;

    public PacketEvent(short id, PacketReader packetReader, String label) {
        this.packetReader = packetReader;
        packetEvents.put(id, this);
        this.label = label;
    }

    public PacketEvent(short id, PacketReader packetReader, String label, boolean lockable){
        this(id, packetReader, label);
        this.lockable = lockable;
    }

    public void read(Packet packet) {
        if(lockable){
            synchronized (Control.getClientSettings().packetReadingLock()){
                packetReader.readPacket(packet);
            }
        }else{
            packetReader.readPacket(packet);
        }
    }




}
