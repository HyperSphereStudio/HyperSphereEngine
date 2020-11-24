package Deprecated.Graphics.PopUps;
import Deprecated.Graphics.Dep2Frame;
import Deprecated.Graphics.JShapes;
import Deprecated.Graphics.PredeterminedGraphic;
import Graphics.Components.Listeners.ResponseListener;
import Input.Touch;
import Manager.Const;
import Manager.Control;
import com.badlogic.gdx.graphics.Color;

public class CheckMark extends Dep2Frame {

    public float x, y;
    public boolean selected;
    private String title;
    private PredeterminedGraphic buttonRed, buttonGreen;
    private Touch touch;
    private ResponseListener responseListener;

    public CheckMark(float x, float y, String title, boolean selected){
        this.x = x;
        this.y = y;
        this.selected = selected;
        this.title = title;
        buttonRed = new PredeterminedGraphic(Control.getDefaultGraphic("ButtonRed"), x, y + .1f,1f, .5f, Const.Graphic_DISPLAY);
        buttonGreen = new PredeterminedGraphic(Control.getDefaultGraphic("ButtonGreen"), x, y + .1f,1f, .5f, Const.Graphic_DISPLAY);
        touch = new Touch(x, y + .1f, 1f, .5f, Const.Graphic_DISPLAY, false);
        touch.setDefaultEffects();
    }

    public CheckMark start(ResponseListener responseListener){
        this.responseListener = responseListener;
        setVisible(true);
        return this;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(float... metaData) {
        JShapes.drawString(title, x, y + .8f, 2, Color.BLACK, Const.Graphic_DISPLAY);
        if(!selected)
            buttonRed.draw();
        else buttonGreen.draw();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void renderInitialize() {

    }

    @Override
    public void touch(int x, int y) {
        if(touch.Touched())selected = !selected;
        if(responseListener != null)responseListener.respond(x);
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
    public void resize(){
        buttonRed.resize();
        buttonGreen.resize();
    }

    @Override
    public void drag(int x, int y) {

    }

}
