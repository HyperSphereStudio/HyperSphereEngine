package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeKeeper {
    public static String lastTime;

    public static String time(){
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(yourmilliseconds);
        return sdf.format(resultdate);
    }

    public static int find1SecondMillis(){
        return (int) (System.currentTimeMillis() % 1000);
    }
}
