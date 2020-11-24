package Utils;

public class XY {

    public float X;
    public float Y;

    public XY(String X, String Y){
        this.X = Float.parseFloat(X);
        this.Y = Float.parseFloat(Y);
    }

    public XY(){

    }

    public String toString(){
        return "X:" + X + " Y:" + Y;
    }


}
