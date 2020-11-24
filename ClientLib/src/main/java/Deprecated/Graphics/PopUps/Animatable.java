package Deprecated.Graphics.PopUps;
import Deprecated.Graphics.Dep2Frame;
import Deprecated.Graphics.JShapes;
import Manager.Const;

public class Animatable extends Dep2Frame {
    public boolean isGUI;
    public String name, beginChar, endChar;
    public int maxSize, GameAssetStart, GameAssetEnd, counter = 0;
    private float speed;
    public static boolean animating;
    public float x, y, w, h;
    public int startVal;

    public Animatable(int maxSize, float speed, float x, float y, float w, float h, int startVal){
        isGUI = true;
        this.maxSize = maxSize;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        counter = startVal;
        this.startVal = startVal;
    }

    public Animatable start(String GUIName, String beginChar, String endChar){
        this.name = GUIName;
        this.beginChar = beginChar;
        this.endChar = endChar;
        setVisible(true);
        return this;
    }

    public Animatable start(int GameAssetStart, int GameAssetEnd){
        this.GameAssetStart = GameAssetStart;
        this.GameAssetEnd = GameAssetEnd;
        setVisible(true);
        return this;
    }

    public Animatable(float speed, float x, float y, float w, float h, int startVal){
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.counter = startVal;
        this.startVal = startVal;
    }

    @Override
    public void update(){
        animating = true;
        if(isGUI){
            if(counter < maxSize)++counter;
            else counter = startVal;
        }else{
            if(counter < GameAssetEnd - GameAssetStart)++counter;
            else counter = startVal;
        }
    }

    @Override
    public void render(float... metaData) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void renderInitialize() {

    }

    @Override
    public void touch(int x, int y) {

    }

    @Override
    public void typed(char c) {

    }

    @Override
    public void keyPressed(int code) {

    }

    @Override
    public void touchedRemoved() {

    }

    @Override
    public void release() {

    }

    @Override
    public void resize() {

    }

    @Override
    public void drag(int x, int y) {

    }


    public void render(){
        JShapes.setMode(Const.Graphic_BATCH);

    }


}
