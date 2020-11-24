package Graphics.Components.Tasks;

import Graphics.Components.Frameable;
import Graphics.Components.Listeners.RenderTask;
import com.badlogic.gdx.math.Rectangle;

public class RenderETask extends ElementalTask<RenderTask> {

    public RenderETask(RenderTask renderTask, Rectangle bounds) {
        super(renderTask, bounds);
    }

    @Override
    public void runTask(float... data) {
        if(bounds != Frameable.defRect)
        this.t.render(bounds.x, bounds.y, bounds.width, bounds.height);
        else this.t.render(data);
    }
}
