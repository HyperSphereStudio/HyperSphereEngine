package Utilities;

public class TryOrDefault{
    public static <T> T tryOrDefault(AttemptRun<T> attemptRun,T defaultResult, boolean writeError){
        try{
            return attemptRun.run();
        }catch (Exception e){
            if(writeError)
            e.printStackTrace();
            return defaultResult;
        }
    }
}
