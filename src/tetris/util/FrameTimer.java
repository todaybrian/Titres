/**
 * Author: Brian Yan, Aaron Zhang
 *
 * This is a FrameTimer class that is used to keep track of the time passed.
 */
package tetris.util;

public class FrameTimer {
    //Intended length of the timer in seconds
    private long length;

    //The time that the timer started in nanoseconds
    private long startTime;

    //Is the timer disabled?
    private boolean isDisabled;

    public FrameTimer(double length) {
        startTime = System.nanoTime();
        this.length = (long)(length*1e9);
        isDisabled = false;
    }

    public boolean isDone() {
        return !isDisabled && System.nanoTime() - startTime > length;
    }

    public long timeElapsed() {
        return System.nanoTime() - startTime;
    }

    public void reset() {
        isDisabled = false;
        startTime = System.nanoTime();
    }

    public void setLength(double length) {
        this.length = (long)(length*1e9);
    }

    public double getLength() {
        return length/1e9;
    }

    public void disable() {
        isDisabled = true;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    //Return the progress of the timer as a percentage
    public double getProgress() {
        //If the percentage is over 100%, return 100% (happens when the timer is done and this method is called)
        return Math.min((double)(System.nanoTime() - startTime) / (double)length, 1);
    }
}

