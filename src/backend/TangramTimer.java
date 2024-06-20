package backend;

/**
 * TangramTimer is a class that measures the elapsed time.
 * It contains start time, end time, elapsed time, and elapsed time in seconds, and provides methods to start the timer, stop the timer, and get the elapsed time in seconds.
 */
public class TangramTimer {
    /**
     * The start time of the timer.
     */
    long startTime;

    /**
     * The end time of the timer.
     */
    long endTime;

    /**
     * The elapsed time of the timer.
     */
    long elapsedTime = 0;

    /**
     * The elapsed time of the timer in seconds.
     */
    double elapsedTimeInSeconds = elapsedTime / 1000.0;

    /**
     * Constructor for the TangramTimer class.
     * It initializes the timer.
     */
    public TangramTimer() {

    }

    /**
     * This method starts the timer.
     * It sets the start time to the current time in milliseconds.
     */
    public void start() {
        startTime = System.currentTimeMillis();
    }

    /**
     * This method stops the timer.
     * It sets the end time to the current time in milliseconds, and calculates the elapsed time and the elapsed time in seconds.
     */
    public void stop() {
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        elapsedTimeInSeconds = elapsedTime / 1000.0;
    }

    /**
     * This method returns the elapsed time in seconds.
     *
     * @return The elapsed time in seconds.
     */
    public double getElapsedTimeInSeconds() {
        return elapsedTimeInSeconds;
    }

}