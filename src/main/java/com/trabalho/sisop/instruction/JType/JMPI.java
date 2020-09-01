package com.trabalho.sisop.instruction.JType;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import com.trabalho.sisop.utils.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JMPI extends Instruction {

    @Override
    public Instruction construct(String[] parameters) {
        this.OPCODE = parameters[0];
        this.REGISTER_ONE =  parameters[1];
        this.REGISTER_TWO = null;
        this.PARAMETER = null;

        return this;
    }

    @Override
    public void execute(CPU cpu, Memory memory, MemorySector memorySector) {

        int rs = Parser.parseParamater(REGISTER_ONE);

        int rsValue = cpu.getRegisters()[rs];

        log.info("PC Jump to {}", rsValue);
        cpu.updatePC(rsValue);

    }
}
