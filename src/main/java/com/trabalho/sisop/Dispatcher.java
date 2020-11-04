package com.trabalho.sisop;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.process.ProcessControlBlock;
import com.trabalho.sisop.process.StatusPCB;

public class Dispatcher extends Thread {


    public static void dispatch() { // Usar isso dentro do run() ?

        while (true) {

            if (ReadyQueue.getSize() > 0) {
                ProcessControlBlock programContext = ReadyQueue.removeProcess();
                programContext.setStatus(StatusPCB.RUNNING);
                CPU.setContext(programContext.getPC(), programContext.getRegisters(), programContext.getRegisterInstruction());
                CPU.execute(programContext.getPID(), programContext.getMemoryFrames());
            }
        }
    }
}
