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

public class Notice extends Dep2Frame {

    public String notice;
    private PredeterminedGraphic Notice;

    public Notice(){

        Notice = new PredeterminedGraphic(Control.getDefaultGraphic("notice"), 2, 2, 6, 6, Const.Graphic_NONSQUARE);
    }

    public Notice start(String noticeMessage){
        this.notice = noticeMessage;
        setVisible(true);
        return this;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(float... metaData) {
        Notice.draw();
        JShapes.drawString(notice, 2.9f, 5.3f, 2,4.5f, Color.BLACK, Const.Graphic_WINDOW);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void renderInitialize() {

    }

    @Override
    public void touch(int x, int y) {
        if(Input.touchedAt(7, 6, 1, 1, Const.Graphic_NONSQUARE)){
            Touch.playGUIDefault();
            setVisible(false);
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

    public void resize(){
        Notice.resize();
    }

    @Override
    public void drag(int x, int y) {

    }
}
