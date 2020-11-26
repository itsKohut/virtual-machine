package com.trabalho.sisop;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.order.IOTYpe;
import com.trabalho.sisop.process.ProcessManager;

import static com.trabalho.sisop.Dispatcher.dispatchSemaphore;

public class InterruptProcessor {

    public static final int HALT = 0;
    public static final int TERMINATE = 1;
    public static final int INTERRUPTION_BY_IO = 2;
    public static final int CLOCK = 3;
    public static final int QUANTITY_OF_FLAGS = 4;

    private boolean[] interruptFlags = new boolean[QUANTITY_OF_FLAGS];

    public void setFlag(int type) {
        interruptFlags[type] = true;
    }

    public void handleInterruption(int programID, int logicalMemoryPosition) {

        if (interruptFlags[0]) { // Instrução com  erro
            ProcessManager.deallocate(programID);
        } else if (interruptFlags[1]) { // Programa finalizado
            ProcessManager.deallocate(programID);
        } else if (interruptFlags[2]) { // Devido a IO

            // Pega os valores dos registradores
            int ioType = CPU.getValueFromRegister(8);
            IOTYpe teste = IOTYpe.getEnum(ioType);

            ProcessManager.IO_1(programID, teste, logicalMemoryPosition);

        } else if (interruptFlags[3]) { // Por tempo
            ProcessManager.saveContext(programID);
        }

        dispatchSemaphore.release();
    }
}
