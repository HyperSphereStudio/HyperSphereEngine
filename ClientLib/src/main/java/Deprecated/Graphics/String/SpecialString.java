package Deprecated.Graphics.String;

import com.badlogic.gdx.graphics.Color;

public class SpecialString implements Cloneable{

    private String string;
    public int chatType, colorID;
    public float r, g, b;
    public boolean color = false;
    public int texture;
    public float[] textureBundle;
    public int counter = 0;

    public SpecialString(){

    }

    public SpecialString(String string, float r, float g, float b, int texture,int colorID, float[] textureBundle, int counter) {
        this.string = string;
        this.color = true;
        this.counter = counter;
        this.colorID = colorID;
        this.textureBundle = textureBundle;
        this.r = r;
        this.g = g;
        this.b = b;
        this.texture = texture;
    }

    public SpecialString(String string){
        this.string = string;
    }

    public SpecialString(String string, int chatType){
        this.chatType = chatType;
        this.string = string;
    }


    public int getType(){
        return chatType;
    }
    public SpecialString(String string, float r, float g, float b){
        this.string = string;
        this.color = true;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public SpecialString(String string, Color color){
        this.r = color.r;
        this.b = color.b;
        this.g = color.b;
        this.string = string;
    }


    public Color getColor(){
        if(color)return new Color(r, g, b, 1);
        else return Color.WHITE;
    }

    public String getString(){
        return string;
    }

    public void setString(String string){
        this.string = string;
    }


    public SpecialString clone(){
        try {
            return (SpecialString) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
