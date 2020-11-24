package Deprecated.Graphics.PopUps;

import Deprecated.Graphics.Dep2Frame;
import Deprecated.Graphics.JShapes;
import Deprecated.Graphics.PredeterminedGraphic;
import Graphics.Components.Listeners.ResponseListener;
import Input.Input;
import Manager.Const;
import Manager.Control;
import Utils.StringManager;
import Utils.UNIJMath;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

public class InputField extends Dep2Frame {
    private String title;
    private int curserHorizPlacement = 0;
    private int curserVertPlacement = 0;
    private int errorline = -1;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    public ArrayList<Line> list = new ArrayList<>();
    private float verticalBarPosition = 0, horizontalBarPosition = 0;
    private float maxWidth;
    private float lockOnVerticalBar, lockOnHorizontalBar;
    private boolean vbarSelected, hbarSelected;
    private static final float x = 1f, y = 1.2f, width = 7.5f, height = 5f;
    public PredeterminedGraphic textbox = new PredeterminedGraphic(Control.getDefaultGraphic("textbox"), 0, 0, 10f, 7f, Const.Graphic_NONSQUARE), ball = new PredeterminedGraphic(Control.getDefaultGraphic("ball"), 0, 0, 1, 1, Const.Graphic_SQUAREMIN), buttonRed = new PredeterminedGraphic(Control.getDefaultGraphic("ButtonRed"), 0, 6f, 1f, .5f, Const.Graphic_NONSQUARE);

    public ResponseListener responseListener;

    public InputField() {

    }

    public InputField start(String string, String title, ResponseListener responseListener){
        this.title = title;
        this.responseListener = responseListener;
        font = JShapes.getFont(2);
        glyphLayout = new GlyphLayout();
        appendString(string);
        curserHorizPlacement = list.get(curserVertPlacement).string.length();
        findmaxWidth();
        setVisible(true);
        return this;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(float... metaData) {
        JShapes.drawString(title, 2, 6.75f, 2, Color.BLACK, Const.Graphic_SQUAREMIN);
        JShapes.setImageTransparency(Control.getDefaultTransparency());
        textbox.draw();
        JShapes.setImageTransparency(1);
        ball.draw(8.75f, 4.5f - verticalBarPosition * 4.5f + .75f, .5f, .5f);
        ball.draw(horizontalBarPosition * 6.75f + 1f, .3f, .5f, .5f);
        buttonRed.draw();
        buttonRed.draw(0, 5.5f, 1f, .5f);
        buttonRed.draw(0, 5f, 1f, .5f);
        buttonRed.draw(0, 4.5f, 1f, .5f);
        buttonRed.draw(0, 4, 1, .5f);
        JShapes.drawString("Copy", 0.125f, 6.25f, 2, Color.WHITE, Const.Graphic_NONSQUARE);
        JShapes.drawString("Paste", 0.125f, 5.75f, 2, Color.WHITE, Const.Graphic_NONSQUARE);
        JShapes.drawString("Save", .125f, 5.25f, 2, Color.WHITE, Const.Graphic_NONSQUARE);
        JShapes.drawString("Exit", .125f, 4.75f, 2, Color.WHITE, Const.Graphic_NONSQUARE);
        JShapes.drawString("Clear", .125f, 4.25f, 2, Color.WHITE, Const.Graphic_NONSQUARE);
        if (Control.getShapeRenderer().isDrawing()) {
            Control.getShapeRenderer().end();
        }
        if (Control.getBatch().isDrawing()) {
            Control.getBatch().end();
        }
        if (!Control.getBatch().isDrawing()) {
            Control.getBatch().begin();
        }
        float h = height + y;
        for (int i = Math.max(Math.min((int) (verticalBarPosition * list.size()), list.size() - 1), 0); i < list.size(); i++) {
            if (h - list.get(i).height / Control.nonSquareScaleY > y) {
                Line line = list.get(i);
                h -= line.height / Control.nonSquareScaleY;
                if (line.string.length() >= maxWidth * horizontalBarPosition) {
                    if (errorline != i)
                        Control.getLayout().setText(font, line.string, (int) (maxWidth * horizontalBarPosition), line.string.length(), Color.WHITE, width * Control.nonSquareScaleX * .975f, Align.left, false, "");
                    else
                        Control.getLayout().setText(font, line.string, (int) (maxWidth * horizontalBarPosition), line.string.length(), Color.RED, width * Control.nonSquareScaleX * .975f, Align.left, false, "");
                    font.draw(Control.getBatch(), Control.getLayout(), (x + .05f) * Control.nonSquareScaleX, (h + .1f) * Control.nonSquareScaleY);
                }
            } else break;
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
        if (Input.touchedAt(1, .9f, 7.5f, 5.4f, Const.Graphic_NONSQUARE)) {
            list.get(curserVertPlacement).deActivate();
            findAndSetCourser();
            list.get(curserVertPlacement).setActive(curserHorizPlacement);
            Gdx.input.setOnscreenKeyboardVisible(true);
        } else if (Input.Touched) {
            Gdx.input.setOnscreenKeyboardVisible(false);
            if (Input.touchedAt(8.75f, 4.5f - verticalBarPosition * 4.5f + .75f, .5f, .5f, Const.Graphic_SQUAREMIN)) {
                vbarSelected = true;
                hbarSelected = false;
                lockOnVerticalBar = Input.floatY / Control.nonSquareScaleY;
            } else if (Input.touchedAt(horizontalBarPosition * 6.75f + 1f, .3f, .5f, .5f, Const.Graphic_SQUAREMIN)) {
                hbarSelected = true;
                vbarSelected = false;
                lockOnHorizontalBar = Input.floatX / Control.nonSquareScaleX;
            } else {
                vbarSelected = false;
                hbarSelected = false;
            }
            list.get(curserVertPlacement).deActivate();
            if (Input.touchedAt(0, 6f, 1f, .5f, Const.Graphic_NONSQUARE)) {
                copy();
            } else if (Input.touchedAt(0, 5.5f, 1f, .5f, Const.Graphic_NONSQUARE)) {
                paste();
                curserHorizPlacement = list.get(curserVertPlacement).string.length();
                list.get(curserVertPlacement).setActive(curserHorizPlacement);
            } else if (Input.touchedAt(0, 5, 1, .5f, Const.Graphic_NONSQUARE)) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).string.length() > 0) {
                        Line line = list.get(i);
                        if (list.size() - 1 > i) stringBuilder.append(line.string).append("\n");
                        else stringBuilder.append(line.string);
                    }
                }
                responseListener.respond(stringBuilder.toString());
            } else if (Input.touchedAt(0, 4.5f, 1, .5f, Const.Graphic_NONSQUARE)) {
                setVisible(false);
            } else if (Input.touchedAt(0, 4, 1, .5f, Const.Graphic_NONSQUARE)) {
                if (list.size() > 0) {
                    for (int i = 1; i < list.size(); ++i) {
                        list.remove(1);
                    }
                    curserHorizPlacement = 0;
                    curserVertPlacement = 0;
                    list.get(0).string = "";
                    list.get(0).setActive(0);
                }
            }

        }
    }

    @Override
    public void typed(char c) {
        String s = String.valueOf(c);
        if (StringManager.validStringforChat(s)) {
            list.get(curserVertPlacement).addString(s, curserHorizPlacement);
            curserHorizPlacement += s.length();
            list.get(curserVertPlacement).setCourser(curserHorizPlacement);
            findmaxWidth();
        }
    }

    @Override
    public void keyPressed(int code) {
        if (Input.keyList[com.badlogic.gdx.Input.Keys.LEFT]) {
            if (curserHorizPlacement > 0) {
                curserHorizPlacement--;
                list.get(curserVertPlacement).setCourser(curserHorizPlacement);
            } else if (curserVertPlacement > 0) {
                curserHorizPlacement = Math.min(curserHorizPlacement, list.get(curserVertPlacement - 1).string.length());
                list.get(curserVertPlacement).deActivate();
                curserVertPlacement--;
                list.get(curserVertPlacement).setActive(curserHorizPlacement);
            }
            Input.keyList[com.badlogic.gdx.Input.Keys.LEFT] = false;
        } else if (Input.keyList[com.badlogic.gdx.Input.Keys.RIGHT]) {
            if (curserHorizPlacement < list.get(curserVertPlacement).string.length()) {
                curserHorizPlacement++;
                list.get(curserVertPlacement).setCourser(curserHorizPlacement);
            } else if (list.size() - 1 > curserVertPlacement) {
                curserHorizPlacement = Math.min(curserHorizPlacement, list.get(curserVertPlacement + 1).string.length());
                list.get(curserVertPlacement).deActivate();
                curserVertPlacement++;
                list.get(curserVertPlacement).setActive(curserHorizPlacement);
            }
            Input.keyList[com.badlogic.gdx.Input.Keys.RIGHT] = false;
        } else if (Input.keyList[com.badlogic.gdx.Input.Keys.UP]) {
            if (curserVertPlacement > 0) {
                curserHorizPlacement = Math.min(curserHorizPlacement, list.get(curserVertPlacement - 1).string.length());
                list.get(curserVertPlacement).deActivate();
                curserVertPlacement--;
                list.get(curserVertPlacement).setActive(curserHorizPlacement);
                verticalBarPosition = Math.min(4.5f, Math.max(0, verticalBarPosition - 1f / list.size()));
            }
            Input.keyList[com.badlogic.gdx.Input.Keys.UP] = false;
        } else if (Input.keyList[com.badlogic.gdx.Input.Keys.DOWN]) {
            if (list.size() - 1 > curserVertPlacement) {
                curserHorizPlacement = Math.min(curserHorizPlacement, list.get(curserVertPlacement + 1).string.length());
                list.get(curserVertPlacement).deActivate();
                curserVertPlacement++;
                list.get(curserVertPlacement).setActive(curserHorizPlacement);
                verticalBarPosition = Math.min(4.5f, Math.max(0, verticalBarPosition + 1f / list.size()));
            }
            Input.keyList[com.badlogic.gdx.Input.Keys.DOWN] = false;
        } else if (Input.keyList[com.badlogic.gdx.Input.Keys.BACKSPACE]) {
            if (curserHorizPlacement > 0) {
                list.get(curserVertPlacement).removeString(curserHorizPlacement);
                curserHorizPlacement--;
                list.get(curserVertPlacement).setCourser(curserHorizPlacement);
            } else if (curserVertPlacement > 0) {
                if (list.get(curserVertPlacement).string.length() > 0) {
                    curserHorizPlacement = list.get(curserVertPlacement - 1).string.length();
                    list.get(curserVertPlacement - 1).addString(list.get(curserVertPlacement).string, list.get(curserVertPlacement - 1).string.length());
                    list.get(curserVertPlacement).deActivate();
                    curserVertPlacement--;
                    list.get(curserVertPlacement).setActive(curserHorizPlacement);
                    list.remove(curserVertPlacement + 1);
                } else {
                    curserHorizPlacement = Math.min(curserHorizPlacement, list.get(curserVertPlacement - 1).string.length());
                    list.get(curserVertPlacement).deActivate();
                    curserVertPlacement--;
                    list.get(curserVertPlacement).setActive(curserHorizPlacement);
                    list.remove(curserVertPlacement + 1);
                }
                Input.keyList[com.badlogic.gdx.Input.Keys.BACKSPACE] = false;
            }
            findmaxWidth();
        } else if (Input.keyList[com.badlogic.gdx.Input.Keys.ENTER]) {
            if (list.get(curserVertPlacement).string.length() == curserHorizPlacement) {
                insertLine(curserVertPlacement + 1);
                list.get(curserVertPlacement).deActivate();
                curserVertPlacement++;
            } else {
                String partial = list.get(curserVertPlacement).string.substring(curserHorizPlacement);
                list.get(curserVertPlacement).string = list.get(curserVertPlacement).string.substring(0, curserHorizPlacement);
                list.get(curserVertPlacement).deActivate();
                insertLine(curserVertPlacement + 1);
                curserHorizPlacement = Math.min(curserHorizPlacement, list.get(curserVertPlacement + 1).string.length());
                curserVertPlacement++;
                list.get(curserVertPlacement).addString(partial, 0);
            }
            list.get(curserVertPlacement).setActive(curserHorizPlacement);
            findmaxWidth();
            Input.keyList[com.badlogic.gdx.Input.Keys.ENTER] = false;
        }
    }

    @Override
    public void touchedRemoved() {

    }

    @Override
    public void release() {

    }

    @Override
    public void resize() {
        textbox.resize();
        ball.resize();
        buttonRed.resize();
    }

    @Override
    public void drag(int x, int y) {
        if (vbarSelected) {
            verticalBarPosition = Math.min(4.5f, Math.max(0, -(Input.dragFloatY / Control.nonSquareScaleY - lockOnVerticalBar))) / 4.5f;
        } else if (hbarSelected) {
            horizontalBarPosition = Math.min(6.5f, Math.max(0, (Input.dragFloatX / Control.nonSquareScaleX - lockOnHorizontalBar))) / 6.75f;
        }
    }

    public void findmaxWidth() {
        for (Line line : list) {
            if (line.width > maxWidth) maxWidth = line.string.length();
        }
    }

    public void appendString(String string) {
        String[] lines = string.split("\n");
        int place = curserVertPlacement;
        list.add(new Line("", glyphLayout, font));
        for (int i = 0; i < lines.length; i++) {
            String str = lines[i];
            insertLine(place).addString(str, 0);
            place++;
        }
    }

    public Line insertLine(int place) {
        ArrayList<Line> list1 = new ArrayList<>();
        ArrayList<Line> list2 = new ArrayList<>();
        for (int i = 0; i < place; i++) list1.add(list.get(i));
        for (int i = place; i < list.size(); i++) list2.add(list.get(i));
        Line line = new Line("", glyphLayout, font);
        list1.add(line);
        list1.addAll(list2);
        list = list1;
        return line;
    }

    private void paste() {
        String[] lines = Gdx.app.getClipboard().getContents().split("\n");
        int place = curserVertPlacement;
        for (int i = 0; i < lines.length; i++) {
            String str = lines[i];
            insertLine(place).addString(str, 0);
            place++;
        }
    }

    private void copy() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            Line line = list.get(i);
            if (list.size() - 1 > i)
                stringBuilder.append(line.string).append("\n");
            else stringBuilder.append(line.string);
        }
        Gdx.app.getClipboard().setContents(stringBuilder.toString());
    }


    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).string.length() > 0) {
                Line line = list.get(i);
                if (list.size() - 1 > i) stringBuilder.append(line.string).append("\n");
                else stringBuilder.append(line.string);
            }
        }
        return stringBuilder.toString();
    }

    public void findAndSetCourser() {
        float h = height + y;
        int index = 0;
        double compare = Integer.MAX_VALUE;
        for (int i = (int) (verticalBarPosition * list.size()); list.size() > i; i++) {
            if (h - list.get(i).height / Control.nonSquareScaleY > y) {
                Line line = list.get(i);
                h -= line.height / Control.nonSquareScaleY;
                double val = UNIJMath.distance(0, h, Input.floatX / Control.nonSquareScaleX, Input.floatY / Control.nonSquareScaleY);
                if (val < compare) {
                    compare = val;
                    index = i;
                }
            } else break;
        }
        curserVertPlacement = index;
        curserHorizPlacement = list.get(index).setCurserXY((int) (maxWidth * horizontalBarPosition), Input.floatX / Control.nonSquareScaleX - x);
    }


    public void error(int lineNumber) {
        this.errorline = lineNumber;
    }


}

class Line {

    protected float width, height;
    protected String string;
    private GlyphLayout glyphLayout;
    private BitmapFont bitmapFont;
    private boolean active;

    protected Line(String string, GlyphLayout glyphLayout, BitmapFont font) {
        this.string = string;
        this.glyphLayout = glyphLayout;
        this.bitmapFont = font;
        setStats();
    }

    public int setCurserXY(int startVal, float x) {
        int index = -1;
        if (string.length() > startVal) {
            double compare = UNIJMath.distance((width + width / string.length()) / Control.nonSquareScaleX, 0, x, 0);
            for (int i = startVal; i < string.length(); i++) {
                glyphLayout.setText(bitmapFont, string.substring(startVal, i));
                double val = UNIJMath.distance(glyphLayout.width / Control.nonSquareScaleX, 0, x, 0);
                if (compare > val) {
                    compare = val;
                    index = i;
                }
            }
            if (index == -1) index = string.length();
        } else {
            index = string.length();
        }
        setCourser(index);
        return index;
    }

    private void setStats() {
        glyphLayout.setText(bitmapFont, string);
        width = glyphLayout.width;
        height = glyphLayout.height;
    }

    protected void setActive(int placement) {
        setCourser(placement);
    }

    protected void deActivate() {
        removeCourser();
    }

    public void setCourser(int placement) {
        removeCourser();
        addString("|", placement);
    }

    protected void addString(String str, int placement) {
        if (placement > 0 && placement < string.length()) {
            String string1 = string.substring(0, placement);
            string1 += str;
            String string2 = string.substring(placement);
            this.string = string1 + string2;
        } else if (placement == 0) {
            this.string = str + this.string;
        } else {
            this.string += str;
        }
        setStats();
    }

    protected void removeString(int placement) {
        if (placement > 0 && placement < string.length()) {
            String string1 = string.substring(0, placement - 1);
            String string2 = string.substring(placement);
            string = string1 + string2;
            setStats();
        }
    }

    private void removeCourser() {
        string = string.replaceAll("\\|", "");
    }
}
