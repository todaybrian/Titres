/**
 * Author: Brian Yan, Aaron Zhang
 *
 * This is a FrameTimer class that is used to keep track of the time passed.
 * It is used a lot for animations.
 */
package tetris.util;

public class FrameTimer {
    //Intended length of the timer in seconds
    private long length;

    //The time that the timer started in nanoseconds
    private long startTime;

    //Is the timer disabled?
    private boolean isDisabled;

    //Constructor to initialize the timer
    public FrameTimer(double length) {
        this.startTime = System.nanoTime(); //The time that the timer started
        this.length = (long)(length*1e9); //Length of the timer in nanoseconds
        this.isDisabled = false; //The timer is not disabled
    }

    /**
     * Returns if the timer is up or not.
     * If it is disabled, the timer is never up.
     */
    public boolean isDone() {
        return !isDisabled && System.nanoTime() - startTime > length;
    }

    /**
     * Returns the time elapsed in nano seconds
     */
    public long timeElapsed() {
        return System.nanoTime() - startTime;
    }

    /**
     * Restarts and enables the timer.
     */
    public void reset() {
        isDisabled = false;
        startTime = System.nanoTime(); //The time that the timer started
    }

    //Return the length of the timer in seconds
    public double getLength() {
        return length/1e9;
    }

    //Set the timer to be disabled
    public void disable() {
        isDisabled = true;
    }

    //Return whether the timer is disabled
    public boolean isDisabled() {
        return isDisabled;
    }

    //Return the progress of the timer as a percentage
    public double getProgress() {
        //If the percentage is over 100%, return 100% (happens when the timer is done and this method is called)
        return Math.min((double)(System.nanoTime() - startTime) / (double)length, 1);
    }
}

