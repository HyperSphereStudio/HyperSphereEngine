package Utilities;

import java.util.ArrayList;

public class RunList extends ArrayList<Runnable> implements Runnable {
    @Override
    public void run() {
        for (Runnable runner : this) {
            runner.run();
        }
    }
}