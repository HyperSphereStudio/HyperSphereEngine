package Graphics.GraphicLoaders;
import Graphics.Components.AbstractGraphic;
import Graphics.TextureManager;
import Utils.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;
import IteratorWrappers.ObjObj.JObjObjList;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class StringNormalNest extends AbstractGraphicLoader<JObjObjList<String, AbstractGraphic>, JObjObjList<Pixmap, JObjObjList<String, int[]>>>{

    private static StringNormalNest stringNormalNest = new StringNormalNest();

    public StringNormalNest(){

    }

    public static void loadTexture(JObjObjList<String, AbstractGraphic> output, FileHandle jsonHandle, String texturePath, String packFileName, Runnable onCompletion){
        JObjObjList<Pixmap, JObjObjList<String, int[]>> list = new JObjObjList<Pixmap, JObjObjList<String, int[]>>().enableStorage(true);
        int i = 0;
        JsonNode textureData = Util.readJson(Util.readCryptoFile(jsonHandle, true));
        while (textureData.hasNonNull(String.valueOf(i))) {
            String[] array = Util.tokenize(textureData.path(String.valueOf(i)).asText(), ':');
            JObjObjList<String, int[]> gui = new JObjObjList<>();
            for (int v = 0; v < array.length / 5; ++v) {
                gui.put(array[v * 5], new int[]{Integer.parseInt(array[v * 5 + 1]), Integer.parseInt(array[v * 5 + 2]), Integer.parseInt(array[v * 5 + 3]), Integer.parseInt(array[v * 5 + 4])});
            }
            String id;
            if (i == 0) id = "";
            else id = String.valueOf(i + 1);
            list.put(Util.readEncryptedImage(Gdx.files.internal(texturePath + "/" + packFileName + id + ".png")), gui);
            ++i;
        }
        GraphicsLoader.maxNumber += list.size();
        stringNormalNest.storage.add(new GraphicPackage<>(output, list, onCompletion));
    }


    @Override
    public boolean dealWithPackageRemoveUponCompletion(GraphicPackage<JObjObjList<String, AbstractGraphic>, JObjObjList<Pixmap, JObjObjList<String, int[]>>> Package) {
        int i = 0;
        Iterator<Object2ObjectMap.Entry<Pixmap, JObjObjList<String, int[]>>> iterator = Package.input.object2ObjectEntrySet().fastIterator();

        while(iterator.hasNext() && i++ == 0){
            Object2ObjectMap.Entry<Pixmap, JObjObjList<String, int[]>> entry = iterator.next();
            int texture = TextureManager.load(new Texture(entry.getKey()));
            for (Object2ObjectOpenHashMap.Entry<String, int[]> entry2 : entry.getValue().object2ObjectEntrySet()) {
                Package.result.put(entry2.getKey(), new AbstractGraphic(new TextureRegion(TextureManager.get(texture), entry2.getValue()[0], entry2.getValue()[1], entry2.getValue()[2], entry2.getValue()[3])));
            }
            Package.input.remove(entry.getKey());
            GraphicsLoader.currentNumber++;
            if (Package.input.size() == 0){
                Package.runnable.run();
                return true;
            }
        }

        return false;
    }
}
