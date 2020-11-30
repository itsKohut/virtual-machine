package com.trabalho.sisop.queues;

import com.trabalho.sisop.process.ProcessControlBlock;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.trabalho.sisop.Dispatcher.dispatchSemaphore;

@Slf4j
public class ReadyQueue {

    public static Queue<ProcessControlBlock> readyQueue = new LinkedBlockingQueue<>();

    public synchronized static void addProcess(ProcessControlBlock pcb) {

        boolean vazio = readyQueue.size() == 0;

        log.info("Process with id {} added to ready queue", pcb.getPID());
        readyQueue.add(pcb);

        if (vazio && readyQueue.size() != 0) {
            dispatchSemaphore.release();
        }
    }

    public synchronized static ProcessControlBlock removeProcess() {
        if (getSize() > 0) {


            ProcessControlBlock pcb = readyQueue.remove();
            log.info("Process with id {} removed to ready queue", pcb.getPID());

            return pcb;

        } else
            return null;
    }

    public synchronized static void print() {

        if (readyQueue.size() == 0) {
            System.out.println("Lista de prontos vazia");
        } else {
            readyQueue.forEach(e -> System.out.println("Processo de id " + e.getPID() + " está na fila de prontos"));
        }
    }

    public synchronized static int getSize() {
        return readyQueue.size();
    }
}