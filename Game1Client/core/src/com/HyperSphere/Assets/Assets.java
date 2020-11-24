package com.HyperSphere.Assets;
import com.HyperSphere.Manager.Controller;
import com.HyperSphere.Manager.Launcher;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import Manager.Const;
import Manager.Control;
import Utils.Secure.FileCrypto;
import Utils.Util;

public class Assets {

    public static boolean finishedBaseAsset = false;
    public static AssetLoader assetLoader;
    public static ShaderProgram shaderProgram;


    public static void partialLoading() {
        if (!finishedBaseAsset) {
            assetLoader.convertPixmaps();
            Gdx.graphics.requestRendering();
        }
    }

    public static void startLoading() {
        assetLoader = new AssetLoader();
    }

    public static ShaderProgram loadShader() {
        Control.setClientSettings(Controller.CSettings);
        ShaderProgram.pedantic = false;
        shaderProgram = new ShaderProgram(Util.readCryptoFile(Gdx.files.internal("shaders/colors.vsh"), true), Util.readCryptoFile(Gdx.files.internal("shaders/colors.fsh"), true));
        if (shaderProgram.isCompiled()) {
            System.out.println("Shader Compiled!");
        } else {
            Controller.getDeviceManager().sendAnalytic(Const.Analytics_ERROR, shaderProgram.getLog());
            System.out.println("Shader Failed!" + shaderProgram.getLog());
        }
        return shaderProgram;
    }

}
