package States;

@Deprecated
public abstract class DepState extends State {

    public DepState() {
        addRenderInitializer(this::renderInitialize);
    }

    public abstract void update();

    public abstract void render();

    public abstract void initialize();

    public abstract void renderInitialize();

    public abstract void touch(int x, int y);

    public abstract void typed(char c);

    public abstract void keyPressed(int code);

    public abstract void touchedRemoved();

    public abstract void release();

    public abstract void resize();

    public abstract void drag();
}
