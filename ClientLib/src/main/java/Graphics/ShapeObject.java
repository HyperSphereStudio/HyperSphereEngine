package Graphics;
import Deprecated.Graphics.JShapes;
import Graphics.Components.AbstractGraphic;
import Graphics.Components.GraphicElement;
import IteratorWrappers.ObjObj.JObjObjList;
import Manager.Const;
import Manager.Control;
import Stored.PreferenceList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;


public class ShapeObject {
    //Non Scale
    public static GraphicElement filledRect(float x, float y, float width, float height, Color color, short type) throws RuntimeException {
        GraphicElement graphicElement = new GraphicElement(x, y, width, height, type);
        graphicElement.addRenderTask(metaData -> {
            setMode(Const.Graphic_SHAPE);
            Control.getShapeRenderer().set(ShapeRenderer.ShapeType.Filled);
            Control.getShapeRenderer().setColor(color);
            Control.getShapeRenderer().rect(metaData[0], metaData[1], metaData[2], metaData[3]);
        });
        return graphicElement;
    }

    public static GraphicElement filledTriangle(float posX, float posY, float width, float height, Color color, short type) {
        GraphicElement graphicElement = new GraphicElement(posX, posY, width, height, type);
        graphicElement.addRenderTask(metaData -> {
            setMode(Const.Graphic_SHAPE);
            Control.getShapeRenderer().set(ShapeRenderer.ShapeType.Filled);
            Control.getShapeRenderer().setColor(color);
            Control.getShapeRenderer().triangle(metaData[0] + metaData[2] / 2f, metaData[1], metaData[0], metaData[1] + metaData[3], metaData[0] + metaData[2], metaData[1] + metaData[3]);
        });
        return graphicElement;
    }

    public static void lineWidth(float width) {
        JShapes.setMode(Const.Graphic_NONE);
        Gdx.gl.glLineWidth(width);
    }

    public static void resetLineWidth() {
        JShapes.setMode(Const.Graphic_NONE);
        Gdx.gl.glLineWidth(1);
    }

    public static GraphicElement Arc(float x, float y, float radius, Color color, short type) throws RuntimeException {
        GraphicElement graphicElement = new GraphicElement(x + radius, y + radius, radius, 0, type);
        graphicElement.addRenderTask(metaData -> {
            setMode(Const.Graphic_SHAPE);
            Control.getShapeRenderer().set(ShapeRenderer.ShapeType.Line);
            Control.getShapeRenderer().setColor(color);
            Control.getShapeRenderer().arc(metaData[0], metaData[1], metaData[2], 0, graphicElement.metaData[0]);
        });
        return graphicElement;
    }


    public static GraphicElement filledEllipse(float x, float y, float width, float height, Color color, short type) throws RuntimeException {
        GraphicElement graphicElement = new GraphicElement(x, y, width, height, type);
        graphicElement.addRenderTask(metaData -> {
            setMode(Const.Graphic_SHAPE);
            Control.getShapeRenderer().set(ShapeRenderer.ShapeType.Filled);
            Control.getShapeRenderer().setColor(color);
            Control.getShapeRenderer().ellipse(metaData[0], metaData[1], metaData[2], metaData[3]);
        });
        return graphicElement;
    }


    public static GraphicElement Rect(float x, float y, float width, float height, Color color, short type) throws RuntimeException {
        GraphicElement graphicElement = new GraphicElement(x, y, width, height, type);
        graphicElement.addRenderTask(metaData -> {
            setMode(Const.Graphic_SHAPE);
            Control.getShapeRenderer().set(ShapeRenderer.ShapeType.Line);
            Control.getShapeRenderer().setColor(color);
            Control.getShapeRenderer().rect(metaData[0], metaData[1], metaData[2], metaData[3]);
        });
        return graphicElement;
    }

    public static GraphicElement drawString(String string, float x, float y, float fontSize, float width, Color color, short type) {
        GraphicElement graphicElement = new GraphicElement(x, y, -1, -1, type);
        boolean wrap = width != 0;
        graphicElement.addRenderTask(metaData -> {
            AbstractGraphic.setDefault();

            Control.getLayout().setText(getFont(fontSize), string, color, 0, Align.bottomLeft, false);

            switch (type) {
                case Const.Graphic_TILE:
                    Control.getLayout().setText(getFont(fontSize), string, color, width * Control.Tile, Align.center, wrap);
                    break;
                case Const.Graphic_NOTICE:
                case Const.Graphic_WINDOW:
                case Const.Graphic_DISPLAY:
                    Control.getLayout().setText(getFont(fontSize), string, color, width * Control.SquareScaleMin, Align.bottomLeft, wrap);
                    break;
            }

            getFont(fontSize).draw(Control.getBatch(), Control.getLayout(), metaData[0], (metaData[1] + 10 * (Control.Height / 1000)));
        });
        return graphicElement;
    }

    public static GraphicElement drawStringCentered(String string, float x, float y, float fontSize, Color color, short type) {
        GraphicElement graphicElement = new GraphicElement(x, y, -1, -1, type);
        graphicElement.addRenderTask(metaData -> {
            AbstractGraphic.setDefault();

            Control.getLayout().setText(getFont(fontSize), string, color, 0, Align.bottomLeft, false);

            switch (type) {
                case Const.Graphic_TILE:
                    Control.getLayout().setText(getFont(fontSize), string, color, 0, Align.center, false);
                    break;
                case Const.Graphic_NOTICE:
                case Const.Graphic_WINDOW:
                case Const.Graphic_DISPLAY:
                    Control.getLayout().setText(getFont(fontSize), string, color, 0, Align.bottomLeft, false);
                    break;
            }

            getFont(fontSize).draw(Control.getBatch(), Control.getLayout(), metaData[0], (metaData[1] + 10 * (Control.Height / 1000)));
        });
        return graphicElement;
    }


    public static GraphicElement drawString(String string, float x, float y, float fontSize, Color color, short type) {
        return drawString(string, x, y, fontSize, 0, color, type);
    }


    public static void shapeTransparency(boolean set) {
        if (set) {
            setMode(Const.Graphic_NONE);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        } else {
            setMode(Const.Graphic_NONE);
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public static void setImageTransparency(float a) {
        Control.getBatch().setColor(1, 1, 1, a);
    }


    public static BitmapFont getFont(float fontSize) {
        return JShapes.getFont(fontSize);
    }

    public static void setMode(short mode) {
        switch (mode) {
            case Const.Graphic_BATCH:
                if (Control.getShapeRenderer().isDrawing()) {
                    Control.getShapeRenderer().end();
                }
                if (!Control.getBatch().isDrawing()) {
                    Control.getBatch().begin();
                }
                break;
            case Const.Graphic_SHAPE:
                if (Control.getBatch().isDrawing()) {
                    Control.getBatch().end();
                }
                if (!Control.getShapeRenderer().isDrawing()) {
                    Control.getShapeRenderer().begin();
                }
                break;
            case Const.Graphic_NONE:
                if (Control.getBatch().isDrawing()) {
                    Control.getBatch().end();
                }
                if (Control.getShapeRenderer().isDrawing()) {
                    Control.getShapeRenderer().end();
                }
                break;
        }
    }
}

