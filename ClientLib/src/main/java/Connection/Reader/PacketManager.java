package Connection.Reader;
import Connection.Objects.Packet;
import Manager.Control;
import Utils.Thread.IThread;
import com.badlogic.gdx.Gdx;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.CopyOnWriteArrayList;

public class PacketManager extends IThread {

    public static CopyOnWriteArrayList<Object> queue = new CopyOnWriteArrayList<>();

    public PacketManager(){
        super("Packet Manager", () -> {
            if(queue.size() > 0 && !Control.isPaused()) {
                    for (Object o : PacketManager.queue) {
                        PacketManager.packetManage(o);
                        queue.remove(o);
                    }
                Gdx.graphics.requestRendering();
            }
        });
    }



    public static void packetManage(Object o) {
        try {
            if(o instanceof Packet){
                ((Packet) o).getContents();
            }
        }catch (Exception e2){
            StringWriter sw = new StringWriter();
            e2.printStackTrace(new PrintWriter(sw));
            System.out.println(sw + " PacketCode:" + ((Packet) o).t);
        }
    }
}
