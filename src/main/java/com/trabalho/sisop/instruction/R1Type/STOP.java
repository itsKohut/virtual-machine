package com.trabalho.sisop.instruction.R1Type;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class STOP extends Instruction {

    @Override
    public Instruction construct(String[] parameters) {
        this.OPCODE = parameters[0];
        this.REGISTER_ONE = null;
        this.REGISTER_TWO = null;
        this.PARAMETER = null;

        return this;
    }

    @Override
    public void execute(CPU cpu, Memory memory, MemorySector memorySector) {

        log.info("PROGRAM FINISHED");
        log.info("PC moved to initial memory position");
        cpu.updatePC(0);

    }
}
