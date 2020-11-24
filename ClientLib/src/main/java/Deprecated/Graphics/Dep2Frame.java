package Deprecated.Graphics;

import Graphics.Components.Frame;

public abstract class Dep2Frame extends Frame {

    public Dep2Frame(){
        addUpdateListener(this::update);
        addRenderTask(this::render);
        addInitializationListener(this::initialize);
        addRenderInitializer(this::renderInitialize);
        addTouchListener(false, false, this::touch);
        addKeyTypedListener(false, false, this::typed);
        addKeypressedListener(false, false, this::keyPressed);
        addResizeListener(this::resize);
        addDragListener(false, false, this::drag);
        addReleaseListener(this::release);
        addTouchedRemovedListener(false, false, this::touchedRemoved);
    }

    @Override
    public abstract void update();

    @Override
    public abstract void render(float... metaData);

    @Override
    public abstract void initialize();

    public abstract void renderInitialize();

    @Override
    public abstract void touch(int x, int y);

    @Override
    public abstract void typed(char c);

    @Override
    public abstract void keyPressed(int code);

    @Override
    public abstract void touchedRemoved();

    @Override
    public abstract void release();

    @Override
    public abstract void resize();

    @Override
    public abstract void drag(int x, int y);
}
