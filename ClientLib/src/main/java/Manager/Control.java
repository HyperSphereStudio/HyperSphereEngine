package Manager;

import Connection.ConnectionManager;
import Connection.Objects.Packet;
import Connection.Reader.PacketManager;
import FXManager.SoundManager;
import Graphics.Components.AbstractGraphic;
import Graphics.Components.Frame;
import Graphics.Components.Frameable;
import Graphics.TextureManager;
import Input.Input;
import States.State;
import States.SubState;
import Utils.DeviceManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import Deprecated.Graphics.JShapes;
import com.badlogic.gdx.math.Rectangle;
import com.esotericsoftware.kryonet.Connection;
import com.hyperspherestudio.kryonet.HyperSphereClient;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.gl;

//Author Johnathan Bizzano

public abstract class Control extends ApplicationAdapter {
    private static SpriteBatch batch;
    private static ShapeRenderer shapeRenderer;
    private static GlyphLayout layout;
    private static boolean initalized;
    public static float nonSquareScaleX, nonSquareScaleY, SquareScaleMin, SquareScaleMax, Width, Height, Tile, fontScaler, cameraX, cameraY, widthAspect, heightAspect;
    private static boolean portrait;
    private static ClientSettings clientSettings;
    private static DeviceManager deviceManager;
    private static ShaderProgram shaderProgram;
    private static final Object layoutLock = new Object();
    private static boolean paused;
    private static HyperSphereClient hyperSphereClient;
    private static PacketManager packetManager;
    public static Control control;
    private static ArrayList<Class> classes = new ArrayList<>();

    public Control(DeviceManager deviceManager, ClientSettings clientSettings1, ShaderProgram shaderProgram) {
        super.create();
        adjustSize();
        System.out.println("Initializing HyperSphereEngine (Client) created by Johnathan Bizzano!");
        control = this;
        if (initalized) throw new RuntimeException("Cannot Initialize More Then 1 Control!");
        initalized = true;
        Control.shaderProgram = shaderProgram;
        clientSettings = clientSettings1;
        shapeRenderer = new ShapeRenderer(5000);
        shapeRenderer.setAutoShapeType(true);
        Control.deviceManager = deviceManager;
        if (shaderProgram != null)
            batch = new SpriteBatch(1000, shaderProgram);
        else batch = new SpriteBatch(1000);
        layout = new GlyphLayout();
        clientSettings.initializationThread();
        Gdx.input.setInputProcessor(new Input());
        onCreate();
        System.out.println("Initialized!");
    }

    public Control(DeviceManager deviceManager, ClientSettings clientSettings) {
        this(deviceManager, clientSettings, null);
    }

    public static void handleException(Exception e) {
        clientSettings.handleException(e);
    }

    @Override
    public void resize(int width, int height) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        shapeRenderer.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        shapeRenderer.updateMatrices();
        adjustSize();
        JShapes.fontHolder.iterateFastSingle(entry -> entry.getValue().dispose());
        JShapes.fontHolder.clear();

        if (State.getState() != null)
            State.getState().resize();

        if (SubState.getState() != null)
            SubState.getState().resize();

        System.out.println("Resized:" + width + "x" + height);
        onResize(width, height);
        Gdx.graphics.requestRendering();
    }

    private void adjustSize(){
        Width = (float) Gdx.graphics.getWidth();
        Height = (float) Gdx.graphics.getHeight();
        nonSquareScaleX = Width / 10;
        nonSquareScaleY = Height / 10;
        SquareScaleMin = Math.min(Width, Height) / (10);
        SquareScaleMax = Math.max(Width, Height) / 10;

        if (Width > Height) {
            cameraY = 0;
            cameraX = (Width - Height) / 2;
            portrait = false;
            heightAspect = Height / Width;
            widthAspect = 1;
        } else {
            heightAspect = 1;
            widthAspect = Width / Height;
            cameraY = (Height - Width) / 2;
            cameraX = 0;
            portrait = true;
        }

        Tile = Math.min(Width, Height) / (GAME_UNIT() * TILE_SIZE());
        fontScaler = (float) (Math.sqrt(Height * Width) / 1000);
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static int TILE_SIZE() {
        return clientSettings.TILESIZE();
    }

    public static int GAME_UNIT() {
        return clientSettings.GAMEUNIT();
    }

    public static ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public static GlyphLayout getLayout() {
        return layout;
    }

    public static boolean isPortrait() {
        return portrait;
    }

    public static void registerClass(Class clzz) {
        classes.add(clzz);
    }

    public static ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public static void setShaderProgram(ShaderProgram shaderProgram) {
        Control.shaderProgram = shaderProgram;
    }

    public static TextureRegion getDefaultTextureRegion(String name) {
        return clientSettings.defaultGetRegion(name);
    }

    public static int getMaxGameCoordX() {
        return clientSettings.maxGameCoordX();
    }

    public static AbstractGraphic getDefaultGraphic(String name) {
        return clientSettings.defaultGetGraphic(name);
    }

    public static int getMaxGameCoordY() {
        return clientSettings.maxGameCoordY();
    }

    public static Object getLayoutLock() {
        return layoutLock;
    }

    public static byte[] getSecret16() {
        return clientSettings.getSecret16();
    }

    public static float getDefaultTransparency() {
        return clientSettings.getDefaultTransparency();
    }

    public static float getVolume() {
        return clientSettings.getVolume();
    }

    public static boolean playMusic() {
        return clientSettings.playMusic();
    }

    public static boolean playSound() {
        return clientSettings.playSound();
    }

    public static DeviceManager getDeviceManager() {
        return deviceManager;
    }

    public static ClientSettings getClientSettings() {
        return clientSettings;
    }

    @Override
    public void pause() {
        paused = true;
        onPause();
    }

    @Override
    public void resume() {
        paused = false;
        onResume();
    }


    @Override
    public void render() {
        try {
            gl.glClearColor(0, 0, 0, 0);
            gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


            if(State.getState() != null)
            State.getState().render();

            onRender();
            JShapes.setMode(Const.Graphic_NONE);
        } catch (Exception e2) {
            StringWriter sw = new StringWriter();
            e2.printStackTrace(new PrintWriter(sw));
            deviceManager.sendAnalytic(Const.Analytics_ERROR, sw.toString());
        }
    }

    public static void setClientSettings(ClientSettings clientSettings) {
        Control.clientSettings = clientSettings;
    }


    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        TextureManager.dispose();
        getShaderProgram().dispose();
        JShapes.fontHolder.iterateFastSingle(entry -> entry.getValue().dispose());
        SoundManager.dispose();
        ConnectionManager.stop();
        onDispose();
    }

    public static boolean isPaused() {
        return paused;
    }

    public static void sendTCP(Object o) {
        hyperSphereClient.sendTCP(o);
    }

    public static void sendUDP(Object o) {
        hyperSphereClient.sendUDP(o);
    }

    public static HyperSphereClient getHyperSphereClient() {
        return hyperSphereClient;
    }

    public static void setHyperSphereClient(HyperSphereClient hyperSphereClient) {
        Control.hyperSphereClient = hyperSphereClient;
    }

    public static ArrayList<Class> getRegisteredClasses() {
        return classes;
    }


    public static void startClient() {
        ConnectionManager.start();
    }

    public static void stopClient() {
        ConnectionManager.stop();
    }

    public static boolean isConnected() {
        if (hyperSphereClient == null) return false;
        return hyperSphereClient.isConnected();
    }

    public static void setPacketManager(PacketManager packetManager) {
        Control.packetManager = packetManager;
    }

    public static PacketManager getPacketManager() {
        return packetManager;
    }

    public abstract void onCreate();

    public abstract void onRender();

    public abstract void onPause();

    public abstract void onResume();

    public abstract void onDispose();

    public abstract void onResize(int x, int y);

    public abstract void onReceived(Connection c, Object o);

    public abstract void onConnected();

    public abstract void onDisconnection();

}
