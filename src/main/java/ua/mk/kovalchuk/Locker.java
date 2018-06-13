package ua.mk.kovalchuk;

public class Locker {

    private final Object monitor = new Object();
    private volatile boolean isOpen;

    public Locker(final boolean open) {
        isOpen = open;
    }

    public void waitOne() throws InterruptedException {
        synchronized (monitor) {
            while (!isOpen) {
                monitor.wait();
            }
            isOpen = false;
        }
    }

    public void waitOne(final long timeout) throws InterruptedException {
        synchronized (monitor) {
            long t = System.currentTimeMillis();
            while (!isOpen) {
                monitor.wait(timeout);
                // Check for timeout
                if (System.currentTimeMillis() - t >= timeout)
                    break;
            }
            isOpen = false;
        }
    }

    public void set() {
        synchronized (monitor) {
            isOpen = true;
            monitor.notify();
        }
    }

    public void reset() {
        isOpen = false;
    }
}
