package com.trabalho.sisop.queues;

import com.trabalho.sisop.process.ProcessControlBlock;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class FinishedQueue {

    public static Queue<ProcessControlBlock> finishedQueue = new LinkedBlockingQueue<>();

    public synchronized static void addProcess(ProcessControlBlock pcb) {
        finishedQueue.add(pcb);
    }

    public synchronized static int getSize() {
        return finishedQueue.size();
    }
}