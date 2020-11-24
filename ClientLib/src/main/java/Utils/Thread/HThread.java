package Utils.Thread;

import IteratorWrappers.ObjObj.JObjObjList;

public class HThread implements Runnable{
    public static JObjObjList<String, HThread> threadList = new JObjObjList<>();
    public Thread thread;
    public String name;
    public boolean running = true;
    public long timePerUpdate;
    public Runnable runnable;
    public float fps;

    public HThread(String name, float fps, Runnable runnable){
        if(fps <= 1000) {
            this.name = name;
            this.fps = fps;
            this.runnable = runnable;
            this.timePerUpdate = (long) (1000 / fps);
            start();
        }else throw new RuntimeException("Thread fps to fast!! Must be less than or equal to 1000");
    }

    public void start(){
        if(!threadList.containsKey(name)) {
            thread = new Thread(this, name);
            thread.start();
            threadList.put(name, this);
            System.out.println("Thread Started: " + name + " Total Thread Count:" + threadList.size());
        }
    }

    public void pause(){
        timePerUpdate = 1000000;
    }

    public void resume(){
        timePerUpdate = (long) (1000 / fps);
    }

    public void stop(){
        threadList.remove(this.name);
        this.running = false;
    }

    public void run(){
        while(running) {
            try {
                update();
                Thread.sleep(timePerUpdate);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.print(" From:" + name);
            }
        }
        System.out.println("Thread:" + name + " Stopped!");
    }

    public void update(){
        runnable.run();
    }

}
