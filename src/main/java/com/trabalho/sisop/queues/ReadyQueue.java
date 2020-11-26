package com.trabalho.sisop.queues;

import com.trabalho.sisop.process.ProcessControlBlock;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.trabalho.sisop.Dispatcher.dispatchSemaphore;

public class ReadyQueue {

    public static Queue<ProcessControlBlock> readyQueue = new LinkedBlockingQueue<>();

    public synchronized static void addProcess(ProcessControlBlock pcb) {

        boolean vazio = readyQueue.size() == 0;

        readyQueue.add(pcb);

        if (vazio && readyQueue.size() != 0) {
            dispatchSemaphore.release();
        }
    }

    public synchronized static ProcessControlBlock removeProcess() {
        if (getSize() > 0) {
            return readyQueue.remove();
        } else
            return null;
    }

    public synchronized static int getSize() {
        return readyQueue.size();
    }
}