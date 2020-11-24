package Graphics.Components;

import Graphics.Components.Listeners.VisibilityListener;

import java.util.ArrayList;

public class Layer<T> {
    public ArrayList<Layer<T>> layerManagers = new ArrayList<>();
    public T t;
    public VisibilityListener visibilityListener;
    public int i, sum;
    public boolean gotten;

    public Layer(T t, VisibilityListener visibilityListener) {
        this.t = t;
        this.visibilityListener = visibilityListener;
    }

    public Layer() {

    }

    public void addManager(Layer<T> layerManager) {
        layerManagers.add(layerManager);
    }

    public void addLayer(T t, VisibilityListener visibilityListener) {
        layerManagers.add(new Layer<>(t, visibilityListener));
    }


    public void removeLayerManager(Layer<T> layerManager) {
        layerManagers.remove(layerManager);
    }

    public boolean isVisible() {
        if (visibilityListener != null) {
            return visibilityListener.isVisible();
        }
        if (t != null) {
            return !gotten;
        }
        return true;
    }

    public boolean hasNext() {
        if (t != null) {
            return isVisible() && !gotten;
        } else {
            return i < layerManagers.size() && layerManagers.get(i).hasNext();
        }
    }

    public T next() {
        if (t != null) {
            if (!gotten) {
                gotten = true;
                return t;
            } else return null;
        } else {
            T t = layerManagers.get(i).next();
            reload();
            return t;
        }
    }

    public void reload() {
        while (i < layerManagers.size() && !layerManagers.get(i).hasNext()) {
            i++;
        }
    }

    public void reset() {
        for(Layer layer : layerManagers){
            layer.reset();
        }
        i = 0;
        gotten = false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (layerManagers.size() != 0) {
            stringBuilder.append("OutsideCount:").append(layerManagers.size()).append("{").append("\n");
            for (Layer<T> layer : layerManagers) {
                stringBuilder.append(layer.toString());
            }
            stringBuilder.append("}").append("\n");
            return stringBuilder.toString();
        } else {
            return "Last Layer:" + t;
        }
    }
}
