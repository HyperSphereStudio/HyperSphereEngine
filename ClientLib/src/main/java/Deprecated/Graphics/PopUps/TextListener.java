package Deprecated.Graphics.PopUps;

import Deprecated.Graphics.Dep2Frame;
import Deprecated.Graphics.JShapes;
import Input.Input;
import Manager.Const;
import Manager.Control;
import Utils.StringManager;
import Utils.UNIJMath;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;

public class TextListener extends Dep2Frame {

    public String string;
    public boolean selected;
    public float posX, posY, width, height;
    private int fontSize, maxSize;
    private int curserPlacement = 0;
    private String showString;
    private int showPlacement = 0;
    public short graphicType = Const.Graphic_NONSQUARE;
    private int chatType;
    private Color color;
    private static boolean selectedAll = false;
    private boolean renderHorizontalBar;
    private boolean hBarSelected = false;
    private float stringWidth, stringHeight, horizontalBarWidth = 1;
    private int testBoxType;
    private float lockOnHorizontalBar;
    private float textShift;
    private float startMoveAt;
    private String hiddenString = "";
    private float horizontalBarPosition = 0;
    private boolean generateHiddenString;
    public String generateString;

    //Note Rank = 0 is integer only, Rank 1 = Letters and numbers (Login), Rank 2 = Email, Rank 3 = All Others

    public TextListener(float posX, float posY, float width, float height, float textShift, int fontSize, int maxSize, Color color, float startHorizMoveAt, boolean renderHorizontalBar, int chatAllow, boolean generateHiddenString, String generationString) {
        this.posX = posX;
        this.startMoveAt = startHorizMoveAt;
        this.posY = posY;
        this.textShift = textShift;
        this.generateHiddenString = generateHiddenString;

        this.width = width;
        this.height = height;
        this.testBoxType = chatAllow;
        this.renderHorizontalBar = renderHorizontalBar;
        this.fontSize = fontSize;
        this.generateString = generationString;
        this.maxSize = maxSize;
        this.color = color;
        if (renderHorizontalBar) {
            horizontalBarPosition = (1 - horizontalBarWidth) * width;
        }
    }

    public TextListener start(String intialString) {
        curserPlacement = intialString.length();
        if (generateHiddenString) {
            hiddenString = regenerateShowPass(intialString);
        }
        this.showString = intialString;
        this.string = intialString;
        setVisible(true);
        return this;
    }

    public void drag() {
        if (hBarSelected) {
            horizontalBarPosition = Math.min((1 - horizontalBarWidth) * width, Math.max(posX, Input.dragFloatX / Control.nonSquareScaleX - lockOnHorizontalBar));
            showPlacement = (int) ((horizontalBarPosition / (posX + width)) * string.length());
            showString = string.substring(showPlacement, generateMaxCap());
        }
    }

    public boolean validString() {
        if (testBoxType == 0) {
            return StringManager.validIntegers(Input.string);
        }
        if (testBoxType == 1) {
            return StringManager.validString(Input.string);
        }
        if (testBoxType == 2) {
            return StringManager.validStringforEmail(Input.string);
        }
        if (testBoxType == 3) {
            return StringManager.validStringforChat(Input.string);
        }
        return false;
    }

    public String regenerateShowPass(String password) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            stringBuilder.append(generateString);
        }
        return stringBuilder.toString();
    }

    public int findPositionInStringOfClick() {
        if (showString.length() > 0) {
            int index = 0;
            double value = UNIJMath.distance(getWidthPartial(showString.substring(0, 1)), 0, Input.floatX / Control.nonSquareScaleX, 0);
            for (int i = 0; i < showString.length(); i++) {
                if (UNIJMath.distance(getWidthPartial(showString.substring(0, i)), 0, Input.floatX / Control.nonSquareScaleX, 0) < value) {
                    value = UNIJMath.distance(getWidthPartial(showString.substring(0, i)), 0, Input.floatX / Control.nonSquareScaleX, 0);
                    index = i;
                }
            }
            return index + showPlacement;
        }
        return 0;
    }


    public int generateMaxCap() {
        int capAmount = string.length();
        float partialWidth = getWidthPartial(string.substring(showPlacement));
        while (partialWidth > startMoveAt * width) {
            capAmount--;
            partialWidth = getWidthPartial(string.substring(showPlacement, capAmount));
        }
        return capAmount;
    }

    public static String insertString(
            String originalString, String stringToBeInserted, int index) {
        StringBuilder newString = new StringBuilder();
        if (index != 0 && originalString.length() != index) {
            for (int i = 0; i < originalString.length(); i++) {
                newString.append(originalString.charAt(i));
                if (i == index) {
                    newString.append(stringToBeInserted);
                }
            }
        } else if (index == 0 && originalString.length() > 0)
            newString.append(originalString, 0, 1).append(stringToBeInserted).append(originalString.substring(1));
        else if (index == 0) newString.append(stringToBeInserted);
        else newString.append(originalString).append(stringToBeInserted);
        return newString.toString();
    }

    public String removeChar(String str, Integer n) {
        String front = str.substring(0, n);
        String back = str.substring(n + 1);
        return front + back;
    }

    public float getWidthPartial(String string) {
        Control.getLayout().setText(JShapes.getFont(fontSize), string, color, 0, Align.bottomLeft, false);
        return Control.getLayout().width / Control.nonSquareScaleX;
    }

    public float getHeightPartial() {
        return 0;
    }


    @Override
    public void update() {

    }

    @Override
    public void render(float... metaData) {
        if (!generateHiddenString) {
            if (selected)
                JShapes.drawString(insertString(showString, "|", curserPlacement - showPlacement), posX, posY + textShift, fontSize, color, graphicType);
            else JShapes.drawString(showString, posX, posY + textShift, fontSize, color, graphicType);
        } else {
            if (selected)
                JShapes.drawString(insertString(hiddenString, "|", curserPlacement - showPlacement), posX, posY + textShift, fontSize, color, graphicType);
            else JShapes.drawString(hiddenString, posX, posY + textShift, fontSize, color, graphicType);
        }
        if (renderHorizontalBar) {
            JShapes.filledRect(horizontalBarPosition, posY - .3f, horizontalBarWidth * width, .2f, Color.BROWN, graphicType);
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
        if (Input.touchedAt(posX, posY, width, height, graphicType)) {
            if (!selected) {
                selected = true;
                selectedAll = true;
            } else {
                curserPlacement = findPositionInStringOfClick();
            }
            Gdx.input.setOnscreenKeyboardVisible(true);
            respond(true);
        } else if (Input.touchedAt(horizontalBarPosition, posY - .3f, horizontalBarWidth * width, .2f, graphicType) && renderHorizontalBar) {
            hBarSelected = true;
            lockOnHorizontalBar = Input.floatX / Control.nonSquareScaleX;
            respond(true);
        } else if (Input.Touched && selected) {
            selected = false;
            Gdx.input.setOnscreenKeyboardVisible(false);
            respond(true);
        }

    }

    @Override
    public void typed(char c) {

        if (selected) {
            if (validString() && maxSize >= string.length() && !Input.string.equals("")) {
                if (curserPlacement == string.length()) {
                    string += Input.string;
                    if (generateHiddenString)
                        hiddenString += "*";
                } else {
                    string = insertString(string, Input.string, curserPlacement);
                    if (generateHiddenString)
                        hiddenString = regenerateShowPass(string);
                }
                Control.getLayout().setText(JShapes.getFont(fontSize * Control.fontScaler), string, color, 0, Align.bottomLeft, false);
                stringWidth = Control.getLayout().width / Control.nonSquareScaleX;
                stringHeight = Control.getLayout().height / Control.nonSquareScaleY;
                horizontalBarWidth = (width) / stringWidth;
                if (horizontalBarWidth > 1) horizontalBarWidth = 1;
                float partialWidth = getWidthPartial(string.substring(showPlacement));
                while (partialWidth > startMoveAt * width) {
                    showPlacement++;
                    partialWidth = getWidthPartial(string.substring(showPlacement));
                }
                showString = string.substring(showPlacement, generateMaxCap());
                curserPlacement++;
            } else if (Input.keyList[com.badlogic.gdx.Input.Keys.BACKSPACE] && curserPlacement >= 0 && string.length() > 0) {
                if (curserPlacement != string.length())
                    string = removeChar(string, curserPlacement);
                else string = string.substring(0, string.length() - 1);
                if (generateHiddenString)
                    hiddenString = hiddenString.substring(0, hiddenString.length() - 1);
                curserPlacement--;
                if (showPlacement > 0) {
                    showPlacement--;
                }
                stringWidth = Control.getLayout().width / Control.nonSquareScaleX;
                stringHeight = Control.getLayout().height / Control.nonSquareScaleY;
                horizontalBarWidth = (width) / stringWidth;
                if (horizontalBarWidth > 1) horizontalBarWidth = 1;
                showString = string.substring(showPlacement, generateMaxCap());
            } else if (hBarSelected) hBarSelected = false;
            Gdx.graphics.requestRendering();
            Input.string = "";
        }


    }

    @Override
    public void keyPressed(int code) {
        if (selected) {
            if (Input.keyList[com.badlogic.gdx.Input.Keys.LEFT] || Input.keyList[com.badlogic.gdx.Input.Keys.RIGHT]) {
                if (Input.keyList[com.badlogic.gdx.Input.Keys.LEFT]) {
                    curserPlacement--;
                    if (showPlacement > 0) {
                        showPlacement--;
                    }
                } else {
                    curserPlacement++;
                    if (curserPlacement < 0) curserPlacement = 0;
                    if (curserPlacement < string.length()) {
                        if (getWidthPartial(string.substring(curserPlacement)) >= startMoveAt * width && curserPlacement < string.length()) {
                            showPlacement++;
                        }
                    }
                }
                Gdx.graphics.requestRendering();
                Input.keyList[com.badlogic.gdx.Input.Keys.LEFT] = false;
                Input.keyList[com.badlogic.gdx.Input.Keys.RIGHT] = false;
            }
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

    }

    @Override
    public void drag(int x, int y) {

    }
}
