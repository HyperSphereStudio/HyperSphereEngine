package States;

public interface StateChangeListener<T> {
    void changed(T t, T t2);
}
