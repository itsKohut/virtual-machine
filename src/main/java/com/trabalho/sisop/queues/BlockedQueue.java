package com.trabalho.sisop.queues;

import com.trabalho.sisop.process.ProcessControlBlock;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.trabalho.sisop.Dispatcher.dispatchSemaphore;

public class BlockedQueue {
    private static Queue<ProcessControlBlock> blockedReady = new LinkedBlockingQueue<>();

    public synchronized static void addProcess(ProcessControlBlock pcb) {

        boolean vazio = blockedReady.size() == 0;

        blockedReady.add(pcb);

        if (vazio && blockedReady.size() != 0) {
            dispatchSemaphore.release();
        }
    }

    public synchronized static ProcessControlBlock removeProcess() {
        if (getSize() > 0) {
            return blockedReady.remove();
        } else
            return null;
    }

    public synchronized static int getSize() {
        return blockedReady.size();
    }
}