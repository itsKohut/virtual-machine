package com.trabalho.sisop.queues;

import com.trabalho.sisop.order.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class ConsoleOrderQueue {

    public static Queue<Order> consoleOrderQueue = new LinkedBlockingQueue<>();

    public synchronized static void addProcess(Order order) {

        log.info("Process with id {} added to console order queue", order.getPID());
        consoleOrderQueue.add(order);
    }

    public synchronized static Order removeOrder() {

        Order order = consoleOrderQueue.remove();
        log.info("Process with id {} removed to blocked ready", order.getPID());

        return order;
    }

    public synchronized static void print() {

        if (consoleOrderQueue.size() == 0) {
            System.out.println("Lista de pedidos para console vazia");
        } else {
            consoleOrderQueue.forEach(e -> System.out.println("Processo de id " + e.getPID() + " está na fila de pedidos da console"));
        }
    }


    public synchronized static int getSize() {
        return consoleOrderQueue.size();
    }
}

