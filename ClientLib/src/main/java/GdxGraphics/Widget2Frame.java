package GdxGraphics;
import Deprecated.Graphics.Dep2Frame;
import Manager.Control;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class Widget2Frame extends Dep2Frame {
    public Widget widget;
    public float alpha = 1;

    public Widget2Frame(Widget widget) {
        this.widget = widget;
        widget.pack();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(float... metaData) {
        widget.draw(Control.getBatch(), alpha);
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
