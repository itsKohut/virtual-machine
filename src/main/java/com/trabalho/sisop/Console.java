package com.trabalho.sisop;

import com.trabalho.sisop.memory.MemoryManager;
import com.trabalho.sisop.order.IOTYpe;
import com.trabalho.sisop.process.ProcessManager;
import com.trabalho.sisop.queues.ConsoleOrderQueue;
import com.trabalho.sisop.order.Order;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@Getter
public class Console extends Thread {

    public Semaphore consoleSemaphore = new Semaphore(0);
    public volatile int transferencia; // ou atomic

    @SneakyThrows
    public void run() {

        while (true) {

            if (isNotEmpty(ConsoleOrderQueue.consoleOrderQueue)) {

                Order order = ConsoleOrderQueue.removeOrder();// Pega o pedido

                if (order.getIO_OPCODE() == IOTYpe.WRITE) { // Lê valor da memória- DMA (Direct Memory Access)
                    log.info("Value {} was found at memory position {}", order.getValue(), order.getPositionMemory());
                }

                if (order.getIO_OPCODE() == IOTYpe.READ) { // Escreve um valor direto na memória

                    consoleSemaphore.acquire();

                    System.out.println("Write a value to write in memory: ");

                    MemoryManager.writeValueToMemory(order.getMemoryFrames(), order.getPositionMemory(), order.getValue());
                    log.info("The value {} was wrote at memory postion {}", transferencia, order.getPositionMemory());
                }

                ProcessManager.IO_2(order.getPID());
            }
        }
    }
}
