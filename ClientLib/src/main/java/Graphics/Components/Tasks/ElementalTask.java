package Graphics.Components.Tasks;


import com.badlogic.gdx.math.Rectangle;

public abstract class ElementalTask<T> {

    public T t;
    public Rectangle bounds;

    public ElementalTask(T t, Rectangle bounds){
        this.t = t;
        this.bounds = bounds;
    }

    public abstract void runTask(float... data);
}
