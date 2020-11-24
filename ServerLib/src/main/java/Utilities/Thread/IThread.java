package Utilities.Thread;

public class IThread extends HThread{

    public IThread(String name, Runnable runnable){
        super(name, .00001f, runnable);
    }

    @Override
    public void run(){
        while(running){
            try{
                Thread.sleep(timePerUpdate);
            }catch (InterruptedException ignored){
                update();
            }
        }
        System.out.println("Utilities.Thread:" + name + " Stopped!");
    }

    public void interrupt(){
        thread.interrupt();
    }

}
