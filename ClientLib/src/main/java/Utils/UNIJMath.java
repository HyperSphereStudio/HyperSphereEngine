package Utils;

import Manager.Const;
import Manager.Control;

public class UNIJMath {


    public static double distance(float x, float y, float x2, float y2) {
        return Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2));
    }

    public static float getGraphicX(float x) {
        return x * Control.SquareScaleMin + Control.cameraX;
    }

    public static float getGraphicY(float y) {
        return y * Control.SquareScaleMin + Control.cameraY;
    }

    public static float getGUIX(float sX, float sW) {
        if(sX + sW/2 > 5) {
            return Control.Width * ((sX + sW) / 10) - sW * Control.SquareScaleMin;
        }else{
            return Control.Width * (sX / 10);
        }
    }

    public static float getGUIY(float sY, float sH) {
        if(sY + sH/2 > 5){
            return Control.Height * ((sY + sH) / 10) - sH * Control.SquareScaleMin;
        }else{
            return Control.Height * ((sY) / 10);
        }
    }

    public static float getSquareCenteredX(float startPosX, float startWidth, float width){
        return xLogic(startPosX, startWidth * Control.SquareScaleMin, width * Control.SquareScaleMin);
    }

    public static float getSquareCenteredY(float startPosY, float startHeight, float height){
        return yLogic(startPosY, startHeight * Control.SquareScaleMin, height * Control.SquareScaleMin);
    }


    public static float getGUISquareX(float centerX, float width){
        return centerX * Control.Width - width * Control.SquareScaleMin;
    }

    public static float getGUISquareY(float centerY, float height){
        return centerY * Control.Height - height * Control.SquareScaleMin;
    }

    public static float xLogic(float startPosX, float startWidth, float width){
        return startPosX * Control.nonSquareScaleX + startWidth/2f - width /2f;
    }

    public static float yLogic(float startPosY, float startHeight, float height){
        return startPosY * Control.nonSquareScaleX + startHeight/2f - height /2f;
    }

    public static float getGraphicX(float x, float width, float height) {
        return (x + (width - height) / 2);
    }

    public static float getGraphicY(float y, float width, float height) {
        return (y + (height - width) / 2);
    }

    public static short getShortestDirection(float x, float y, float x2, float y2) {
        double angle = getAngle(x, y, x2, y2);
        if (angle < 0) angle += 2 * Math.PI;
        if (angle <= 0.3927 && angle >= -0.3927) {
            return Const.Direction_W;
        }
        if (angle <= 1.178 && angle >= .3927) {
            return Const.Direction_SW;
        }
        if (angle <= 1.964 && angle >= 1.178) {
            return Const.Direction_S;
        }
        if (angle <= 2.749 && angle >= 1.964) {
            return Const.Direction_SE;
        }
        if (angle <= 3.534 && angle >= 2.749) {
            return Const.Direction_E;
        }
        if (angle <= 4.3197 && angle >= 3.534) {
            return Const.Direction_NE;
        }
        if (angle <= 5.11 && angle >= 4.3197) {
            return Const.Direction_N;
        }
        return Const.Direction_NW;
    }

    public static double getAngle(float x, float y, float x2, float y2) {
        return Math.atan2((y - y2), (x - x2));
    }

}




