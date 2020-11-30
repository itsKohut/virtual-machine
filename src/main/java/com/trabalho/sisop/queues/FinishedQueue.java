package com.trabalho.sisop.queues;

import com.trabalho.sisop.process.ProcessControlBlock;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class FinishedQueue {

    public static Queue<ProcessControlBlock> finishedQueue = new LinkedBlockingQueue<>();

    public synchronized static void addProcess(ProcessControlBlock pcb) {
        log.info("Process with id {} added to finished queue", pcb.getPID());
        finishedQueue.add(pcb);
    }

    public synchronized static void print() {

        if (finishedQueue.size() == 0) {
            System.out.println("Lista de terminados vazia");
        } else {
            finishedQueue.forEach(e -> System.out.println("Processo de id " + e.getPID() + " está na fila de terminados"));
        }
    }

    public synchronized static int getSize() {
        return finishedQueue.size();
    }
}