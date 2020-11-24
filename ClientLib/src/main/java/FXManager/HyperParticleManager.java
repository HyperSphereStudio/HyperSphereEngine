package FXManager;

import Graphics.GameCamera;
import Manager.Control;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;

public class HyperParticleManager {
    public HyperParticlePool particleEffectManager;
    public ParticleEffectPool.PooledEffect hyperParticle;
    public boolean calculateAble;
    public float posX, posY;
    public String lastID;

    public HyperParticleManager(ParticleEffectPool.PooledEffect particleEffect, HyperParticlePool particleEffectManager) {
        hyperParticle = particleEffect;
        this.particleEffectManager = particleEffectManager;
    }

    public void setCalculateAble(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.calculateAble = true;
    }


    public void free() {
        if (hyperParticle != null) {
            this.particleEffectManager.effects.removeValue(this, true);
            this.hyperParticle.reset(true);
            ((ParticleEffectPool.PooledEffect) hyperParticle).free();
            this.hyperParticle = null;
        }
    }


    public void updatePosition() {
        if (calculateAble) {
            hyperParticle.setPosition((posX - GameCamera.getxOffset()) * Control.Tile, (posY - GameCamera.getyOffset()) * Control.Tile);
        }
    }

    public void draw() {
        if (hyperParticle != null) {
            if (!hyperParticle.isComplete()) {
                updatePosition();
                hyperParticle.draw(Control.getBatch(), Gdx.graphics.getDeltaTime());
            } else {
                free();
            }
        }
    }
}