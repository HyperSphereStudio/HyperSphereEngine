package Constants;

public class Const {


    public static final short
            Packet_Unknown = 0,

            Direction_S = 0, Direction_SW = 1, Direction_W = 2, Direction_NW = 3, Direction_N = 4, Direction_NE = 5, Direction_E = 6, Direction_SE = 7,

    Executable_Function = 0, Executable_Variable = 1, Executable_Statement = 2,

    TRISWITCH_OFF = 0, TRISWITCH_MIDDLE = 1, TRISWITCH_ON = 2, TRISWITCH_NOTSET = 3;

    //Time Information
    public static long Time_CONSTANT = 1590000000000L;

    //Color Information
    public static final float[] Color_DEFAULTMATRIX = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
}
