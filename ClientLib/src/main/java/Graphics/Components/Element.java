package Graphics.Components;

import Graphics.Components.Listeners.*;
import FXManager.SoundManager;
import FXManager.VibrationManager;
import Graphics.Components.Tasks.RenderETask;
import Manager.Const;
import Manager.Control;
import Utils.Loops.DoubleLoop;
import Utils.Loops.Loop;
import Utils.UNIJMath;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public abstract class Element<T extends Frameable> extends Frameable<T> implements Cloneable {
    public short type;
    public Rectangle bounds = new Rectangle();
    public Rectangle baseBounds = new Rectangle();
    public String playSound;
    public int vibrationLength = -1;
    public boolean yFlipped;
    public boolean allowAll;
    public DoubleLoop positionalLoop;


    public Element(float x, float y, float w, float h, short type) {
        if (x == 0 && y == 0 && w == 10 && h == 10 && type == Const.Graphic_NONSQUARE) {
            allowAll = true;
        }
        update(x, y, w, h, type);
        setDefaultFX();
    }

    public T update(float x, float y, float w, float h, short type) {
        this.type = type;
        baseBounds.x = x;
        baseBounds.y = y;
        baseBounds.width = w;
        baseBounds.height = h;
        resize();
        return (T) this;
    }

    public T update(Rectangle baseBounds, short type) {
        this.type = type;
        this.baseBounds = baseBounds;
        resize();
        return (T) this;
    }

    public T setPositionalLooping(int xStart, int xEnd, float xStep, int yStart, int yEnd, int yStep) {
        positionalLoop = new DoubleLoop(yStart, yEnd, yStep, null, new Loop(xStart, xEnd, xStep, (data) -> update(data[1], data[0], baseBounds.width, baseBounds.height)));
        return (T) this;
    }

    public T setSound(String sound) {
        this.playSound = sound;
        return (T) this;
    }

    public T setVibration(int length) {
        this.vibrationLength = length;
        return (T) this;
    }

    @Override
    public T flipY() {
        baseBounds.y = Gdx.graphics.getHeight() - baseBounds.y;
        resize();
        yFlipped = !yFlipped;
        return super.flipY();
    }

    public T setDefaultFX() {
        setDefaultSound();
        setDefaultVibration();
        return (T) this;
    }

    public T setDefaultSound() {
        playSound = "interface3";
        return (T) this;
    }

    public T setDefaultVibration() {
        vibrationLength = 30;
        return (T) this;
    }


    public T setBounds(Element element) {
        this.baseBounds = element.baseBounds;
        this.bounds = element.bounds;
        this.type = element.type;
        return (T) this;
    }

    public T update(float x, float y, float w, float h) {
        update(x, y, w, h, type);
        return (T) this;
    }


    public T triggerFX() {
        triggerFX(playSound, vibrationLength);
        return (T) this;
    }

    public T triggerFX(String playSound, int vibrationLength) {
        SoundManager.playMusic(playSound, false);
        VibrationManager.vibrate(vibrationLength);
        return (T) this;
    }

    @Override
    public void resize() {
        bounds.x = baseBounds.x;
        bounds.y = baseBounds.y;
        bounds.width = baseBounds.width;
        bounds.height = baseBounds.height;

        switch (type) {
            case Const.Graphic_NONSQUARE:
                bounds.x *= Control.nonSquareScaleX;
                bounds.y *= Control.nonSquareScaleY;
                bounds.width *= Control.nonSquareScaleX;
                bounds.height *= Control.nonSquareScaleY;
                break;
            case Const.Graphic_TILE:
                bounds.x *= Control.Tile;
                bounds.y *= Control.Tile;
                bounds.width *= Control.Tile;
                bounds.height *= Control.Tile;
                break;
            case Const.Graphic_SQUAREMIN:
                bounds.x *= Control.nonSquareScaleX;
                bounds.y *= Control.nonSquareScaleY;
                bounds.width *= Control.SquareScaleMin;
                bounds.height *= Control.SquareScaleMin;
                break;
            case Const.Graphic_GUI:
                bounds.width *= Control.SquareScaleMin;
                bounds.height *= Control.SquareScaleMin;
                bounds.x = UNIJMath.getGUIX(baseBounds.x, baseBounds.width);
                bounds.y = UNIJMath.getGUIY(baseBounds.y, baseBounds.height);
                break;
            case Const.Graphic_SQUAREMAX:
                bounds.x *= Control.nonSquareScaleX;
                bounds.y *= Control.nonSquareScaleY;
                bounds.width *= Control.SquareScaleMax;
                bounds.height *= Control.SquareScaleMax;
                break;
            case Const.Graphic_MULTI:
                bounds.x *= Control.SquareScaleMin;
                bounds.y *= Control.SquareScaleMin;
                bounds.width *= Control.SquareScaleMin;
                bounds.height *= Control.SquareScaleMin;
                break;
            case Const.Graphic_DISPLAY:
                bounds.x = UNIJMath.getGraphicX(bounds.x);
                bounds.y = UNIJMath.getGraphicY(bounds.y);
                bounds.width *= Control.SquareScaleMin;
                bounds.height *= Control.SquareScaleMin;
                break;
        }
        super.resize();
    }

    public InputManager addTouchListenerRef(boolean disableFurtherInput, boolean fXOnChange, TouchListener touchListener) {
        InputManager inputManager = new InputManager(disableFurtherInput, fXOnChange, touchListener);
        addInputManager(inputManager, true);
        return inputManager;
    }

    public InputManager addDragListenerRef(boolean disableFurtherInput, boolean fXOnChange, DragListener dragListener) {
        InputManager inputManager = new InputManager(disableFurtherInput, fXOnChange, dragListener);
        addInputManager(inputManager, true);
        return inputManager;
    }

    public InputManager addTouchedRemovedListenerRef(boolean disableFurtherInput, boolean fXOnChange, TouchRemovedListener touchRemovedListener) {
        InputManager inputManager = new InputManager(disableFurtherInput, fXOnChange, touchRemovedListener);
        addInputManager(inputManager, true);
        return inputManager;
    }

    public InputManager addKeyTypedListenerRef(boolean disableFurtherInput, boolean fXOnChange, TypedListener typedListener) {
        InputManager inputManager = new InputManager(disableFurtherInput, fXOnChange, typedListener);
        addInputManager(inputManager, true);
        return inputManager;
    }

    public InputManager addKeypressedListenerRef(boolean disableFurtherInput, boolean fXOnChange, KeyPressListener keyPressListener) {
        InputManager inputManager = new InputManager(disableFurtherInput, fXOnChange, keyPressListener);
        addInputManager(inputManager, true);
        return inputManager;
    }


    public T addTouchListener(boolean disableFurtherInput, boolean fXOnChange, TouchListener touchListener) {
        addInputManager(new InputManager(disableFurtherInput, fXOnChange, touchListener), true);
        return (T) this;
    }

    public T addTouchListener(boolean disableFurtherInput, boolean fXOnChange, ReferentialTouchListener<T> referentialTouchListener) {
        return addTouchListener(disableFurtherInput, fXOnChange, true, ListenerConverter.ReferentialTouch2Touch((T) this, referentialTouchListener));
    }

    public T addDragListener(boolean disableFurtherInput, boolean fXOnChange, ReferentialDragListener<T> referentialDragListener) {
        return addDragListener(disableFurtherInput, fXOnChange, true, ListenerConverter.ReferentialDrag2Drag((T) this, referentialDragListener));
    }

    public T addDragListener(boolean disableFurtherInput, boolean fXOnChange, DragListener dragListener) {
        addInputManager(new InputManager(disableFurtherInput, fXOnChange, dragListener), true);
        return (T) this;
    }

    public T addTouchedRemovedListener(boolean disableFurtherInput, boolean fXOnChange, TouchRemovedListener touchRemovedListener) {
        addInputManager(new InputManager(disableFurtherInput, fXOnChange, touchRemovedListener), true);
        return (T) this;
    }

    public T addKeypressedListener(boolean disableFurtherInput, boolean fXOnChange, KeyPressListener keyPressListener) {
        addInputManager(new InputManager(disableFurtherInput, fXOnChange, keyPressListener), true);
        return (T) this;
    }

    public T addKeyTypedListener(boolean disableFurtherInput, boolean fXOnChange, TypedListener typedListener) {
        addInputManager(new InputManager(disableFurtherInput, fXOnChange, typedListener), true);
        return (T) this;
    }

    public T addTouchListener(boolean disableFurtherInput, boolean fXOnChange, boolean setGraphicBounds, TouchListener touchListener) {
        addInputManager(new InputManager(disableFurtherInput, fXOnChange, touchListener), setGraphicBounds);
        return (T) this;
    }

    public T addTouchListener(boolean disableFurtherInput, boolean fXOnChange, boolean setGraphicBounds, ReferentialTouchListener<T> referentialTouchListener) {
        return addTouchListener(disableFurtherInput, fXOnChange, setGraphicBounds, ListenerConverter.ReferentialTouch2Touch((T) this, referentialTouchListener));
    }

    public T addDragListener(boolean disableFurtherInput, boolean fXOnChange, boolean setGraphicBounds, ReferentialDragListener<T> referentialDragListener) {
        return addDragListener(disableFurtherInput, fXOnChange, setGraphicBounds, ListenerConverter.ReferentialDrag2Drag((T) this, referentialDragListener));
    }

    public T addDragListener(boolean disableFurtherInput, boolean fXOnChange, boolean setGraphicBounds, DragListener dragListener) {
        addInputManager(new InputManager(disableFurtherInput, fXOnChange, dragListener), setGraphicBounds);
        return (T) this;
    }

    public T addTouchedRemovedListener(boolean disableFurtherInput, boolean fXOnChange, boolean setGraphicBounds, TouchRemovedListener touchRemovedListener) {
        addInputManager(new InputManager(disableFurtherInput, fXOnChange, touchRemovedListener), setGraphicBounds);
        return (T) this;
    }

    public T addKeypressedListener(boolean disableFurtherInput, boolean fXOnChange, boolean setGraphicBounds, KeyPressListener keyPressListener) {
        addInputManager(new InputManager(disableFurtherInput, fXOnChange, keyPressListener), setGraphicBounds);
        return (T) this;
    }

    public T addKeyTypedListener(boolean disableFurtherInput, boolean fXOnChange, boolean setGraphicBounds, TypedListener typedListener) {
        addInputManager(new InputManager(disableFurtherInput, fXOnChange, typedListener), setGraphicBounds);
        return (T) this;
    }

    @Override
    public T addInputManager(InputManager inputManager) {
        inputManager.setBounds(this).setAllowAll(allowAll);
        super.addInputManager(inputManager);
        return (T) this;
    }

    public T addInputManager(InputManager inputManager, boolean setGraphicBounds) {
        if (setGraphicBounds) return addInputManager(inputManager);
        else return super.addInputManager(inputManager);
    }

    @Override
    public void render(float... metaData) {
        super.render(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public T addPreRenderTask(RenderTask renderTask) {
        this.preRenderTasks.addLayer(new RenderETask(renderTask, bounds), visibilityListener);
        return (T) this;
    }

    @Override
    public T addRenderTask(RenderTask renderTask) {
        this.renderTask.addLayer(new RenderETask(renderTask, bounds), visibilityListener);
        return (T) this;
    }

    public T setAllowAll(boolean allowAll) {
        this.allowAll = allowAll;
        return (T) this;
    }
}
