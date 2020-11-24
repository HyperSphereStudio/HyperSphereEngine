package Utilities;

import java.text.DecimalFormat;

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
        return "X:" + round(X) + " Y:" + round(Y);
    }

    public String round(float num){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(num);
    }

}
