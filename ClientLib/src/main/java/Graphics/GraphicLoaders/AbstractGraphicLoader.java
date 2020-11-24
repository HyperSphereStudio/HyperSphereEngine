package Graphics.GraphicLoaders;

import java.util.ArrayList;

public abstract class AbstractGraphicLoader<Result, Input> {
    public ArrayList<GraphicPackage<Result, Input>> storage = new ArrayList<>();

    public AbstractGraphicLoader(){
        GraphicsLoader.graphics.add(this);
    }


    public boolean checkStorage(){
        if(storage.size() > 0){
            if(dealWithPackageRemoveUponCompletion(storage.get(0)))storage.remove(0);
            return true;
        }
        return false;
    }

    public abstract boolean dealWithPackageRemoveUponCompletion(GraphicPackage<Result, Input> Package);
}
