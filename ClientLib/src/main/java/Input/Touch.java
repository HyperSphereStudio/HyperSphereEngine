package Input;
import Deprecated.Graphics.JShapes;
import FXManager.SoundManager;
import FXManager.VibrationManager;
import Manager.Const;
import Manager.Control;
import Utils.UNIJMath;
import com.badlogic.gdx.graphics.Color;

public class Touch {

    float x, y, width, height, baseX, baseY, baseWidth, baseHeight;
    private boolean drag;
    private static boolean touched, dragged;
    private short type;
    private String sound;
    private boolean vibrate;

    public Touch(float x, float y, float width, float height, short Type, boolean drag) {
        this.drag = drag;
        this.baseX = x;
        this.baseY = y;
        this.baseWidth = width;
        this.baseHeight = height;
        this.type = Type;
        update(baseX, baseY, baseWidth, baseHeight);
    }

    public void setEffects(String sound, boolean vibrate){
        this.sound = sound;
        this.vibrate = vibrate;
    }

    public Touch setDefaultEffects(){
        this.sound = "interface3";
        this.vibrate = true;
        return this;
    }

    public static void playGUIDefault(){
        SoundManager.playMusic("interface3",false);
        VibrationManager.vibrate(30);
    }

    public void draw(){
        JShapes.filledRect(baseX, baseY, baseWidth, baseHeight, Color.RED, type);
    }

    public boolean Touched() {
        if (!drag) {
            if (!touched) {
                touched = (Input.floatX >= x && x + width >= Input.floatX && y <= Input.floatY && y + height >= Input.floatY);
                if(touched){
                    if(sound != null){
                        SoundManager.playMusic(sound, false);
                    }
                    if(vibrate){
                        VibrationManager.vibrate(30);
                    }
                }
                return touched;
            }
        } else {
            if (!dragged) {
                dragged = (Input.dragFloatX >= x && x + width >= Input.dragFloatX && y <= Input.dragFloatY && y + height >= Input.dragFloatY);
                return dragged;
            }
        }
        return false;
    }


    public static void reset(boolean drag) {
        if (!drag) {
            touched = false;
        } else {
            dragged = false;
        }
    }

    public void update(float x, float y, float width, float height) {
        this.baseX = x;
        this.baseY = y;
        this.baseWidth = width;
        this.baseHeight = height;
        switch (type) {
            case Const.Graphic_NONSQUARE:
                x *= Control.nonSquareScaleX;
                y *= Control.nonSquareScaleY;
                width *= Control.nonSquareScaleX;
                height *= Control.nonSquareScaleY;
                break;
            case Const.Graphic_TILE:
                x *= Control.Tile;
                y *= Control.Tile;
                width *= Control.Tile;
                height *= Control.Tile;
                break;
            case Const.Graphic_SQUAREMIN:
                x *= Control.nonSquareScaleX;
                y *= Control.nonSquareScaleY;
                width *= Control.SquareScaleMin;
                height *= Control.SquareScaleMin;
                break;
            case Const.Graphic_SQUAREMAX:
                x *= Control.nonSquareScaleX;
                y *= Control.nonSquareScaleY;
                width *= Control.SquareScaleMax;
                height *= Control.SquareScaleMax;
                break;
            case Const.Graphic_GUI:
                width *= Control.SquareScaleMin;
                height *= Control.SquareScaleMin;
                x = UNIJMath.getGUIX(x, width);
                y = UNIJMath.getGUIY(y, height);
                break;
            case Const.Graphic_MULTI:
                x *= Control.SquareScaleMin;
                y *= Control.SquareScaleMin;
                width *= Control.SquareScaleMin;
                height *= Control.SquareScaleMin;
                break;
            case Const.Graphic_DISPLAY:
                x = UNIJMath.getGraphicX(x);
                y = UNIJMath.getGraphicY(y);
                width *= Control.SquareScaleMin;
                height *= Control.SquareScaleMin;
                break;
            default:
                throw new RuntimeException("Invalid Type!!");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public void resize(){
        update(baseX, baseY, baseWidth, baseHeight);
    }

}
