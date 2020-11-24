package com.HyperSphere.Assets;
import Graphics.GraphicLoaders.GraphicsLoader;

public class AssetLoader {

    public void run() {

    }

    void convertPixmaps() {
        GraphicsLoader.checkForUnLoadedGraphics();
    }

    AssetLoader() {
        new Thread(this::run).start();
    }

}
