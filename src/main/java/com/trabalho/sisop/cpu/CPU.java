package com.trabalho.sisop.cpu;

import com.trabalho.sisop.InterruptProcessor;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.MemoryFrame;
import com.trabalho.sisop.memory.MemoryManager;
import com.trabalho.sisop.utils.Parser;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Getter
@ToString
public class CPU {

    public final static int QUANTITY_OF_REGISTERS = 8;
    private final static String STOP = "STOP";
    public static final int INVALID = -1;
    public static final int CYCLES = 5;

    public static int PC = 0;
    public static Integer[] REGISTERS = new Integer[QUANTITY_OF_REGISTERS];
    public static Instruction REG_INSTRUCTION;

    public static void incrementPC() {
        PC++;
    }

    public static void updatePC(int jumpPosition) {
        PC = jumpPosition;
    }

    public static void updateRegister(int register, int value) {
        REGISTERS[register] = value;
    }

    public static int getValueFromRegister(int register) {
        return REGISTERS[register];
    }

    public static void setContext(int pc, Integer[] registers, Instruction regInstruction) {
        PC = pc;
        REGISTERS = registers;
        REG_INSTRUCTION = regInstruction;
    }

    public static void execute(int programID, MemoryFrame[] memoryFrames) {

        int clock = 0;
        String OPCODE = "";

        while (isFalse(OPCODE.equals(STOP))) {

            int logicalMemoryPosition = MemoryManager.logicalMemoryTranslator(memoryFrames, PC);
            String instruction = MemoryManager.getInstructions(logicalMemoryPosition);
            String[] parameters = Parser.parseOperation(instruction);

            OPCODE = parameters[0];

            if (checkInterruptions(clock, logicalMemoryPosition, OPCODE) == TRUE) {
                break;
            }

            REG_INSTRUCTION = Instruction.instructions.get(OPCODE).construct(parameters);
            REG_INSTRUCTION.execute(memoryFrames);

            clock++;
        }

        InterruptProcessor.handleInterruption(programID);
    }

    public static boolean checkInterruptions(int clock, int logicalMemoryPosition, String OPCODE) {

        boolean flag = false;

        if (clock == CYCLES) {
            InterruptProcessor.setFlag(InterruptProcessor.CLOCK);
            flag = true;
        }

        if (logicalMemoryPosition == INVALID) {
            InterruptProcessor.setFlag(InterruptProcessor.HALT);
            flag = true;
        }

        if (OPCODE.equals(STOP)) {
            InterruptProcessor.setFlag(InterruptProcessor.TERMINATE);
            flag = true;
        }

        return flag;
    }

}
