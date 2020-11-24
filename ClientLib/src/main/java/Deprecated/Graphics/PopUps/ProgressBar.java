package Deprecated.Graphics.PopUps;
import Deprecated.Graphics.Dep2Frame;
import Deprecated.Graphics.JShapes;
import Deprecated.Graphics.PredeterminedGraphic;
import Manager.Const;
import Manager.Control;
import com.badlogic.gdx.graphics.Color;

public class ProgressBar extends Dep2Frame {

    private float progress;
    public float x, y, width, height;
    private PredeterminedGraphic loading;

    public ProgressBar(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        loading = new PredeterminedGraphic(Control.getDefaultGraphic("Loading"), 0, 0, 0, 0, Const.Graphic_NONSQUARE);
    }

    public ProgressBar start(){
        setVisible(true);
        return this;
    }

    public void updateProgress(float current, float max){
        progress = Math.min(1, current / max);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(float... metaData) {
        loading.draw(x, y, width, height);
        JShapes.filledRect(x + .5f * (width / 5f), (y + .08f * (height / .5f)), progress * (width - 1f * (width / 5f)), .05f * (height / .5f), Color.GREEN, Const.Graphic_NONSQUARE);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void renderInitialize() {

    }

    @Override
    public void touch(int x, int y) {

    }

    @Override
    public void typed(char c) {

    }

    @Override
    public void keyPressed(int code) {

    }

    @Override
    public void touchedRemoved() {

    }

    @Override
    public void release() {

    }

    public void resize(){
        loading.resize();
    }

    @Override
    public void drag(int x, int y) {

    }

}
