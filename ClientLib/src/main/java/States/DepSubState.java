package States;

@Deprecated
public abstract class DepSubState extends State {

    public DepSubState() {
        addRenderInitializer(this::renderInitialize);
    }

    @Override
    public abstract void update();

    @Override
    public abstract void render(float... metaData);

    @Override
    public abstract void initialize();

    public abstract void renderInitialize();

    @Override
    public abstract void touch(int x, int y);

    @Override
    public abstract void typed(char c);

    @Override
    public abstract void keyPressed(int code);

    @Override
    public abstract void touchedRemoved();

    @Override
    public abstract void release();

    @Override
    public abstract void resize();

    @Override
    public abstract void drag(int x, int y);
}