package Utils.Loops;

public class Loop {
    public int start;
    public int end;
    public Iteration iteration;
    public float step;
    public float[] data;

    public Loop(int start, int end, float step, Iteration iteration) {
        this.start = start;
        this.end = end;
        this.step = step;
        this.iteration = iteration;
    }


    public void setData(int nestCount, float f) {
        if (data == null || data.length < nestCount) {
            float[] d = new float[nestCount];
            if (data != null)
                System.arraycopy(data, 0, d, 0, data.length);
            data = d;
        }
        data[data.length - nestCount] = f;
    }

    public void loop() {
        for (float i = start; i < end; i += step) {
            setData(1, i);
            iteration.iteration(data);
        }
    }
}
