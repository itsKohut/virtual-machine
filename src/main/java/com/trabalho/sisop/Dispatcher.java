package com.trabalho.sisop;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.process.ProcessControlBlock;
import com.trabalho.sisop.process.StatusPCB;
import com.trabalho.sisop.queues.ReadyQueue;
import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

public class Dispatcher extends Thread {

    public static Semaphore dispatchSemaphore = new Semaphore(0);

    @SneakyThrows
    public void run() {


        while (true) {

            if (ReadyQueue.getSize() > 0) {

                dispatchSemaphore.acquire();

                ProcessControlBlock programContext = ReadyQueue.removeProcess();
                programContext.setStatus(StatusPCB.RUNNING);
                CPU.setContext(programContext.getPC(), programContext.getRegisters(), programContext.getRegisterInstruction(), programContext.getMemoryFrames(), programContext.getPID());

                Thread tr = new CPU();
                tr.start();


            }
        }
    }
}

