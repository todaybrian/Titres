package tetris.util;

public class Util {
    private static double renderScale;
    public static void setRenderScale(double renderScale){
        Util.renderScale = renderScale;
    }

    private int scale(double val) {
        return (int)Math.round(val * renderScale);
    }

}
