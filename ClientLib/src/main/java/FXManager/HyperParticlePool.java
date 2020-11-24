package FXManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Array;

public class HyperParticlePool {
    public ParticleEffectPool effectPool;
    public Array<HyperParticleManager> effects = new Array<>();
    public int lengthOfTime;
    boolean foreGround;
    public String name;

    public HyperParticlePool(String name, ParticleEffect particleEffect, int defaultDuration, boolean foreGround, int initialCapicty, int maxCapacity) {
        effectPool = new ParticleEffectPool(particleEffect, initialCapicty, maxCapacity);
        this.lengthOfTime = defaultDuration;
        this.foreGround = foreGround;
        this.name = name;
    }

    public void clear() {
        for (int i = effects.size - 1; i >= 0; i--)
            effects.get(i).free();
        effects.clear();
    }

    public void dispose() {
        effectPool.clear();
    }


    public ParticleEffect newEffect(int posx, int posy, int duration, boolean calculatable) {
        ParticleEffect particleEffect = obtainEffect();
        particleEffect.setPosition(posx, posy);
        particleEffect.setDuration(duration);
        particleEffect.start();
        HyperParticleManager hyperParticleManager = new HyperParticleManager((ParticleEffectPool.PooledEffect) particleEffect, this);
        if(calculatable){
            hyperParticleManager.setCalculateAble(posx, posy);
        }
        effects.add(hyperParticleManager);
        return particleEffect;
    }

    public ParticleEffect newEffect(int posx, int posy, boolean calculatable) {
        return newEffect(posx, posy, lengthOfTime, calculatable);
    }

    public ParticleEffect obtainEffect() {
        return effectPool.obtain();
    }

    public void draw() {
        for (int i = effects.size - 1; i >= 0; i--) {
            effects.get(i).draw();
        }
    }


}
