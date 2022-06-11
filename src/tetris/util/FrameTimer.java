package tetris.util;

public class FrameTimer {
    private long length;
    private long startTime;

    public FrameTimer(double length) {
        startTime = System.nanoTime();
        this.length = (long)(length*1e9);
    }

    public boolean isDone() {
        return System.nanoTime() - startTime >= length;
    }

    public void reset() {
        startTime = System.nanoTime();
    }

    public void setLength(double length) {
        this.length = (long)(length*1e9);
    }

    public double getProgress() {
        return (double)(System.nanoTime() - startTime) / (double)length;
    }
}

