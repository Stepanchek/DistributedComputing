package org.example;

public class MyReadWriteLock {
    private int readers       = 0;
    private int writers       = 0;
    private int writeRequests = 0;

    public synchronized void readLock() throws InterruptedException{
        while(writers > 0 || writeRequests > 0){
            wait();
        }
        readers++;
    }

    public synchronized void readUnlock(){
        readers--;
        notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException{
        writeRequests++;

        while(readers > 0 || writers > 0){
            wait();
        }
        writeRequests--;
        writers++;
    }

    public synchronized void writeUnlock() throws InterruptedException{
        writers--;
        notifyAll();
    }
}
