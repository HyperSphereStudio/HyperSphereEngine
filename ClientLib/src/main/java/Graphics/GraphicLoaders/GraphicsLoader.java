package Graphics.GraphicLoaders;
import com.badlogic.gdx.Gdx;
import java.util.ArrayList;

public class GraphicsLoader {
    public static ArrayList<AbstractGraphicLoader> graphics = new ArrayList<>();
    public static int currentNumber, maxNumber;
    public static boolean complete;

    public static void dispose(){
        complete = true;
        graphics = null;
    }

    public static void checkForUnLoadedGraphics() {
        if(!complete){
            for(AbstractGraphicLoader abstractGraphicLoader : graphics){
                if(abstractGraphicLoader.checkStorage()){
                    Gdx.graphics.requestRendering();
                    break;
                }
            }
        }
    }

}
