package States;
import Graphics.Components.Frame;

public abstract class State extends Frame {

    private static State currentState = null;
    private boolean iterateReady;
    private static StateChangeListener<State> stateStateChangeListener;

    public State() {
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
        State.stateStateChangeListener = stateStateChangeListener;
    }

    @Override
    public void initialize() {
        iterateReady = false;
        super.initialize();
        iterateReady = true;
    }

    @Override
    public void render(float... metaData) {
        if (iterateReady){
            super.render(metaData);
            if(SubState.getState() != null)SubState.getState().render(metaData);
        }
    }

    @Override
    public void release() {
        iterateReady = false;
        super.release();
    }

    @Override
    public void resize() {
        if (iterateReady){
            super.resize();
            if(SubState.getState() != null)SubState.getState().resize();
        }
    }


    @Override
    public void touch(int x, int y) {
        if (iterateReady){
            super.touch(x, y);
            if(SubState.getState() != null)SubState.getState().touch(x, y);
        }
    }


    @Override
    public void drag(int x, int y) {
        if (iterateReady){
            super.drag(x, y);
            if(SubState.getState() != null)SubState.getState().drag(x, y);
        }
    }

    @Override
    public void update() {
        if (iterateReady) {
            super.update();
            if(SubState.getState() != null)SubState.getState().update();
        }
    }


    @Override
    public void typed(char c) {
        if (iterateReady){
            super.typed(c);
            if(SubState.getState() != null)SubState.getState().typed(c);
        }
    }

    @Override
    public void keyPressed(int code) {
        if (iterateReady) {
            super.keyPressed(code);
            if(SubState.getState() != null)SubState.getState().keyPressed(code);
        }
    }

    @Override
    public void touchedRemoved() {
        if (iterateReady) {
            super.touchedRemoved();
            if(SubState.getState() != null)SubState.getState().touchedRemoved();
        }
    }
}
