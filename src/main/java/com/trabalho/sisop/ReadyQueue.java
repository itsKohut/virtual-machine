package com.trabalho.sisop;

import com.trabalho.sisop.process.ProcessControlBlock;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ReadyQueue {

    private static Queue<ProcessControlBlock> queue = new LinkedBlockingQueue<>();

    public static void addProcess(ProcessControlBlock pcb) {
        queue.add(pcb);
    }

    public static ProcessControlBlock removeProcess() {
        if (getSize() > 0) {
            return queue.remove();
        } else
            return null;
    }

    public static int getSize() {
        return queue.size();
    }
}