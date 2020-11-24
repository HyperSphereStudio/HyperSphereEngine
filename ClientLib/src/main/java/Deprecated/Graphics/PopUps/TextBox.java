package Deprecated.Graphics.PopUps;

import Deprecated.Graphics.Dep2Frame;
import Deprecated.Graphics.JShapes;
import Deprecated.Graphics.PredeterminedGraphic;
import Deprecated.Graphics.String.SpecialString;
import Graphics.Components.Listeners.RenderTask;
import Input.Input;
import Manager.Const;
import Manager.Control;
import Utils.Util;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class TextBox extends Dep2Frame {

    public CopyOnWriteArrayList<SpecialString> storageLines;
    public float verticalBarPosition;
    public float verticalBarHeight;
    public float standardPageHeight;
    public float posX, posY, width, height;
    private static int fontSize = 2;
    private boolean vBarSelected = false;
    private boolean guiHandle;
    private float lockOnVerticalBar;
    private short graphicType;
    private boolean avatarMode;
    private float avatarOffset = 0;
    private RenderTask renderTask;
    private PredeterminedGraphic ball, avatar;

    public TextBox(float posX, float posY, float width, float height, short graphicType, RenderTask renderTask) {
        this.renderTask = renderTask;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.graphicType = graphicType;
        ball = new PredeterminedGraphic(Control.getDefaultGraphic("ball"), 0, 0, 0, 0, graphicType);
        avatar = new PredeterminedGraphic(Control.getDefaultGraphic("Avatar"), 0, 0, 0, 0, Const.Graphic_SQUAREMIN);
    }

    public TextBox start(CopyOnWriteArrayList<SpecialString> data, boolean gui) {
        this.storageLines = data;
        this.guiHandle = gui;
        float start = 0;
        Control.getLayout().setText(JShapes.getFont(fontSize * Control.fontScaler), "P", Color.WHITE, width * Util.getWidth(graphicType), Align.bottomLeft, false);
        while (height - start - .5f >= Control.getLayout().height / Util.getHeight(graphicType)) {
            start += Control.getLayout().height / Util.getHeight(graphicType);
        }
        standardPageHeight = start;
        doHeightAnalysis();
        verticalBarPosition = posY;
        if (guiHandle) verticalBarHeight = .5f;
        setVisible(true);
        return this;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(float... metaData) {
        JShapes.setMode(Const.Graphic_BATCH);
        ArrayList<Coordinates> list = new ArrayList<>();
        float start = 0;
        synchronized (Control.getLayoutLock()) {
            for (int i = (int) (((posY + height - verticalBarPosition) / height) * storageLines.size() - 1); i > -1; i--) {
                try {
                    SpecialString specialString = storageLines.get(i);
                    Control.getLayout().setText(JShapes.getFont(fontSize), specialString.getString(), specialString.getColor(), (width - avatarOffset) * Util.getWidth(graphicType), Align.bottomLeft, false);
                    if (height - start - .5f >= Control.getLayout().height / Util.getHeight(graphicType)) {
                        JShapes.getFont(fontSize).draw(Control.getBatch(), Control.getLayout(), (Util.getX(posX, graphicType) + avatarOffset * Control.SquareScaleMin), (Util.getY(start + posY, graphicType)) + .3f * Control.SquareScaleMin);
                        if (avatarMode)
                            list.add(new Coordinates(posX, (start + posY + .3f), specialString.texture, specialString.colorID, specialString.textureBundle));
                        start += textHeight;
                        Control.getBatch().flush();
                    } else break;
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }
        if (!guiHandle) {
            JShapes.filledRect(posX + width - .25f, posY, .125f, height, Color.BROWN, graphicType);
            JShapes.filledRect(posX + width - .375f, verticalBarPosition, .375f, verticalBarHeight * height, Color.BLACK, graphicType);
        } else {
            ball.draw((posX + width) - 1.05f, verticalBarPosition, .5f, .5f);
        }

        if (renderTask != null) {
            float[] array;
            for (Coordinates coordinates : list) {
                if (coordinates.textureBundle != null) {
                    array = new float[coordinates.textureBundle.length + 4];
                    array[0] = coordinates.amt;
                    array[1] = coordinates.x;
                    array[2] = coordinates.y;
                    array[3] = coordinates.colorID;
                    System.arraycopy(coordinates.textureBundle, 0, array, 4, coordinates.textureBundle.length);
                } else array = new float[]{coordinates.amt, coordinates.x, coordinates.y, coordinates.colorID};

                renderTask.render(array);
            }
        }
    }

    @Override
    public void initialize() {

    }

    @Override
    public void renderInitialize() {

    }

    @Override
    public void touch(int x, int y) {
        if (Input.touchedAt((posX + width) - 1.05f, verticalBarPosition, .5f, .5f, graphicType)) {
            vBarSelected = true;
            lockOnVerticalBar = Input.floatY / Util.getHeight(graphicType) - posY;
        } else {
            vBarSelected = false;
        }
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

    public void resize() {
        ball.resize();
        avatar.resize();
    }

    @Override
    public void drag(int x, int y) {
        if (vBarSelected) {
            verticalBarPosition = Math.max(posY, Math.min(posY + height - 1f, Input.dragFloatY / Util.getHeight(graphicType) - lockOnVerticalBar));
        }
    }


    public void replaceData(CopyOnWriteArrayList<SpecialString> data) {
        this.storageLines = data;
        doHeightAnalysis();
        verticalBarPosition = posY;
        if (guiHandle) verticalBarHeight = .5f;
    }


    public void setAvatar() {
        avatarOffset = .44f;
        avatarMode = true;
    }

    public void disableAvatar() {
        avatarOffset = 0;
        avatarMode = false;
    }


    public void doHeightAnalysis() {
        try {
            if (!guiHandle) {
                verticalBarHeight = (height) / getTotalHeight();
                if (verticalBarHeight > 1) verticalBarHeight = 1;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public float getTotalHeight() {
        if (storageLines != null) {
            return textHeight * storageLines.size();
        } else return 1;
    }

    public static final float textHeight = .4f;

    public static void addChatCorrectlyToList(CopyOnWriteArrayList<SpecialString> list, SpecialString specialString, float width, short graphicType) {
        synchronized (Control.getLayoutLock()) {
            int lastIndex = 0;
            int counter = 0;
            for (int i = 0; i < specialString.getString().length(); ++i) {
                Control.getLayout().setText(JShapes.getFont(fontSize), specialString.getString().substring(lastIndex, i));
                if (Control.getLayout().width / Util.getWidth(graphicType) > width) {
                    ++counter;
                    list.add(new SpecialString(specialString.getString().substring(lastIndex, i), specialString.r, specialString.g, specialString.b, specialString.texture, specialString.colorID, specialString.textureBundle, counter));
                    lastIndex = i;
                }
            }
            list.add(new SpecialString(specialString.getString().substring(lastIndex), specialString.r, specialString.g, specialString.b, specialString.texture, specialString.colorID, specialString.textureBundle, counter));
        }
    }

}

class Coordinates {
    public float x, y;
    public int amt, colorID;
    public float[] textureBundle;

    public Coordinates(float x, float y, int amt, int colorID, float[] textureBundle) {
        this.x = x;
        this.y = y;
        this.colorID = colorID;
        this.amt = amt;
        this.textureBundle = textureBundle;
    }


}
