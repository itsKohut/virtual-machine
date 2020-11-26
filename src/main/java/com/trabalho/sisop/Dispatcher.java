package com.trabalho.sisop;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.process.ProcessControlBlock;
import com.trabalho.sisop.process.StatusPCB;
import com.trabalho.sisop.queues.ReadyQueue;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

import static com.trabalho.sisop.cpu.CPU.cpuSemaphore;

@Getter
public class Dispatcher extends Thread {

    public Thread thread;
    public static Semaphore dispatchSemaphore = new Semaphore(0);

    public Dispatcher() {
        this.thread = new CPU();
    }

    @SneakyThrows
    public void run() {

        while (true) {

            if (ReadyQueue.getSize() > 0) {

                dispatchSemaphore.acquire();

                ProcessControlBlock programContext = ReadyQueue.removeProcess();
                programContext.setStatus(StatusPCB.RUNNING);
                CPU.setContext(programContext.getPC(), programContext.getRegisters(), programContext.getRegisterInstruction(), programContext.getMemoryFrames(), programContext.getPID());

                cpuSemaphore.release();

            }
        }
    }
}

