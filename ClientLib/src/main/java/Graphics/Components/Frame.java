package Graphics.Components;

import Manager.Const;

public class Frame extends Element<Frame> {

    public Frame() {
        super(0, 0, 10, 10, Const.Graphic_NONSQUARE);
        setVisible(false);
    }

    public Frame(float x, float y, float w, float h, short type) {
        super(x, y, w, h, type);
        setVisible(false);
    }

    public Frame(boolean setVisisible) {
        super(0, 0, 10, 10, Const.Graphic_NONSQUARE);
        setVisible(setVisisible);
    }

    public Frame(boolean setVisibile, float x, float y, float w, float h, short type) {
        super(x, y, w, h, type);
        setVisible(setVisibile);
    }
}
