package Graphics.Components;;
import Graphics.Components.Listeners.RenderTask;
import Graphics.Components.Tasks.RenderETask;
import Manager.Control;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GraphicElement extends Element<GraphicElement> {
    private AbstractGraphic graphic;
    public boolean defaultVisibility, touchVisibility;

    public GraphicElement(String graphicName, float x, float y, float w, float h, short type) {
        this(x, y, w, h, type);
        setGraphic(graphicName);
    }

    public GraphicElement(TextureRegion textureRegion, float x, float y, float w, float h, short type) {
        this(x, y, w, h, type);
        setGraphic(textureRegion);
    }

    public GraphicElement(float x, float y, float w, float h, short type) {
        super(x, y, w, h, type);
        setVisible(true);

        addRenderTask(metaData -> {
            if (defaultVisibility) {
                graphic.draw(x, y, w, h);
            }
        });
    }


    public GraphicElement setGraphic(AbstractGraphic abstractGraphic) {
        graphic = abstractGraphic;

        if (abstractGraphic == null) {
            setGraphicVisibility(false);
        } else {
            setGraphicVisibility(defaultVisibility);
        }

        return this;
    }

    @Override
    public void render(float... metaData) {
        super.render(bounds.x, bounds.y, bounds.width, bounds.height);
    }




    public GraphicElement setGraphic(TextureRegion textureRegion) {
        if (textureRegion != null)
            setGraphic(new AbstractGraphic(textureRegion));
        else setGraphic((AbstractGraphic) null);
        return this;
    }

    public GraphicElement setGraphic(String name) {
        if (name != null) {
            setGraphic(Control.getDefaultTextureRegion(name));
        } else {
            setGraphic((TextureRegion) null);
        }
        return this;
    }

    public GraphicElement setGraphicBounds(float x, float y, float w, float h, short type) {
        super.update(x, y, w, h, type);
        return this;
    }

    public GraphicElement setGraphicMatrix(float[] matrix) {
        graphic.matrix = matrix;
        return this;
    }

    @Override
    public GraphicElement addInputManager(InputManager inputManager) {
        inputManager.setBounds(this);
        super.addInputManager(inputManager.setVisibilityListener(() -> touchVisibility));
        return this;
    }

    @Override
    public GraphicElement addInputManager(InputManager inputManager, boolean setGraphicBounds) {
        if (setGraphicBounds) return addInputManager(inputManager);
        else
            return super.addInputManager(inputManager.setVisibilityListener(() -> touchVisibility));
    }

    public GraphicElement setVisibility(boolean visible) {
        setGraphicVisibility(visible);
        setTouchVisibility(visible);
        return this;
    }

    public void setGraphicVisibility(boolean visible) {
        defaultVisibility = visible;
    }

    public void setTouchVisibility(boolean visible) {
        touchVisibility = visible;
    }

}
