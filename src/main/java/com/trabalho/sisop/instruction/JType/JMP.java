package com.trabalho.sisop.instruction.JType;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import com.trabalho.sisop.utils.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JMP extends Instruction {

    @Override
    public Instruction construct(String[] parameters) {
        this.OPCODE = parameters[0];
        this.REGISTER_ONE = null;
        this.REGISTER_TWO = null;
        this.PARAMETER = parameters[1];

        return this;
    }

    @Override
    public void execute(CPU cpu, Memory memory, MemorySector memorySector) {

        int k = Parser.parseParamater(PARAMETER);

        log.info("PC Jump to {}", k);
        cpu.updatePC(k);

    }
}
