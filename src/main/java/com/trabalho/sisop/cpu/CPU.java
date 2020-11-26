package com.trabalho.sisop.cpu;

import com.trabalho.sisop.InterruptProcessor;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.MemoryFrame;
import com.trabalho.sisop.memory.MemoryManager;
import com.trabalho.sisop.utils.Parser;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Getter
@ToString
public class CPU extends Thread {

    public static Semaphore cpuSemaphore = new Semaphore(0);

    public final static int QUANTITY_OF_REGISTERS = 9;
    private final static String STOP = "STOP";
    private final static String TRAP = "TRAP";
    public static final int INVALID = -1;
    public static final int CYCLES = 5;

    public static int PC = 0;
    public static Integer[] REGISTERS = new Integer[QUANTITY_OF_REGISTERS];
    public static Instruction REG_INSTRUCTION;

    public static Integer runningPID = null;
    public static MemoryFrame[] pidMemoryFrames = null;

    public synchronized static void incrementPC() {
        PC++;
    }

    public synchronized static void updatePC(int jumpPosition) {
        PC = jumpPosition;
    }

    public synchronized static void updateRegister(int register, int value) {
        REGISTERS[register] = value;
    }

    public synchronized static int getValueFromRegister(int register) {
        return REGISTERS[register];
    }

    public static void setContext(int pc, Integer[] registers, Instruction regInstruction, MemoryFrame[] memoryFrames, Integer pid) {
        PC = pc;
        REGISTERS = registers;
        REG_INSTRUCTION = regInstruction;

        runningPID = pid;
        pidMemoryFrames = memoryFrames;
    }

    @SneakyThrows
    public void run() {

        while (true) {

            int clock = 0;
            String OPCODE = "";
            InterruptProcessor ip = new InterruptProcessor();
            Integer logicalMemoryPosition = null;

            cpuSemaphore.acquire();

            while (isFalse(OPCODE.equals(STOP))) {

                logicalMemoryPosition = MemoryManager.logicalMemoryTranslator(pidMemoryFrames, PC);
                String instruction = MemoryManager.getInstructions(logicalMemoryPosition);
                String[] parameters = Parser.parseOperation(instruction);

                OPCODE = parameters[0];

                if (checkInterruptions(ip, clock, logicalMemoryPosition, OPCODE) == TRUE) {
                    break;
                }

                REG_INSTRUCTION = Instruction.instructions.get(OPCODE).construct(parameters);
                REG_INSTRUCTION.execute(pidMemoryFrames);

                clock++;
            }

            ip.handleInterruption(runningPID, logicalMemoryPosition);
        }
    }

    public synchronized static boolean checkInterruptions(InterruptProcessor ip, int clock, int logicalMemoryPosition, String OPCODE) {

        boolean flag = false;

        if (OPCODE.equals(TRAP)) {
            CPU.incrementPC(); //
            ip.setFlag(InterruptProcessor.INTERRUPTION_BY_IO);
            flag = true;
        }

        if (clock == CYCLES) {
            ip.setFlag(InterruptProcessor.CLOCK);
            flag = true;
        }

        if (logicalMemoryPosition == INVALID) {
            ip.setFlag(InterruptProcessor.HALT);
            flag = true;
        }

        if (OPCODE.equals(STOP)) {
            ip.setFlag(InterruptProcessor.TERMINATE);
            flag = true;
        }

        return flag;
    }

}
