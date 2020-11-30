package com.trabalho.sisop;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.order.IOTYpe;
import com.trabalho.sisop.process.ProcessManager;
import lombok.extern.slf4j.Slf4j;

import static com.trabalho.sisop.Dispatcher.dispatchSemaphore;

@Slf4j
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

    public void handleInterruption(int programID) {

        if (interruptFlags[0]) {
            log.info("Interruption by invalid instruction");
            ProcessManager.deallocate(programID);

        } else if (interruptFlags[1]) {
            log.info("Interruption by stop instruction");
            ProcessManager.deallocate(programID);

        } else if (interruptFlags[2]) {

            log.info("Interruption by IO");
            int tipo = CPU.getValueFromRegister(8);
            int value = CPU.getValueFromRegister(9);

            IOTYpe iotYpe = IOTYpe.getEnum(tipo);

            ProcessManager.resolveIOInterruption(programID, iotYpe, value);

        } else if (interruptFlags[3]) {

            log.info("Interruption by clock timer");
            ProcessManager.saveContext(programID);
        }

        dispatchSemaphore.release();
    }
}
