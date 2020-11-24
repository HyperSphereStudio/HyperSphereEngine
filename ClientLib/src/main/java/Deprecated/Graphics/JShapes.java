package Deprecated.Graphics;
import Graphics.Components.AbstractGraphic;
import IteratorWrappers.ObjObj.JObjObjList;
import Manager.Const;
import Manager.Control;

import Utils.UNIJMath;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;


public class JShapes {
    //Non Scale
    public static void filledRect(float x, float y, float width, float height, Color color, int type) throws RuntimeException {
        setMode(Const.Graphic_SHAPE);
        Control.getShapeRenderer().set(ShapeRenderer.ShapeType.Filled);
        Control.getShapeRenderer().setColor(color);
        float X; float Y; float Width; float Height;
        switch (type) {
            case Const.Graphic_NONSQUARE:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Width = Control.nonSquareScaleX;
                Height = Control.nonSquareScaleY;
                break;
            case Const.Graphic_GUI:
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                X = UNIJMath.getGUIX(x, width) / x;
                Y = UNIJMath.getGUIY(y, height) / y;
                break;
            case Const.Graphic_TILE:
                X = Control.Tile;
                Y = Control.Tile;
                Width = Control.Tile;
                Height = Control.Tile;
                break;
            case Const.Graphic_SQUAREMIN:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                break;
            case Const.Graphic_SQUAREMAX:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Width = Control.SquareScaleMax;
                Height = Control.SquareScaleMax;
                break;
            case Const.Graphic_MULTI:
                X = Control.SquareScaleMin;
                Y = Control.SquareScaleMin;
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                break;
            case Const.Graphic_DISPLAY:
                X = UNIJMath.getGraphicX(x) / x;
                Y = UNIJMath.getGraphicY(y) / y;
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                break;
            default:
                throw new RuntimeException("Invalid Type!!");
        }
        Control.getShapeRenderer().rect(x * X, y * Y,  width * Width, height * Height);
    }

    public static void filledTriangle(float posX, float posY, float width, float height, Color color, short type){
        setMode(Const.Graphic_SHAPE);
        Control.getShapeRenderer().set(ShapeRenderer.ShapeType.Filled);
        Control.getShapeRenderer().setColor(color);
        float X; float Y; float Width; float Height;
        switch (type) {
            case Const.Graphic_NONSQUARE:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Width = Control.nonSquareScaleX;
                Height = Control.nonSquareScaleY;
                break;
            case Const.Graphic_GUI:
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                X = UNIJMath.getGUIX(posX, width) / posX;
                Y = UNIJMath.getGUIY(posY, height) / posY;
                break;
            case Const.Graphic_TILE:
                X = Control.Tile;
                Y = Control.Tile;
                Width = Control.Tile;
                Height = Control.Tile;
                break;
            case Const.Graphic_SQUAREMIN:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                break;
            case Const.Graphic_SQUAREMAX:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Width = Control.SquareScaleMax;
                Height = Control.SquareScaleMax;
                break;
            case Const.Graphic_MULTI:
                X = Control.SquareScaleMin;
                Y = Control.SquareScaleMin;
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                break;
            case Const.Graphic_DISPLAY:
                X = UNIJMath.getGraphicX(posX) / posX;
                Y = UNIJMath.getGraphicY(posY) / posY;
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                break;
            default:
                throw new RuntimeException("Invalid Type!!");
        }
        Control.getShapeRenderer().triangle(posX * X + Width * width/2f, Y * posY, X * posX, Y * posY + Height * height, X * posX + Width * width, Y * posY + Height * height);
    }

    public static void lineWidth(float width){
        JShapes.setMode(Const.Graphic_NONE);
        Gdx.gl.glLineWidth(width);
    }

    public static void resetLineWidth(){
        JShapes.setMode(Const.Graphic_NONE);
        Gdx.gl.glLineWidth(1);
    }

    public static void Arc(float x, float y, float radius, float degrees, Color color, int type) throws RuntimeException {
        setMode(Const.Graphic_SHAPE);
        Control.getShapeRenderer().set(ShapeRenderer.ShapeType.Line);
        Control.getShapeRenderer().setColor(color);
        float X; float Y; float Radius;
        switch (type) {
            case Const.Graphic_TILE:
                X = Control.Tile;
                Y = Control.Tile;
                Radius = Control.Tile;
                break;
            case Const.Graphic_SQUAREMIN:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Radius = Control.SquareScaleMin;
                break;
            case Const.Graphic_GUI:
                Radius = Control.SquareScaleMin;
                X = UNIJMath.getGUIX(x, Radius) / x;
                Y = UNIJMath.getGUIY(y, Radius) / y;
                break;
            case Const.Graphic_SQUAREMAX:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Radius = Control.SquareScaleMax;
                break;
            case Const.Graphic_MULTI:
                X = Control.SquareScaleMin;
                Y = Control.SquareScaleMin;
                Radius = Control.SquareScaleMin;
                break;
            case Const.Graphic_DISPLAY:
                X = UNIJMath.getGraphicX(x) / x;
                Y = UNIJMath.getGraphicY(y) / y;
                Radius = Control.SquareScaleMin;
                break;
            default:
                throw new RuntimeException("Invalid Type!!");
        }

        Control.getShapeRenderer().arc(x * X + radius * Radius, y * Y + radius * Radius,  radius * Radius,0, degrees);
    }



    public static void filledEllipse(float x, float y, float width, float height, Color color, int type) throws RuntimeException {
        setMode(Const.Graphic_SHAPE);
        Control.getShapeRenderer().set(ShapeRenderer.ShapeType.Filled);
        Control.getShapeRenderer().setColor(color);
        float X; float Y; float Width; float Height;
        switch (type) {
            case Const.Graphic_NONSQUARE:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Width = Control.nonSquareScaleX;
                Height = Control.nonSquareScaleY;
                break;
            case Const.Graphic_TILE:
                X = Control.Tile;
                Y = Control.Tile;
                Width = Control.Tile;
                Height = Control.Tile;
                break;
            case Const.Graphic_GUI:
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                X = UNIJMath.getGUIX(x, width) / x;
                Y = UNIJMath.getGUIY(y, height) / y;
                break;
            case Const.Graphic_SQUAREMIN:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                break;
            case Const.Graphic_SQUAREMAX:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Width = Control.SquareScaleMax;
                Height = Control.SquareScaleMax;
                break;

            case Const.Graphic_MULTI:
                X = Control.SquareScaleMin;
                Y = Control.SquareScaleMin;
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                break;
            case Const.Graphic_DISPLAY:
                X = UNIJMath.getGraphicX(x) / x;
                Y = UNIJMath.getGraphicY(y) / y;
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                break;
            default:
                throw new RuntimeException("Invalid Type!!");
        }
        Control.getShapeRenderer().ellipse(x * X, y * Y,  width * Width, height * Height);
    }

    public static void Rect(float x, float y, float width, float height, Color color, int type) throws RuntimeException {
        setMode(Const.Graphic_SHAPE);
        float X; float Y; float Width; float Height;
        switch (type) {
            case Const.Graphic_NONSQUARE:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Width = Control.nonSquareScaleX;
                Height = Control.nonSquareScaleY;
                break;
            case Const.Graphic_TILE:
                X = Control.Tile;
                Y = Control.Tile;
                Width = Control.Tile;
                Height = Control.Tile;
                break;
            case Const.Graphic_GUI:
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                X = UNIJMath.getGUIX(x, width) / x;
                Y = UNIJMath.getGUIY(y, height) / y;
                break;
            case Const.Graphic_SQUAREMIN:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                break;
            case Const.Graphic_SQUAREMAX:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Width = Control.SquareScaleMax;
                Height = Control.SquareScaleMax;
                break;
            case Const.Graphic_MULTI:
                X = Control.SquareScaleMin;
                Y = Control.SquareScaleMin;
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                break;
            case Const.Graphic_DISPLAY:
                X = UNIJMath.getGraphicX(x) / x;
                Y = UNIJMath.getGraphicY(y) / y;
                Width = Control.SquareScaleMin;
                Height = Control.SquareScaleMin;
                break;
            default:
                throw new RuntimeException("Invalid Type!!");
        }
        Control.getShapeRenderer().set(ShapeRenderer.ShapeType.Line);
        Control.getShapeRenderer().setColor(color);
        Control.getShapeRenderer().rect(x * X, y * Y,  width * Width, height * Height);
    }
    public static void drawString(String string, float x, float y, float fontSize, float width, Color color, int type) {
        AbstractGraphic.setDefault();
        boolean wrap = width != 0;
        Control.getLayout().setText(getFont(fontSize), string, color, 0, Align.bottomLeft, false);
        float X; float Y;
        switch (type) {
            case Const.Graphic_NONSQUARE:
            case Const.Graphic_SQUAREMIN:
            case Const.Graphic_SQUAREMAX:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                break;
            case Const.Graphic_TILE:
                X = Control.Tile;
                Y = Control.Tile;
                Control.getLayout().setText(getFont(fontSize), string, color, width * X, Align.center, wrap);
                break;
            case Const.Graphic_NOTICE:
            case Const.Graphic_WINDOW:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Control.getLayout().setText(getFont(fontSize), string, color, width * Control.SquareScaleMin, Align.bottomLeft, wrap);
                break;
            case Const.Graphic_MULTI:
                X = Control.SquareScaleMin;
                Y = Control.SquareScaleMin;
                break;
            case Const.Graphic_DISPLAY:
                X = UNIJMath.getGraphicX(x) / x;
                Y = UNIJMath.getGraphicY(y) / y;
                Control.getLayout().setText(getFont(fontSize), string, color, width * Control.SquareScaleMin, Align.bottomLeft, wrap);
                break;
            default:
                throw new RuntimeException("Invalid Type!!");
        }
        getFont(fontSize).draw(Control.getBatch(), Control.getLayout(), (x * X), (y * Y + 10 * (Control.Height / 1000)));
    }



    public static void drawStringCentered(String string, float x, float y, float fontSize, Color color, int type){
        AbstractGraphic.setDefault();
        boolean wrap = false;
        Control.getLayout().setText(getFont(fontSize), string, color, 0, Align.bottomLeft, false);
        float X; float Y;
        switch (type) {
            case Const.Graphic_NONSQUARE:
            case Const.Graphic_SQUAREMIN:
            case Const.Graphic_SQUAREMAX:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                break;
            case Const.Graphic_TILE:
                X = Control.Tile;
                Y = Control.Tile;
                Control.getLayout().setText(getFont(fontSize), string, color, 0, Align.center, wrap);
                break;
            case Const.Graphic_NOTICE:
            case Const.Graphic_WINDOW:
                X = Control.nonSquareScaleX;
                Y = Control.nonSquareScaleY;
                Control.getLayout().setText(getFont(fontSize), string, color, 0, Align.bottomLeft, wrap);
                break;
            case Const.Graphic_MULTI:
                X = Control.SquareScaleMin;
                Y = Control.SquareScaleMin;
                break;
            case Const.Graphic_DISPLAY:
                X = UNIJMath.getGraphicX(x) / x;
                Y = UNIJMath.getGraphicY(y) / y;
                Control.getLayout().setText(getFont(fontSize), string, color, 0, Align.bottomLeft, wrap);
                break;
            default:
                throw new RuntimeException("Invalid Type!!");
        }
        x *= X;
        x -= Control.getLayout().width / 2f;
        getFont(fontSize).draw(Control.getBatch(), Control.getLayout(), (x), (y * Y + 10 * (Control.Height / 1000)));
    }

    public static void drawString(String string, float x, float y, float fontSize, Color color, int type) {
        drawString(string, x, y, fontSize, 0,color,  type);
    }


    public static void shapeTransparency(boolean set){
        if(set) {
            setMode(Const.Graphic_NONE);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }else{
            setMode(Const.Graphic_NONE);
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public static void setImageTransparency(float a){
        Control.getBatch().setColor(1, 1, 1, a);
    }


    public static BitmapFont getFont(float fontSize){
            if (fontHolder.containsKey(fontSize)) return fontHolder.get(fontSize);
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("textures/Fonts/OpenSans-SemiboldItalic.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = Math.max(3, (int) ((fontSize * Control.fontScaler - .1436f) / .065f));
            fontHolder.put(fontSize, generator.generateFont(parameter));
            generator.dispose();
            return fontHolder.get(fontSize);
    }

    public static JObjObjList<Float, BitmapFont> fontHolder = new JObjObjList<>();

    public static void setMode(short mode){
        switch (mode){
            case Const.Graphic_BATCH:
                if(Control.getShapeRenderer().isDrawing()){
                    Control.getShapeRenderer().end();
                }
                if(!Control.getBatch().isDrawing()){
                    Control.getBatch().begin();
                }
                break;
            case Const.Graphic_SHAPE:
                if (Control.getBatch().isDrawing()) {
                    Control.getBatch().end();
                }
                if(!Control.getShapeRenderer().isDrawing()){
                    Control.getShapeRenderer().begin();
                }
                break;
            case Const.Graphic_NONE:
                if (Control.getBatch().isDrawing()) {
                    Control.getBatch().end();
                }
                if(Control.getShapeRenderer().isDrawing()){
                    Control.getShapeRenderer().end();
                }
                break;
        }
    }
}

