package com.trabalho.sisop;

import com.trabalho.sisop.memory.MemoryManager;
import com.trabalho.sisop.order.IOTYpe;
import com.trabalho.sisop.order.Order;
import com.trabalho.sisop.process.ProcessManager;
import com.trabalho.sisop.queues.ConsoleOrderQueue;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

@Slf4j
@Getter
public class Console extends Thread {

    public Semaphore consoleSemaphore = new Semaphore(0);
    public volatile int transferencia; // ou atomic

    @SneakyThrows
    public void run() {

        while (true) {

            if (ConsoleOrderQueue.getSize() > 0) {

                Order order = ConsoleOrderQueue.removeOrder();// Pega o pedido

                if (order.getIO_OPCODE() == IOTYpe.WRITE) { // Lê valor da memória- DMA (Direct Memory Access)

                    log.info("WRITE IO");


                    int value = MemoryManager.getValueFromIndex(order.getMemoryFrames(), order.getPositionMemory());
                    System.out.printf("\nValue %d was found at memory position %d for process with id %d \n", value, order.getPositionMemory(), order.getPID());

                }

                if (order.getIO_OPCODE() == IOTYpe.READ) { // Escreve um valor direto na memória

                    this.consoleSemaphore.acquire();

                    log.info("READ IO");


                    MemoryManager.writeValueToMemory(order.getMemoryFrames(), order.getPositionMemory(), transferencia);

                    System.out.printf("\nThe value %d was wrote at memory position %d", transferencia, order.getPositionMemory());

                }

                ProcessManager.returnIOInterruption(order.getPID());
            }
        }
    }
}
