package com.trabalho.sisop.queues;

import com.trabalho.sisop.process.ProcessControlBlock;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class BlockedQueue {
    private static Queue<ProcessControlBlock> blockedQueue = new LinkedBlockingQueue<>();

    public synchronized static void addProcess(ProcessControlBlock pcb) {

        log.info("Process with id {} added to blocked queue", pcb.getPID());
        blockedQueue.add(pcb);
    }

    public synchronized static ProcessControlBlock removeProcess() {

        ProcessControlBlock pcb = blockedQueue.remove();
        log.info("Process with id {} removed to blocked ready", pcb.getPID());

        return pcb;
    }

    public synchronized static void print() {

        if (blockedQueue.size() == 0) {
            System.out.println("Lista de bloqueados vazia");
        } else {
            blockedQueue.forEach(e -> System.out.println("Processo de id " + e.getPID() + " está na fila de bloqueados"));
        }
    }

    public synchronized static int getSize() {
        return blockedQueue.size();
    }
}