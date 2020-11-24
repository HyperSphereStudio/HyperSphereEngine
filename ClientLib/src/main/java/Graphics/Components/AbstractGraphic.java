package Graphics.Components;
import Deprecated.Graphics.JShapes;
import Manager.Const;
import Manager.Control;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;

public class AbstractGraphic {

    public float[] matrix;
    public TextureRegion textureRegion;
    public static float[] currentMatrix = new float[]{};

    public AbstractGraphic(TextureRegion textureRegion){
        this(textureRegion, Const.Color_DEFAULTMATRIX);
    }

    public AbstractGraphic(TextureRegion textureRegion, float[] matrix){
        this.textureRegion = textureRegion;
        this.matrix = matrix;
    }
    public static void setDefault() {
        JShapes.setMode(Const.Graphic_BATCH);
        if(currentMatrix != Const.Color_DEFAULTMATRIX) {
            Control.getBatch().flush();
            Control.getShaderProgram().setUniform1fv("matrix[0]", Const.Color_DEFAULTMATRIX, 0, 20);
            currentMatrix = Const.Color_DEFAULTMATRIX;
        }
    }

    public void draw(float x, float y, float w, float h) {
        draw(textureRegion, x, y, w, h, matrix);
    }

    public void draw(float x, float y, float w, float h, float[] matrix){
            draw(textureRegion, x, y, w, h, matrix);
    }

    public static void draw(TextureRegion textureRegion, float x, float y, float w, float h, float[] matrix) {
            if(matrix == null){
                matrix = Const.Color_DEFAULTMATRIX;
            }
            JShapes.setMode(Const.Graphic_BATCH);

            if (!Arrays.equals(currentMatrix, matrix)) {
                Control.getBatch().flush();
                Control.getShaderProgram().setUniform1fv("matrix[0]", matrix, 0, 20);
                currentMatrix = matrix;
            }

        Control.getBatch().draw(textureRegion, x, y, w, h);
    }

    public static void draw(TextureRegion textureRegion, float x, float y, float w, float h) {
        draw(textureRegion, x, y, w, h, Const.Color_DEFAULTMATRIX);
    }

    public static void manualDraw(TextureRegion textureRegion,  float x, float y, float w, float h, float[] matrix){
        draw(textureRegion, x, y, w, h, matrix);
    }
}
