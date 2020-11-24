package Graphics;

import Manager.Control;
import Utils.UNIJMath;

public class GameCamera {
    private static float xOffset = 0, miniMapXOffset = 0, miniMapResultantOffsetX = 0;
    private static float yOffset = 0, miniMapYOffset = 0, miniMapResultantOffsetY = 0;


    public GameCamera(float posX, float posY) {
        Center(posX, posY);
    }

    public static void Center(float posX, float posY) {
        xOffset = Math.max(0, Math.min(Control.getMaxGameCoordX() - Control.Width / (Control.Tile), posX - Control.Width / (2 * Control.Tile)));
        yOffset = Math.max(0, Math.min(Control.getMaxGameCoordY()  - Control.Height / (Control.Tile), posY - Control.Height / (2 * Control.Tile)));
        miniMapResultantOffsetX = (UNIJMath.getGUIX(7.5f, 2.5f)  + 1.25f * Control.SquareScaleMin);
        miniMapResultantOffsetY = (UNIJMath.getGUIY(7.5f, 2.5f) + 1.25f * Control.SquareScaleMin);
        miniMapXOffset = posX / 16f - miniMapResultantOffsetX / Control.Tile;
        miniMapYOffset = posY / 16f - miniMapResultantOffsetY / Control.Tile;
    }

    public static float getxOffset() {
        return xOffset;
    }

    public static float getyOffset() {
        return yOffset;
    }


    public static float getMiniMapXOffset(){
        return miniMapXOffset;
    }

    public static float getMiniMapYOffset(){
        return miniMapYOffset;
    }

    public static float getMiniMapResultantOffsetX(){
        return miniMapResultantOffsetX;
    }

    public static float getMiniMapResultantOffsetY(){
        return miniMapResultantOffsetY;
    }
}

