package GdxGraphics;

import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class GdxWidget<T> extends Widget2Frame {

    public GdxWidget(Widget widget) {
        super(widget);
    }

    public T start() {
        setVisible(true);
        return (T) this;
    }
}
