package Graphics;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class TextureManager {
    public static ArrayList<Texture> TextureList = new ArrayList<>();

    public static int load(String path){
        TextureList.add(new Texture(Gdx.files.internal(path)));
        return TextureList.size() - 1;
    }

    public static int load(Texture texture){
        TextureList.add(texture);
        return TextureList.size() - 1;
    }

    public static Texture get(int num){
        return TextureList.get(num);
    }

    public static void dispose(){
        for(Texture texture : TextureList){
            texture.dispose();
        }
    }

}
