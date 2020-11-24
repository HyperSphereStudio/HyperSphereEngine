package Utilities.Math;

import Constants.Const;
import org.apache.commons.math3.util.FastMath;

public class UNIJMath {

    public static double distance(float x, float y, float x2, float y2){
        return Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2));
    }

    public static short findShortestDirection(float positionX, float positionY, float slopeX, float slopeY, float positionX2, float positionY2, Conditional conditional){
        short direction = -1;
        double shortDistance = Integer.MAX_VALUE;
        double useable;
        if(!(distance(positionX, 0, positionX2, 0) <= slopeX && distance(0, positionY, 0, positionY2) <= slopeY)) {
            if (conditional.If(positionX + slopeX, positionY + slopeY)) {
                useable = distance(positionX + slopeX, positionY + slopeY, positionX2, positionY2);
                if (useable < shortDistance) {
                    shortDistance = useable;
                    direction = Const.Direction_NE;
                }
            }
            if (conditional.If(positionX + slopeX, positionY - slopeY)) {
                useable = distance(positionX + slopeX, positionY - slopeY, positionX2, positionY2);
                if (useable < shortDistance) {
                    shortDistance = useable;
                    direction = Const.Direction_SE;
                }
            }
            if (conditional.If(positionX - slopeX, positionY - slopeY)) {
                useable = distance(positionX - slopeX, positionY - slopeY, positionX2, positionY2);
                if (useable < shortDistance) {
                    shortDistance = useable;
                    direction = Const.Direction_SW;
                }
            }
            if (conditional.If(positionX - slopeX, positionY + slopeY)) {
                useable = distance(positionX - slopeX, positionY + slopeY, positionX2, positionY2);
                if (useable < shortDistance) {
                    shortDistance = useable;
                    direction = Const.Direction_NW;
                }
            }
            if (conditional.If(positionX + slopeX, positionY)) {
                useable = distance(positionX + slopeX, positionY, positionX2, positionY2);
                if (useable < shortDistance) {
                    shortDistance = useable;
                    direction = Const.Direction_E;
                }
            }
            if (conditional.If(positionX - slopeX, positionY)) {
                useable = distance(positionX - slopeX, positionY, positionX2, positionY2);
                if (useable < shortDistance) {
                    shortDistance = useable;
                    direction = Const.Direction_W;
                }
            }
            if (conditional.If(positionX, positionY + slopeY)) {
                useable = distance(positionX, positionY + slopeY, positionX2, positionY2);
                if (useable < shortDistance) {
                    shortDistance = useable;
                    direction = Const.Direction_N;
                }
            }
            if (conditional.If(positionX, positionY - slopeY)) {
                useable = distance(positionX, positionY - slopeY, positionX2, positionY2);
                if (useable < shortDistance) {
                    shortDistance = useable;
                    direction = Const.Direction_S;
                }
            }
        }
        return direction;
    }

    public static short getShortestDirection(float x, float y, float x2, float y2) {
        double angle = FastMath.atan2((y - y2),  (x - x2));

        if(angle < 0) angle += 2 * Math.PI;
        if(angle <= 0.3927 && angle >= -0.3927){
            return Const.Direction_W;
        }
        if(angle <= 1.178 && angle >= .3927){
            return Const.Direction_SW;
        }
        if(angle <= 1.964 && angle >= 1.178){
            return Const.Direction_S;
        }
        if(angle <= 2.749 && angle >= 1.964){
            return Const.Direction_SE;
        }
        if(angle <= 3.534 && angle >= 2.749){
            return Const.Direction_E;
        }
        if(angle <= 4.3197 && angle >= 3.534){
            return Const.Direction_NE;
        }
        if(angle <= 5.11 && angle >= 4.3197){
            return Const.Direction_N;
        }
        return Const.Direction_NW;
    }

    public static short getShortestDirection(float x, float y, float x2, float y2, float additional) {
        double angle = FastMath.atan2((y - y2),  (x - x2));

        if(angle < 0) angle += 2 * Math.PI;

        angle += additional;

        if(angle <= 0.3927 && angle >= -0.3927){
            return Const.Direction_W;
        }
        if(angle <= 1.178 && angle >= .3927){
            return Const.Direction_SW;
        }
        if(angle <= 1.964 && angle >= 1.178){
            return Const.Direction_S;
        }
        if(angle <= 2.749 && angle >= 1.964){
            return Const.Direction_SE;
        }
        if(angle <= 3.534 && angle >= 2.749){
            return Const.Direction_E;
        }
        if(angle <= 4.3197 && angle >= 3.534){
            return Const.Direction_NE;
        }
        if(angle <= 5.11 && angle >= 4.3197){
            return Const.Direction_N;
        }
        return Const.Direction_NW;
    }

}


