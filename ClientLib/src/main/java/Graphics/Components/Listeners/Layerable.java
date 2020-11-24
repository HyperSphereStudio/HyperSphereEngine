package Graphics.Components.Listeners;

public interface Layerable<T> {
    T next();
    boolean hasNext();
    void reset();
    VisibilityListener isVisible();
}
