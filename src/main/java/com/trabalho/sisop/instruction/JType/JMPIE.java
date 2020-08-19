package com.trabalho.sisop.instruction.JType;

import com.trabalho.sisop.cpu.CPU;
import com.trabalho.sisop.instruction.Instruction;
import com.trabalho.sisop.memory.Memory;
import com.trabalho.sisop.memory.MemorySector;
import com.trabalho.sisop.utils.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JMPIE extends Instruction {

    @Override
    public void execute(CPU cpu, Memory memory, MemorySector memorySector, String[] operation) {

        int rs = Parser.parseParamater(operation[1]);
        int rc = Parser.parseParamater(operation[2]);

        int rsValue = cpu.getRegisters()[rs];
        int rcValue = cpu.getRegisters()[rc];

        if (rcValue == 0) {
            log.info("PC Jump to {}", rsValue);
            cpu.updatePC(rsValue);
        } else {
            cpu.incrementPC();
        }
    }

}
