package States;

import Graphics.Components.Frame;

public abstract class SubState extends Frame {

    private static State currentState = null;
    private boolean iterateReady;
    private static StateChangeListener<State> stateStateChangeListener;

    public SubState() {
        super(true);
        iterateReady = false;
    }

    public static void setState(State state) {
        if (state != currentState) {
            if (currentState != null) currentState.release();

            if (stateStateChangeListener != null) stateStateChangeListener.changed(state, currentState);

            currentState = state;
            getState().initialize();
        }
    }

    public static State getState() {
        return currentState;
    }

    public static void setStateStateChangeListener(StateChangeListener<State> stateStateChangeListener) {
        SubState.stateStateChangeListener = stateStateChangeListener;
    }

    @Override
    public void initialize() {
        iterateReady = false;
        super.initialize();
        iterateReady = true;
    }

    @Override
    public void render(float... metaData) {
        if (iterateReady)
            super.render(metaData);
    }

    @Override
    public void release() {
        iterateReady = false;
        super.release();
    }

    @Override
    public void resize() {
        if (iterateReady) super.resize();
    }


    @Override
    public void touch(int x, int y) {
        if (iterateReady) super.touch(x, y);
    }


    @Override
    public void drag(int x, int y) {
        if (iterateReady) super.drag(x, y);
    }

    @Override
    public void update() {
        if (iterateReady) super.update();
    }


    @Override
    public void typed(char c) {
        if (iterateReady) super.typed(c);
    }

    @Override
    public void keyPressed(int code) {
        if (iterateReady) super.keyPressed(code);
    }

    @Override
    public void touchedRemoved() {
        if (iterateReady) super.touchedRemoved();
    }
}
