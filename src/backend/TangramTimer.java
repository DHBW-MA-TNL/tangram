package backend;

public class TangramTimer {
    //Time Variables
    long startTime;
    long endTime;
    long elapsedTime = 0;
    double elapsedTimeInSeconds = elapsedTime / 1000.0;

    public TangramTimer() {

    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        elapsedTimeInSeconds = elapsedTime / 1000.0;
    }

    public double getElapsedTimeInSeconds() {
        return elapsedTimeInSeconds;
    }

}
