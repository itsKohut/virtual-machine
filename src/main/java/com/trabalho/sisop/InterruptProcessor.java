package com.trabalho.sisop;

import com.trabalho.sisop.process.ProcessManager;

import static com.trabalho.sisop.Dispatcher.dispatchSemaphore;
import static com.trabalho.sisop.cpu.CPU.cpuSemaphore;

public class InterruptProcessor {

    public static final int HALT = 0;
    public static final int TERMINATE = 1;
    public static final int CLOCK = 2;

    public static final int QUANTITY_OF_FLAGS = 3;

    private boolean[] interruptFlags = new boolean[QUANTITY_OF_FLAGS];

    public void setFlag(int type) {
        interruptFlags[type] = true;
    }

    public void handleInterruption(int programID) {
        if (interruptFlags[0]) { // Instrução com  erro
            ProcessManager.deallocate(programID);
        } else if (interruptFlags[1]) { // Programa finalizado
            ProcessManager.deallocate(programID);
        } else if (interruptFlags[2]) { // Por tempo
            ProcessManager.saveContext(programID);
        }

        dispatchSemaphore.release();
        cpuSemaphore.release();

    }
}
