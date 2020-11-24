package Utilities;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeKeeper {
    public static long  MAGIC = 86400000L;
    public static int currentDay = setCurrentDate();
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
    public static SimpleDateFormat day = new SimpleDateFormat("MMM/dd/yyyy");
    public static SimpleDateFormat logTime = new SimpleDateFormat("HH:mm:ss");

    public static String time(){
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }

    public static int setCurrentDate (){
        currentDay = (int) (new Date(System.currentTimeMillis()).getTime()/MAGIC);
        return currentDay;
    }

    public static String timeForLog(){
        return logTime.format(new Date(System.currentTimeMillis()));
    }


    public static String time(long time){
        return dateFormat.format(new Date(time));
    }

    public static String getCurrentDate(){
        return day.format(new Date(((long) currentDay) * MAGIC));
    }
}
