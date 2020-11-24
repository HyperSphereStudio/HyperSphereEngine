package Utils.Thread;

import java.util.ArrayList;
import java.util.List;

public class QThread extends HThread{
    public ArrayList<Object> QueueList = new ArrayList<>();
    public ArrayList<Object> MainList = new ArrayList<>();
    public ListReader listReader;

    public QThread(String name, float fps, Runnable runnable, ListReader listReader) {
        super(name, fps, runnable);
        this.listReader = listReader;
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
                e.printStackTrace();
                System.out.print(" From:" + name);
            }
        }
        System.out.println("Thread:" + name + " Stopped!");
    }

    private void readMainList(Object o){
        listReader.readList(o);
    }

    public void addToQueue(Object o){
        QueueList.add(o);
    }
}
