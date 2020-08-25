package com.trabalho.sisop.instruction.JType;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import com.trabalho.sisop.utils.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JMPIG extends Instruction {

    @Override
    public Instruction construct(String[] parameters) {
        this.OPCODE = parameters[0];
        this.REGISTER_ONE =  parameters[1];
        this.REGISTER_TWO = parameters[2];
        this.PARAMETER = null;

        return this;
    }

    @Override
    public void execute(CPU cpu, Memory memory, MemorySector memorySector) {

        int rs = Parser.parseParamater(REGISTER_ONE);
        int rc = Parser.parseParamater(REGISTER_TWO);

        int rsValue = cpu.getRegisters()[rs];
        int rcValue = cpu.getRegisters()[rc];

        if (rcValue > 0) {
            log.info("PC Jump to {}", rsValue);
            cpu.updatePC(rsValue);
        } else {
            cpu.incrementPC();
        }

    }
}
