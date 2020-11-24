package FXManager;
import Stored.PreferenceList;
import com.badlogic.gdx.Gdx;

public class VibrationManager {

    public static void vibrate(int time){
        if(PreferenceList.Vibrate.data){
            Gdx.input.vibrate(time);
        }
    }
}
