package Graphics.Components;

import Graphics.Components.Listeners.*;

public class ListenerConverter {

    public static <T> TouchListener ReferentialTouch2Touch(T t, ReferentialTouchListener<T> referentialTouchListener) {
        return (x, y) -> referentialTouchListener.touch(t, x, y);
    }

    public static <T> RenderTask ReferentialRender2Render(T t, ReferentialRenderListener<T> referentialRenderListener) {
        return (metaData -> referentialRenderListener.render(t, metaData));
    }

    public static <T> DragListener ReferentialDrag2Drag(T t, ReferentialDragListener<T> referentialDragListener) {
        return (x, y) -> referentialDragListener.onDrag(t, x, y);
    }

}
