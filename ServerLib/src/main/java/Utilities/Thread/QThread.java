package Utilities.Thread;
import Manager.Control;
import java.util.ArrayList;

public class QThread extends HThread{
    public ArrayList<Object> QueueList = new ArrayList<>();
    public ArrayList<Object> MainList = new ArrayList<>();
    public ListReader listReader;

    public QThread(String name, float fps, Runnable runnable, ListReader lr) {
        super(name, fps, runnable);
        listReader = lr;
    }


    @Override
    public void run(){
        while(running) {
            try {
                update();
                MainList.addAll(QueueList);
                for (Object o : MainList) readMainList(o);
                MainList.clear();
                QueueList.clear();
                Thread.sleep(timePerUpdate);
            } catch (InterruptedException e) {
                Control.logError(e);
                System.out.print(" From:" + name);
            }
        }
        System.out.println("Utilities.Thread:" + name + " Stopped!");
    }

    private void readMainList(Object o){
        listReader.readList(o);
    }

    public void addToQueue(Object o){
        QueueList.add(o);
    }
}
