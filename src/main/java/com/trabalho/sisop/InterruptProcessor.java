package com.trabalho.sisop;

import com.trabalho.sisop.process.ProcessManager;

public class InterruptProcessor {

    public static final int HALT = 0;
    public static final int TERMINATE = 1;
    public static final int CLOCK = 2;

    public static final int QUANTITY_OF_FLAGS = 6;

    private static boolean[] interruptFlags = new boolean[QUANTITY_OF_FLAGS];

    public static void setFlag(int type) {
        interruptFlags[type] = true;
    }

    public static void handleInterruption(int programID) {
        if (interruptFlags[0]) { // invalida
            ProcessManager.deallocate(programID);
        } else if (interruptFlags[1]) { // STOp
            ProcessManager.deallocate(programID);
        } else if (interruptFlags[2]) { // CLOCK
            ProcessManager.saveContext(programID);
        }
    }
}
