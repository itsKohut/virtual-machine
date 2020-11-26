package com.trabalho.sisop.queues;

import com.trabalho.sisop.order.Order;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsoleOrderQueue {

    public static Queue<Order> consoleOrderQueue = new LinkedBlockingQueue<>();

    public synchronized static void addProcess(Order order) {
        consoleOrderQueue.add(order);
    }

    public synchronized static Order removeOrder() {
        return consoleOrderQueue.remove();
    }

    public synchronized static int getSize() {
        return consoleOrderQueue.size();
    }
}

