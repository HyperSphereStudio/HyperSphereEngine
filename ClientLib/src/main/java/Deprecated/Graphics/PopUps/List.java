package Deprecated.Graphics.PopUps;
import Deprecated.Graphics.Dep2Frame;
import Deprecated.Graphics.JShapes;
import Deprecated.Graphics.PredeterminedGraphic;
import Graphics.Components.Listeners.ResponseListener;
import Input.Input;
import Input.Touch;
import Manager.Const;
import Manager.Control;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class List extends Dep2Frame {

    private PredeterminedGraphic list;
    private VSlider vSlider;
    private ArrayList<String> data;
    private Touch touch;
    private String lastTouched;
    private ResponseListener responseListener;

    public List(){

       list =  new PredeterminedGraphic(Control.getDefaultGraphic("list"), 2.5f, 2.5f, 6, 7, Const.Graphic_DISPLAY);
        vSlider = new VSlider(2.5f, 3.5f, .5f, 4, 0);
        add(vSlider);
    }

    public List start(ArrayList<String> data, ResponseListener responseListener){
        this.data = data;
        this.touch = new Touch(6.2f, 8.7f, .5f, .5f, Const.Graphic_DISPLAY, false);
        this.responseListener = responseListener;
        setVisible(true);
        return this;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(float... metaData) {
        JShapes.setImageTransparency(Control.getDefaultTransparency());
        list.draw();
        vSlider.render();
        JShapes.setImageTransparency(1);
        float y = 7.55f;
        for(int i = 0, startIndex = (int) (vSlider.percentage * data.size()); i < 10 && i + startIndex < data.size(); ++i){
            JShapes.drawString(data.get(startIndex + i), 4.2f, y, 2, Color.WHITE, Const.Graphic_DISPLAY);
            y -= .48f;
        }
    }

    @Override
    public void initialize() {

    }

    @Override
    public void renderInitialize() {

    }

    @Override
    public void touch(int X, int Y) {
        if(touch.Touched()){
            responseListener.respond(Const.Popup_CLOSE);
        }
        float y = 7.55f;
        for(int i = 0, startIndex = (int) (vSlider.percentage * data.size()); i < 10 && i + startIndex < data.size(); ++i){
            if(Input.touchedAt(4.2f, y, 4, .48f, Const.Graphic_DISPLAY)){
                lastTouched = data.get(startIndex + i);
                responseListener.respond(Const.Popup_INFORMATIONREADY);
            }
            y -= .48f;
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
    public void resize(){
        touch.resize();
    }

    @Override
    public void drag(int x, int y) {

    }



}
