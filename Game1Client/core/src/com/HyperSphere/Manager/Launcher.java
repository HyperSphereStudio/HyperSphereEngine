package com.HyperSphere.Manager;

import com.badlogic.gdx.ApplicationAdapter;

public class Launcher extends ApplicationAdapter {

    public static Controller controller;
    private DI di;

    public Launcher(DI di) {
        this.di = di;
    }

    @Override
    public void create() {
        super.create();
        controller = new Controller(di);
        di = null;
    }

    @Override
    public void render() {
        controller.render();
    }

    @Override
    public void resize(int x, int y) {
        controller.resize(x, y);
    }

    @Override
    public void pause() {
        controller.pause();
    }

    @Override
    public void resume() {
        controller.resume();
    }

    @Override
    public void dispose() {
        controller.dispose();
    }


}
