package com.trabalho.sisop.cpu;

import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import com.trabalho.sisop.utils.Parser;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Getter
@ToString
public class CPU {

    private final static int QUANTITY_OF_REGISTERS = 8;
    private final static String STOP = "STOP";

    private int pc = 0;
    private Integer[] registers;
    private Instruction registerInstruction;

    public CPU() {
        this.registers = new Integer[QUANTITY_OF_REGISTERS];
    }

    public void incrementPC() {
        this.pc++;
    }

    public void updatePC(int jumpPosition) {
        this.pc = jumpPosition;
    }

    public void updateRegister(int register, int value) {
        this.registers[register] = value;
    }

    public int getValueFromRegister(int register) {
        return this.getRegisters()[register];
    }

    public void execute(Memory memory, MemorySector memorySector) { //sem validacao e o campo de memoria
        String OPCODE = "";

        while (isFalse(OPCODE.equals(STOP))) {

            String instruction = memory.getInstructions(this.pc);
            String[] parameters = Parser.parseOperation(instruction);
            OPCODE = parameters[0];

            log.info(instruction);

            registerInstruction = Instruction.instructions.get(OPCODE).construct(parameters);

            try {
                registerInstruction.execute(this, memory, memorySector);

            } catch (Exception e) {

                //TODO Limpar memória em caso de erro

                OPCODE = STOP;
                log.info(e.getMessage());
                log.info("PROGRAM FINISHED DUE INTERRUPTION");

            }
        }
    }
}
