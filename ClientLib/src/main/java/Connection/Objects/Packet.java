package Connection.Objects;
import Connection.PacketEvent;
import Manager.Const;
import Manager.Control;
import com.badlogic.gdx.utils.Pool;

public class Packet implements Pool.Poolable {
    public Object o, o2;
    public int[] i;
    public boolean[] b;
    public short t = Const.Packet_UNKNOWN;
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

    public Packet send(boolean free) {
        Control.getHyperSphereClient().sendTCP(this);
        if (free)
            packetPool.free(this);
        return this;
    }

    public Packet obtain() {
        return packetPool.obtain();
    }

    public static void sendPacket(short t) {
        packetPool.obtain().set(t).send(true);
    }

    public static void sendPacket(short t, Object o) {
        packetPool.obtain().set(t, o).send(true);
    }

    public static void sendPacket(short t, Object o, Object o2) {
        packetPool.obtain().set(t, o, o2).send(true);
    }

    public static void sendPacket(short t, boolean[] b) {
        packetPool.obtain().set(t, b).send(true);
    }

    public static void sendPacket(short t, Object o, Object o2, int[] array) {
        packetPool.obtain().set(t, o, o2, array).send(true);
    }

    public static void sendPacket(short t, Object o, int[] array) {
        packetPool.obtain().set(t, o, array).send(true);
    }

    public static void sendPacket(short t, int[] array) {
        packetPool.obtain().set(t, array).send(true);
    }

    public void getContents() {
        readPacket();
    }

    public void readPacket() {
        if (PacketEvent.packetEvents.containsKey(t))
            PacketEvent.packetEvents.get(t).read(this);
    }

    @Override
    public void reset() {
        o = null;
        i = null;
        o2 = null;
        t = Const.Packet_UNKNOWN;
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


}
