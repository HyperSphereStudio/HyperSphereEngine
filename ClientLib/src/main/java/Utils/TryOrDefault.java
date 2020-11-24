package Utils;

public class TryOrDefault<MainClass> {

    public TryOrDefault(){

    }

    public MainClass tryOrDefault(AttemptRun<MainClass> attemptRun, MainClass defaultResult, boolean writeError){
        try{
            return attemptRun.run();
        }catch (Exception e){
            if(writeError)
            e.printStackTrace();
            return defaultResult;
        }
    }
}
