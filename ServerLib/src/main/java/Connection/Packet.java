package Connection;
import Constants.Const;
import Manager.Control;
import com.badlogic.gdx.utils.Pool;
import com.esotericsoftware.kryonet.Connection;

import java.util.List;

public class Packet implements Pool.Poolable{
    public Object o, o2;
    public int[] i;
    public boolean[] b;
    public short t = Const.Packet_Unknown;
    public static Pool<Packet> packetPool = new Pool<Packet>() {
        @Override
        protected Packet newObject() {
            return new Packet();
        }
    };

    public Packet() {

    }

    public Packet(short type) {
        set(type);
    }

    public Packet(short type, Object o) {
        set(type, o);
    }

    public Packet(short type, Object o, Object o2) {
        set(type, o, o2);
    }

    public Packet(short type, int[] array) {
        set(type, array);
    }

    public Packet(short type, int[] array, Object o) {
        this(type, array);
        this.o = o;
    }

    public Packet(short type, boolean[] array) {
        this(type);
        this.b = array;
    }

    public Packet(short type, int[] array, Object o, Object o2) {
        this(type, array, o);
        this.o2 = o2;
    }

    public Packet send(Connection c, boolean free) {
        c.sendTCP(this);
        if (free)
            free();
        return this;
    }

    public void free(){
        packetPool.free(this);
    }


    public Packet sendConnections(List<? extends Connection> connections, boolean free){
        for(Connection c : connections){
            c.sendTCP(this);
        }
        if(free)packetPool.free(this);
        return this;
    }

    public static Packet obtain() {
        return packetPool.obtain();
    }

    public static void sendPacket(Connection c, short t) {
        packetPool.obtain().set(t).send(c, true);
    }

    public static void sendPacket(Connection c, short t, Object o) {
        packetPool.obtain().set(t, o).send(c, true);
    }

    public static void sendPacket(Connection c, short t, Object o, Object o2) {
        packetPool.obtain().set(t, o, o2).send(c,true);
    }

    public static void sendPacket(Connection c, short t, boolean[] b) {
        packetPool.obtain().set(t, b).send(c, true);
    }

    public static void sendPacket(Connection c, short t, Object o, Object o2, int[] array) {
        packetPool.obtain().set(t, o, o2, array).send(c,true);
    }

    public static void sendPacket(Connection c, short t, Object o, int[] array) {
        packetPool.obtain().set(t, o, array).send(c, true);
    }

    public static void sendPacket(Connection c, short t, int[] array) {
        packetPool.obtain().set(t, array).send(c,true);
    }

    public static void sendToALLTCPPacket(short t, Object o){
        Packet p = packetPool.obtain().set(t, o);
        Control.hyperSphereServer.sendToAllTCP(p);
        packetPool.free(p);
    }


    @Override
    public void reset() {
        o = null;
        i = null;
        o2 = null;
        t = Const.Packet_Unknown;
        b = null;
    }


    public Packet set(short type, Object o, Object o2) {
        set(type, o);
        this.o2 = o2;
        return this;
    }

    public Packet set(short type, boolean[] array) {
        set(type);
        this.b = array;
        return this;
    }

    public Packet set(short type, Object o, Object o2, int[] array) {
        set(type, o, o2);
        this.i = array;
        return this;
    }

    public Packet set(short type, Object o) {
        set(type);
        this.o = o;
        return this;
    }

    public Packet set(short type, Object o, int[] array) {
        set(type, o);
        this.i = array;
        return this;
    }

    public Packet set(short type) {
        this.t = type;
        return this;
    }

    public Packet set(short type, int[] array) {
        set(type);
        this.i = array;
        return this;
    }

    public void getContents(Connection c) {
        if(PacketEvent.packetEvents.containsKey(t))
        PacketEvent.packetEvents.get(t).read(c,this);
    }
}
