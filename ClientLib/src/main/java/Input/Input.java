package Input;
import Graphics.GameCamera;
import Manager.Const;
import Manager.Control;
import States.State;
import Utils.StringManager;
import Utils.UNIJMath;
import com.badlogic.gdx.InputProcessor;


public class Input implements InputProcessor {
    public static boolean Touched = false;
    public static float floatX = 1.5f, floatY = 1.5f, dragFloatX, dragFloatY;
    public static String string = "";
    public static boolean[] keyList = new boolean[256];
    private static boolean keyDownLock;

    public Input(){

    }

    @Override
    public boolean keyDown(int keycode) {
        keyDownLock = isFunction(keycode);
        keyList[keycode] = true;
        if(keyDownLock) {
            if (com.badlogic.gdx.Input.Keys.toString(keycode).length() > 1)
                State.getState().keyPressed(keycode);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyList[keycode] = false;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if(!keyDownLock) {
            if (StringManager.validStringforChat(String.valueOf(character)))
                string += String.valueOf(character);
            State.getState().typed(character);
            string = "";
        }else{
            keyDownLock = false;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        floatX = (float) screenX;
        floatY = Control.Height - screenY;
        Touched = true;
        State.getState().touch((int) floatX, (int) floatY);
        Touch.reset(false);
        return true;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        State.getState().touchedRemoved();
        Touched = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        dragFloatX = (float) screenX;
        dragFloatY = Control.Height - screenY;
        State.getState().drag((int) dragFloatX, (int) dragFloatY);
        Touch.reset(true);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public static boolean touchedAtFloat(float x, float y, float width, float height, int type) {
        switch (type) {
            case Const.Graphic_NONSQUARE:
                x *= Control.nonSquareScaleX;
                y *= Control.nonSquareScaleY;
                width *= Control.nonSquareScaleX;
                height *= Control.nonSquareScaleY;
                break;
            case Const.Graphic_GUI:
                x = UNIJMath.getGUIX(x, width);
                y = UNIJMath.getGUIY(y, height);
                width *= Control.SquareScaleMin;
                height *= Control.SquareScaleMin;
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
        }
        return dragFloatX >= x && x + width >= dragFloatX && y <= dragFloatY && y + height >= dragFloatY;
    }

    public static boolean touchedAt(float x, float y, float width, float height, int type) {
        if (!Touched) return false;
        switch (type) {
            case Const.Graphic_NONSQUARE:
                x *= Control.nonSquareScaleX;
                y *= Control.nonSquareScaleY;
                width *= Control.nonSquareScaleX;
                height *= Control.nonSquareScaleY;
                break;
            case Const.Graphic_GUI:
                x = UNIJMath.getGUIX(x, width);
                y = UNIJMath.getGUIY(y, height);
                width *= Control.SquareScaleMin;
                height *= Control.SquareScaleMin;
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
        }
        return floatX >= x && x + width >= floatX && y <= floatY && y + height >= floatY;
    }

    private static boolean joyTouch(float floatX, float floatY) {
        return floatX >= .25f * Control.SquareScaleMin && .25f * Control.SquareScaleMin + 3 * Control.SquareScaleMin >= floatX && .25f * Control.SquareScaleMin <= floatY && .25f * Control.SquareScaleMin + 3 * Control.SquareScaleMin >= floatY;
    }

    public static int getX() {
        return Control.TILE_SIZE() * ((int) (((((floatX / (Control.Tile * Control.TILE_SIZE()))) + GameCamera.getxOffset() / 37))));
    }

    public static int getY() {
        return Control.TILE_SIZE() * ((int) (((((floatY / (Control.Tile * Control.TILE_SIZE()))) + GameCamera.getyOffset() / 37))));
    }

    public static float getFloatXTile() {
        return Control.TILE_SIZE() * ((((((floatX / (Control.Tile * Control.TILE_SIZE()))) + GameCamera.getxOffset() / 37f))));
    }

    public static float getFloatYTile() {
        return Control.TILE_SIZE() * ((((((floatY / (Control.Tile * Control.TILE_SIZE()))) + GameCamera.getyOffset() / 37f))));
    }

    public static int getDragX() {
        return Control.TILE_SIZE() * ((int) (((((dragFloatX / (Control.Tile * Control.TILE_SIZE()))) + GameCamera.getxOffset() / 37))));
    }

    public static int getDragY() {
        return Control.TILE_SIZE() * ((int) (((((dragFloatY / (Control.Tile * Control.TILE_SIZE()))) + GameCamera.getyOffset() / 37))));
    }


    public static boolean isFunction(int code){
        switch (code){
            case com.badlogic.gdx.Input.Keys.DEL:
            case com.badlogic.gdx.Input.Keys.BACK:
            case com.badlogic.gdx.Input.Keys.HOME:
            case com.badlogic.gdx.Input.Keys.ESCAPE:
            case com.badlogic.gdx.Input.Keys.ENTER:
                return true;
        }
        return false;
    }

}
