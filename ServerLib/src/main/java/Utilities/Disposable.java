package Utilities;


import Utilities.Thread.HThread;

import java.util.concurrent.CopyOnWriteArrayList;

public class Disposable<T> {
    
    public T o;
    public long created, destroyIN;
    public static CopyOnWriteArrayList<Disposable> disposables = new CopyOnWriteArrayList<>();
    public Runnable runnable;
    
    public Disposable(T o, long destroyIn, Runnable onDispose){
        this.o = o;
        this.created = System.currentTimeMillis();
        this.runnable = onDispose;
        this.destroyIN = destroyIn;
        disposables.add(this);
    }
    
    public void dispose(){
        if(System.currentTimeMillis() - created > destroyIN){
            disposables.remove(this);
            runnable.run();
        }
    }

    public T getData(){
        return o;
    }
    
    public static void startGarbageCollector(){
        new HThread("Garbage Collector", .1f, () -> {
            for(Disposable disposable : disposables){
                disposable.dispose();
            }
        }).start();
    }
    
    
}