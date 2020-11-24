package Deprecated.Graphics.PopUps;

import Deprecated.Graphics.Dep2Frame;
import Deprecated.Graphics.PredeterminedGraphic;
import Graphics.Components.Listeners.ResponseListener;
import Input.Input;
import Manager.Const;
import Manager.Control;

public class HSlider extends Dep2Frame {

    private float posX, posY, width, height;
    public float percentage = 0;
    private boolean selected = false;
    public boolean moved = false;
    private PredeterminedGraphic progressBar, ball;
    private ResponseListener responseListener;

    public HSlider(float posX, float posY, float width, float height, float initialPercentage) {
        this.posX = posX;
        this.posY = posY;
        this.percentage = initialPercentage;
        this.width = width;
        progressBar = new PredeterminedGraphic(Control.getDefaultGraphic("horizontalprogressbar"), posX, posY, width, height, Const.Graphic_DISPLAY);
        ball = new PredeterminedGraphic(Control.getDefaultGraphic("ball"), (posX + .5f) + (width - 1.5f) * percentage, posY, .5f, height, Const.Graphic_DISPLAY);
    }

    public HSlider(float posX, float posY, float width, float initialPercentage) {
        this(posX, posY, width, .5f, initialPercentage);
    }

    public HSlider start(ResponseListener responseListener) {
        this.responseListener = responseListener;
        setVisible(true);
        return this;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(float... metaData) {
        progressBar.draw();
        ball.draw((posX + .5f) + (width - 1.5f) * percentage, posY, .5f, .5f);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void renderInitialize() {

    }

    @Override
    public void touch(int x, int y) {
        if (Input.touchedAt((posX + .5f) + (width - 1.5f) * percentage, posY, .5f, .5f, Const.Graphic_DISPLAY)) {
            selected = true;
        } else if (Input.Touched) {
            selected = false;
        }
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

    @Override
    public void resize() {
        progressBar.resize();
        ball.resize();
    }

    @Override
    public void drag(int x, int y) {
        if (selected) {
            moved = true;
            percentage = Math.max(0, Math.min(1, ((Input.dragFloatX - Control.cameraX) / Control.SquareScaleMin - (posX + .5f)) / (width - 1.5f)));
        }
    }


}
