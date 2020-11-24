package Deprecated.Graphics.PopUps;

import Deprecated.Graphics.Dep2Frame;
import Deprecated.Graphics.PredeterminedGraphic;
import Input.Input;
import Manager.Const;
import Manager.Control;

public class VSlider extends Dep2Frame {

    private float posX, posY, width, height;
    public float percentage = 0;
    private boolean selected = false;
    public boolean moved = false;
    private PredeterminedGraphic progressBar, ball;

    public VSlider(float posX, float posY, float width, float height, float initialPercentage) {
        this.posX = posX;
        this.posY = posY;
        this.percentage = initialPercentage;
        this.width = width;
        this.height = height;
        progressBar = new PredeterminedGraphic(Control.getDefaultGraphic("verticalprogressbar"), posX, posY, width, height, Const.Graphic_DISPLAY);
        ball = new PredeterminedGraphic(Control.getDefaultGraphic("ball"), posX, (posY + .5f) + (height - 1.5f) * percentage, .5f, .5f, Const.Graphic_DISPLAY);
    }

    public VSlider(float posX, float posY, float width, float initialPercentage) {
        this(posX, posY, width, .5f, initialPercentage);
    }

    public VSlider start() {
        setVisible(true);
        return this;
    }


    @Override
    public void update() {

    }

    @Override
    public void render(float... metaData) {
        progressBar.draw();
        ball.draw(posX, (posY + .5f + height - 1.5f) - (height - 1.5f) * percentage, .5f, .5f);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void renderInitialize() {

    }

    @Override
    public void touch(int x, int y) {
        if (Input.touchedAt(posX, (posY + .5f + height - 1.5f) - (height - 1.5f) * percentage, .5f, .5f, Const.Graphic_DISPLAY)) {
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

    public void resize() {
        progressBar.resize();
        ball.resize();
    }

    @Override
    public void drag(int x, int y) {
        if (selected) {
            moved = true;
            percentage = Math.max(0, Math.min(1, ((-Input.dragFloatY + Control.cameraY) / Control.SquareScaleMin + (posY + .5f + height - 1.5f)) / (height - 1.5f)));
        }
    }


}

