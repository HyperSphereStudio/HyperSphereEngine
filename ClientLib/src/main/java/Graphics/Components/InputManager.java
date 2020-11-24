package Graphics.Components;

import Graphics.Components.Listeners.*;
import Manager.Const;

public class InputManager extends Element<InputManager> {
    private TouchListener touchListener;
    private DragListener dragListener;
    private KeyPressListener keyPressListener;
    private TypedListener typedListener;
    private TouchRemovedListener touchRemovedListener;
    private boolean touchFXOnChange;
    private boolean dragFXOnChange;
    private boolean pressFXOnChange;
    private boolean typedFXOnChange;
    private boolean touchRemoveFXOnChange;
    private final boolean disableFurtherInput;
    private static boolean touchedAlready, draggedAlready, pressedAlready, typedAlready, touchedUpAlready;

    public InputManager(boolean disableFurtherInput, float x, float y, float w, float h, short type) {
        super(x, y, w, h, type);
        this.disableFurtherInput = disableFurtherInput;
    }

    public InputManager(boolean disableFurtherInput) {
        this(disableFurtherInput, 0, 0, 10, 10, Const.Graphic_NONSQUARE);
        allowAll = !disableFurtherInput;
    }

    public InputManager(boolean disableFurtherInput, boolean fXOnChange, TouchListener touchListener) {
        this(disableFurtherInput);
        setTouchListener(fXOnChange, touchListener);
    }

    public InputManager(boolean disableFurtherInput, boolean fXOnChange, DragListener dragListener) {
        this(disableFurtherInput);
        setDragListener(fXOnChange, dragListener);
    }

    public InputManager(boolean disableFurtherInput, boolean fXOnChange, TypedListener typedListener) {
        this(disableFurtherInput);
        setKeyTypedListener(fXOnChange, typedListener);
    }

    public InputManager(boolean disableFurtherInput, boolean fXOnChange, KeyPressListener keyPressListener) {
        this(disableFurtherInput);
        setKeyPressedListener(fXOnChange, keyPressListener);
    }

    public InputManager(boolean disableFurtherInput, boolean fXOnChange, TouchRemovedListener touchRemovedListener) {
        this(disableFurtherInput);
        setTouchedRemoveListener(fXOnChange, touchRemovedListener);
    }


    @Override
    public void touch(int x, int y) {
        if (intersects(x, y) && !touchedAlready) {
            if (touchListener != null) {

                if (touchFXOnChange)
                    triggerFX();
                touchListener.onTouch(x, y);

                if (disableFurtherInput) touchedAlready = true;
            }
        }
    }

    @Override
    public void drag(int x, int y) {
        if (intersects(x, y) && !draggedAlready) {
            if (dragListener != null) {

                if (dragFXOnChange) triggerFX();
                dragListener.onDrag(x, y);

                if (disableFurtherInput) draggedAlready = true;
            }
        }
    }

    @Override
    public void typed(char c) {
        if (!typedAlready) {
            if (typedListener != null) {

                if (typedFXOnChange) triggerFX();
                typedListener.keyTyped(c);

                if (disableFurtherInput) typedAlready = true;
            }
        }
    }

    @Override
    public void keyPressed(int code) {
        if (!pressedAlready) {
            if (keyPressListener != null) {

                if (pressFXOnChange) triggerFX();
                keyPressListener.keyPressed(code);

                if (disableFurtherInput) pressedAlready = true;
            }
        }
    }

    @Override
    public void touchedRemoved() {
        if (!touchedUpAlready) {
            if (keyPressListener != null) {

                if (touchRemoveFXOnChange) triggerFX();
                touchRemovedListener.touchRemoved();

                if (disableFurtherInput) touchedUpAlready = true;
            }
        }
    }

    public static void resetTouchedUP() {
        touchedUpAlready = false;
    }

    public static void resetTouched() {
        touchedAlready = false;
    }

    public static void resetDragged() {
        draggedAlready = false;
    }

    public InputManager setTouchListener(boolean fXOnChange, TouchListener touchListener) {
        this.touchListener = touchListener;
        this.touchFXOnChange = fXOnChange;
        return this;
    }

    public InputManager setDragListener(boolean fXOnChange, DragListener dragListener) {
        this.dragListener = dragListener;
        this.dragFXOnChange = fXOnChange;
        return this;
    }

    public InputManager setKeyPressedListener(boolean fXOnChange, KeyPressListener keyPressedListener) {
        this.keyPressListener = keyPressedListener;
        this.pressFXOnChange = fXOnChange;
        return this;
    }

    public InputManager setKeyTypedListener(boolean fXOnChange, TypedListener keyTypedListener) {
        this.typedListener = keyTypedListener;
        this.typedFXOnChange = fXOnChange;
        return this;
    }

    public InputManager setTouchedRemoveListener(boolean fXOnChange, TouchRemovedListener touchRemovedListener) {
        this.touchRemovedListener = touchRemovedListener;
        this.touchRemoveFXOnChange = fXOnChange;
        return this;
    }

    public TouchListener getTouchListener() {
        return touchListener;
    }

    public DragListener getDragListener() {
        return dragListener;
    }

    public static boolean alreadyDragged() {
        return draggedAlready;
    }

    public static boolean alreadyTouched() {
        return touchedAlready;
    }

    public static boolean alreadyTyped() {
        return typedAlready;
    }

    public static boolean alreadyPressed() {
        return pressedAlready;
    }

    public static boolean alreadyTouchedUp() {
        return touchedUpAlready;
    }

    public KeyPressListener getKeyPressedListener() {
        return keyPressListener;
    }

    public TypedListener getTypedListener() {
        return typedListener;
    }


    public TouchRemovedListener getTouchRemovedListener() {
        return touchRemovedListener;
    }

    public boolean intersects(float x, float y) {
        if (!isVisible()) return false;
        if (allowAll) return true;
        return x >= this.bounds.x && x <= this.bounds.x + this.bounds.width && y >= this.bounds.y && y <= this.bounds.y + this.bounds.height;
    }



}