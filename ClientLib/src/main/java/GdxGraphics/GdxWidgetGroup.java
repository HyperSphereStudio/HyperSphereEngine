package GdxGraphics;

import Deprecated.Graphics.Dep2Frame;
import Manager.Control;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class GdxWidgetGroup<T> extends Dep2Frame {

    public WidgetGroup widgetGroup;
    public float alpha = 1;

    public GdxWidgetGroup(WidgetGroup widgetGroup){
        this.widgetGroup = widgetGroup;
    }

    public T start(){
        setVisible(true);
        return (T)this;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(float... metaData) {
        widgetGroup.draw(Control.getBatch(), alpha);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void renderInitialize() {

    }

    @Override
    public void touch(int x, int y) {

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

    @Override
    public void resize() {

    }

    @Override
    public void drag(int x, int y) {

    }
}
