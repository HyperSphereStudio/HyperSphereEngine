package Utils.Loops;

public class DoubleLoop extends Loop {

    public Loop innerLoop;

    public DoubleLoop(int outerStart, int outerEnd, float outerStep, Iteration outer, Loop innerLoop) {
        super(outerStart, outerEnd, outerStep, (i) -> {
            if (outer != null)
                outer.iteration(i);
            innerLoop.setData(2, i[0]);
            innerLoop.loop();
        });
        this.innerLoop = innerLoop;
    }


}
