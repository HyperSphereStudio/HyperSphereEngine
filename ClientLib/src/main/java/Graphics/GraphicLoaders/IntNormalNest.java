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
import IteratorWrappers.PObj.JIntObjList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;

public class IntNormalNest extends AbstractGraphicLoader<JIntObjList<AbstractGraphic>, JObjObjList<Pixmap, JIntObjList<int[]>>>{

    private static IntNormalNest intNormalNest = new IntNormalNest();

    public IntNormalNest(){

    }

    public static void loadTexture(JIntObjList<AbstractGraphic> resultList, FileHandle jsonHandle, String texturePath, String packFileName, Runnable onCompletion){
        int i = 0;
        JObjObjList<Pixmap, JIntObjList<int[]>> list = new JObjObjList<Pixmap, JIntObjList<int[]>>().enableStorage(true);
        String[] stringTokenizer;
        JsonNode jsonNode = Util.readJson(Util.readCryptoFile(jsonHandle, true));
        while (jsonNode.hasNonNull(String.valueOf(i))) {
            JIntObjList<int[]> lister = new JIntObjList<>();
            stringTokenizer = Util.tokenize(jsonNode.path(String.valueOf(i)).asText(), ':');
            for (int v = 0; v < stringTokenizer.length / 5; ++v) {
                lister.put(Integer.parseInt(stringTokenizer[v * 5]), new int[]{Integer.parseInt(stringTokenizer[v * 5 + 1]), Integer.parseInt(stringTokenizer[v * 5 + 2]), Integer.parseInt(stringTokenizer[v * 5 + 3]), Integer.parseInt(stringTokenizer[v * 5 + 4])});
            }
            String id;
            if (i == 0) id = "";
            else id = String.valueOf(i + 1);
            list.put(Util.readEncryptedImage(Gdx.files.internal(texturePath + "/" + packFileName + id + ".png")), lister);
            ++i;
        }
        GraphicsLoader.maxNumber += list.size();
        intNormalNest.storage.add(new GraphicPackage<>(resultList, list, onCompletion));
    }


    @Override
    public boolean dealWithPackageRemoveUponCompletion(GraphicPackage<JIntObjList<AbstractGraphic>, JObjObjList<Pixmap, JIntObjList<int[]>>> Package) {
        int i = 0;
        Iterator<Object2ObjectMap.Entry<Pixmap, JIntObjList<int[]>>> iterator = Package.input.object2ObjectEntrySet().fastIterator();
        while(iterator.hasNext() && i++ == 0){
            Object2ObjectMap.Entry<Pixmap, JIntObjList<int[]>> entry = iterator.next();
            int texture = TextureManager.load(new Texture(entry.getKey()));
            for (Int2ObjectMap.Entry<int[]> entry2 : entry.getValue().int2ObjectEntrySet()) {
                Package.result.put(entry2.getIntKey(), new AbstractGraphic(new TextureRegion(TextureManager.get(texture), entry2.getValue()[0], entry2.getValue()[1], entry2.getValue()[2], entry2.getValue()[3])));
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
