package Deprecated.Graphics;
import Input.Input;
import Input.Touch;
import Graphics.Components.AbstractGraphic;
import Manager.Const;
import Manager.Control;
import Utils.UNIJMath;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

@Deprecated
public class PredeterminedGraphic extends AbstractGraphic {

    private float x, y, width, height;
    private float sX, sY, sW, sH;
    private short graphicType;

    public PredeterminedGraphic(AbstractGraphic graphic, float x, float y, float width, float height, short graphicType){
        this(graphic.textureRegion, graphic.matrix, x, y, width, height, graphicType);
    }

    public PredeterminedGraphic(TextureRegion textureRegion, float x, float y, float width, float height, short graphicType) {
        this(textureRegion, null, x, y, width, height, graphicType);
    }

    public PredeterminedGraphic(TextureRegion textureRegion, float[] matrix, float x, float y, float width, float height, short graphicType) {
        super(textureRegion, matrix);
        this.graphicType = graphicType;
        update(matrix, x, y, width, height);
    }


    public void draw(){
        super.draw(x, y, width, height);
    }

    public void update(float[] matrix, float x, float y, float width, float height){
        this.matrix = matrix;
        this.sX = x;
        this.sY = y;
        this.sW = width;
        this.sH = height;
        switch (graphicType) {
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
            case Const.Graphic_GUI:
                width *= Control.SquareScaleMin;
                height *= Control.SquareScaleMin;
                x = UNIJMath.getGUIX(sX, sW);
                y = UNIJMath.getGUIY(sY, sH);
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
            default:
                throw new RuntimeException("Invalid Type!!");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void resize(){
        update(matrix, sX, sY, sW, sH);
    }


    @Override
    public void draw(float x,float y, float width, float height){
        draw(x, y, width, height, matrix);
    }

    public void draw(float[] matrix){
        super.draw(x,y, width, height, matrix);
    }

    public boolean Touched(){
        boolean b = Input.floatX >= x && Input.floatX <= x + width && Input.floatY >= y && Input.floatY <= y + height;
        if(b) Touch.playGUIDefault();
        return b;
    }

    public void TouchVerbose(){
        System.out.println("InputX:" + Input.floatX + " X:" + x + " InputY:" + Input.floatY + " Y:" + y + " Width:" + width + " Height:" + height);
    }

    @Override
    public void draw(float x,float y, float width, float height, float[] matrix){
        switch (graphicType) {
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
            case Const.Graphic_GUI:
                width *= Control.SquareScaleMin;
                height *= Control.SquareScaleMin;
                x = UNIJMath.getGUIX(sX, sW);
                y = UNIJMath.getGUIY(sY, sH);
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
            default:
                throw new RuntimeException("Invalid Type!!");
        }
        super.draw(x, y, width, height, matrix);
    }
}
