package Graphics.GraphicLoaders;

public class GraphicPackage<Result, Input> {

    public Result result;
    public Input input;
    public Runnable runnable;

    public GraphicPackage(Result result, Input input, Runnable runnable){
        this.result = result;
        this.input = input;
        this.runnable = runnable;
    }
}
