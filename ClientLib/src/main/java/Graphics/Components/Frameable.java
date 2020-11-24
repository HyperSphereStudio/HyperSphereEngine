package Graphics.Components;
import Graphics.Components.Listeners.*;
import Graphics.Components.Tasks.RenderETask;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Frameable<T extends Frameable> implements Cloneable {
    private boolean visible = true;
    public final Layer<UpdateListener> updateListener = new Layer<>();
    public final Layer<RenderETask> preRenderTasks = new Layer<>();
    public final Layer<InitializationListener> initializationListener = new Layer<>();
    public final Layer<InitializationListener> renderInitializer = new Layer<>();
    public final Layer<ReleaseListener> releaseListener = new Layer<>();
    public final Layer<RenderETask> renderTask = new Layer<>();
    public final Layer<ResponseListener> responseListener = new Layer<>();
    public final Layer<Runnable> resizeListener = new Layer<>();
    public final Layer<InputManager> inputManagers = new Layer<>();
    protected ArrayList<Frameable> components = new ArrayList<>();
    protected VisibilityListener visibilityListener;
    private boolean firstTimeForRenderInitalizer = true;
    public static Rectangle defRect = new Rectangle(0, 0, 0, 0);
    public float[] metaData;

    public Frameable() {

    }

    public <T extends Frameable> Frameable<T> getComponent(int i, Class<T> tClass) {
        return tClass.cast(components.get(i));
    }

    public T removeComponent(int i) {
        return (T) remove(components.get(i));
    }

    public T removeComponent(Frameable frameable) {
        remove(frameable);
        components.remove(frameable);
        return (T) this;
    }

    public T flipY() {
        for (Frameable frameable : components) {
            frameable.flipY();
        }
        return (T) this;
    }

    public T setVisibilityListener(VisibilityListener visibilityListener) {
        this.visibilityListener = visibilityListener;
        return (T) this;
    }

    public T addUpdateListener(UpdateListener updateListener) {
        this.updateListener.addLayer(updateListener, visibilityListener);
        return (T) this;
    }

    public T addPreRenderTask(RenderTask renderTask) {
        this.preRenderTasks.addLayer(new RenderETask(renderTask, defRect), visibilityListener);
        return (T) this;
    }

    public T addInitializationListener(InitializationListener initializationListener) {
        this.initializationListener.addLayer(initializationListener, visibilityListener);
        return (T) this;
    }

    public T addRenderInitializer(InitializationListener initializationListener) {
        this.renderInitializer.addLayer(initializationListener, visibilityListener);
        return (T) this;
    }

    public T addReleaseListener(ReleaseListener releaseListener) {
        this.releaseListener.addLayer(releaseListener, visibilityListener);
        return (T) this;
    }

    public T addRenderTask(RenderTask renderTask) {
        this.renderTask.addLayer(new RenderETask(renderTask, defRect), visibilityListener);
        return (T) this;
    }

    public T addResponseListener(ResponseListener responseListener) {
        this.responseListener.addLayer(responseListener, visibilityListener);
        return (T) this;
    }

    public T addResizeListener(Runnable runnable) {
        this.resizeListener.addLayer(runnable, visibilityListener);
        return (T) this;
    }

    public <P> P lCmp(Class<P> t) {
        return t.cast(components.get(components.size() - 1));
    }

    public T addInputManager(InputManager inputManager) {
        this.inputManagers.addLayer(inputManager, visibilityListener);
        return (T) this;
    }

    public <P extends Frameable> P remove(P t) {
        updateListener.removeLayerManager(t.updateListener);
        renderTask.removeLayerManager(t.renderTask);
        inputManagers.removeLayerManager(t.inputManagers);
        initializationListener.removeLayerManager(t.initializationListener);
        renderInitializer.removeLayerManager(t.renderInitializer);
        releaseListener.removeLayerManager(t.releaseListener);
        preRenderTasks.removeLayerManager(t.preRenderTasks);
        return t;
    }

    public <P extends Frameable> P add(P t) {
        updateListener.addManager(t.updateListener);
        renderTask.addManager(t.renderTask);
        inputManagers.addManager(t.inputManagers);
        initializationListener.addManager(t.initializationListener);
        renderInitializer.addManager(t.renderInitializer);
        releaseListener.addManager(t.releaseListener);
        preRenderTasks.addManager(t.preRenderTasks);
        components.add(t);
        return t;
    }


    public boolean isVisible() {
        if (!visible) return false;
        if (visibilityListener != null) {
            return visibilityListener.isVisible();
        }
        return true;
    }

    public T setVisible(boolean visible) {
        this.visible = visible;

        if (visible)
            initialize();
        else release();

        return (T) this;
    }

    public void setMetaData(float... metaData) {
        this.metaData = metaData;
    }

    public void update() {
        while (updateListener.hasNext()) {
            updateListener.next().update();
        }
        updateListener.reset();
    }

    public void release() {
        while (releaseListener.hasNext()) {
            releaseListener.next().released();
        }
        releaseListener.reset();
    }

    public void initialize() {
        resize();
        while (initializationListener.hasNext()) {
            initializationListener.next().initialized();
        }
        initializationListener.reset();
    }



    public void render(float... metaData) {

        if (firstTimeForRenderInitalizer) {
            while (renderInitializer.hasNext()) {
                renderInitializer.next().initialized();
            }
            firstTimeForRenderInitalizer = false;
        }
        renderInitializer.reset();

        while (preRenderTasks.hasNext()) {
            preRenderTasks.next().runTask(metaData);
        }
        preRenderTasks.reset();


        while (renderTask.hasNext()) {
            renderTask.next().runTask(metaData);
        }
        renderTask.reset();
    }

    public void touch(int x, int y) {
        while (inputManagers.hasNext() && !InputManager.alreadyTouched()) {
            inputManagers.next().touch(x, y);
        }
        inputManagers.reset();
    }

    public void drag(int x, int y) {
        while (inputManagers.hasNext() && !InputManager.alreadyDragged()) {
            inputManagers.next().drag(x, y);
        }
        inputManagers.reset();
    }

    public void typed(char c) {
        while (inputManagers.hasNext() && !InputManager.alreadyTyped()) {
            inputManagers.next().typed(c);
        }
        inputManagers.reset();
    }

    public void keyPressed(int code) {
        while (inputManagers.hasNext() && !InputManager.alreadyPressed()) {
            inputManagers.next().keyPressed(code);
        }
        inputManagers.reset();
    }


    public void resize() {
        while (resizeListener.hasNext()) {
            resizeListener.next().run();
        }
        resizeListener.reset();
    }

    public void touchedRemoved() {
        while (inputManagers.hasNext() && !InputManager.alreadyTouchedUp()) {
            inputManagers.next().touchedRemoved();
        }
        inputManagers.reset();
    }

    public String toString() {
        return "Name:" + getClass().getName() + " ComponentCount:" + components.size() + "UpdaterCount:" + updateListener.sum + " InitializerCount:" + initializationListener.sum + " RenderInitializerCount:" + renderInitializer.sum + " RenderTaskCount:" + renderTask.sum + " PreRenderCount:" + preRenderTasks.sum + " InputManagerCount:" + inputManagers.sum;
    }

    public T respond(Object o) {
        while (responseListener.hasNext()) {
            responseListener.next().respond(o);
        }
        responseListener.reset();
        return (T) this;
    }

    public VisibilityListener combineVisibility(VisibilityListener visibilityListener) {
        return getCombinedVisibility(this.visibilityListener, visibilityListener);
    }

    public VisibilityListener getCombinedVisibility(VisibilityListener visibilityListener1, VisibilityListener visibilityListener2) {
        return () -> visibilityListener1.isVisible() && visibilityListener2.isVisible();
    }

    public T clone() {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
