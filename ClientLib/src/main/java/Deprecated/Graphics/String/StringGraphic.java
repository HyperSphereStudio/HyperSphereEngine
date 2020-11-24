package Deprecated.Graphics.String;
import Deprecated.Graphics.JShapes;
import Graphics.Components.AbstractGraphic;
import Manager.Const;
import Manager.Control;
import Utils.UNIJMath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;

public class StringGraphic {

    public String string;
    public float x, y, fontSize, baseX, baseY;
    public Color color;
    public BitmapFont bitmapFont;
    public short type;

    public StringGraphic(String string, float x, float y, float fontSize, Color color, short type){
        this.string = string;
        baseX = x;
        baseY = y;
        this.color = color;
        this.fontSize = fontSize;
        this.type = type;
        update(baseX, baseY);
    }


    public void update(float x, float y){
        bitmapFont = JShapes.getFont(fontSize);
        switch (type){
            case Const.Graphic_NONSQUARE:
            case Const.Graphic_SQUAREMIN:
            case Const.Graphic_SQUAREMAX:
                this.x = x * Control.nonSquareScaleX;
                this.y = y * Control.nonSquareScaleY;
                break;
            case Const.Graphic_TILE:
                this.x = x * Control.Tile;
                this.y = y * Control.Tile;
                break;
            case Const.Graphic_MULTI:
                this.x = x * Control.SquareScaleMin;
                this.y  = y * Control.SquareScaleMin;
                break;
            case Const.Graphic_DISPLAY:
                this.x =  UNIJMath.getGraphicX(x);
                this.y = UNIJMath.getGraphicY(y);
                break;
        }
    }

    public void draw(){
        draw(x, y, string);
    }

    public void draw(float x, float y, String string){
        AbstractGraphic.setDefault();
        Control.getLayout().setText(bitmapFont, string, color, 0, Align.bottomLeft, false);
        bitmapFont.draw(Control.getBatch(), Control.getLayout(), x, y);
    }

    public void resize(){
        update(baseX, baseY);
    }
}
